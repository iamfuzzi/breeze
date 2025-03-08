package me.fuzzi.breeze.core;

import me.fuzzi.breeze.resources.ResourceMonitor;
import me.fuzzi.breeze.util.Console;
import me.fuzzi.breeze.util.Resources;
import me.fuzzi.mytoml.TOMLObject;

/**
 * <p>Главный класс для наследования всеми веб-приложениями, созданными на Breeze.</p>
 * <p>Содержит все нужные инструменты для создания собственного веб-сайта.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public abstract class WebApplication {

    /**
     * <p>Основной метод запуска веб-приложения.</p>
     * @param application уточняет, какой запускаемый класс приложения будет запущен.
     * @param args для обработки аргументов запуска (Обязательно написать -breeze для запуска!)
     * @since 1.0
     */
    protected static void launch(WebApplication application, String[] args) {
        for (String arg : args) {
            if (arg.equals("-rm")) {
                Variables.monitor();
                ResourceMonitor.start();
            }
        }

        Console.st.println("""
                           __   __   ___  ___ __  ___
                          |__) |__) |__  |__   / |__\s
                          |__) |  \\ |___ |___ /_ |___
                        """);

        Console.out.println("Loading Breeze 1.0 (Dev Build)...");

        boolean isLaunched = false;

        for (String arg : args) {
            if (arg.equals("-breeze")) {

                Console.out.println("Initializing web server...");
                application.init(args);

                Console.out.println("Performing custom actions...");
                application.actions();

                Console.out.println("Registering resources...");
                application.reg();
                application.regStatics();

                if (Variables.getPage() > 0) Console.out.println("Loaded " + Variables.getPage() + " web pages!");
                if (Variables.getStatics() > 0) Console.out.println("Loaded " + Variables.getStatics() + " static files!");
                if (Variables.getController() > 0 && Variables.getPlaceholder() > 0) Console.out.println("Loaded " + Variables.getController() + " controllers and " + Variables.getPlaceholder() + " placeholders!");

                Console.out.println("Starting a server...");

                Console.out.println("Running server on " + application.server + "!");
                application.server.start();

                isLaunched = true;

                break;
            }
        }

        if (!isLaunched) {
            Console.err.println("You need to run application with -breeze argument anywhere!");
        }
    }

    /**
     * <p>Метод для реализации в классах, наследующих WebApplication.</p>
     * <p>Позволяет установить собственные действия, которые происходят между действиями инициализации сервера и его запуском.</p>
     * @since 1.0
     */
    protected abstract void actions();

    /**
     * <p>Экземпляр веб-сервера, который нужен для регистрации веб-страниц (server.getServer()).</p>
     * @since 1.0
     */
    protected WebServer server;

    /**
     * <p>Экземпляр TOML-конфигурации сервера.</p>
     * @since 1.0
     */
    protected TOMLObject config;

    /**
     * <p>Метод инициализации конфига и веб-сервера.</p>
     * @since 1.0
     */
    private void init(String[] args) {
        String conf = "main";
        for (String arg : args) {
            if (arg.startsWith("--br-main:")) {
                conf = arg.replace("--br-main:", "");
                break;
            }
        }
        Console.out.println("Using \"" + conf + "\" main configuration...");

        config = new TOMLObject(Resources.getResourceAsContent("config.toml"));
        Variables.setConfig(config);
        Variables.setConf(conf);
        server = new WebServer(config.getString(conf + ".ip"), config.getInt(conf + ".port"));
    }

    /**
     * <p>Регистрирует все веб-файлы из ресурсов из папки web.</p>
     * @since 1.0
     */
    private void reg() {
        for (String file : Resources.getFilesInDirectory("web")) {
            String name = file.replace(".html", "");
            if (file.contains(".html") && config.hasKey("resource." + name + ".registered") && config.getBoolean("resource." + name + ".registered")) { // Если имеет регистрацию, если это true
                String content = Resources.getResourceAsContent("web/" + name + ".html");

                if (content.contains("%br:")) {
                    String[] values = WebController.list.values().toArray(new String[0]);
                    String[] keys = WebController.list.keySet().toArray(new String[0]);

                    for (int i = 0; i < values.length; i++) {
                        content = content.replace("%br:" + keys[i] + "%", values[i]);
                    }
                }

                server.add(config.getString("resource." + name + ".web"), content);
                Variables.page();
            }
        }
    }

    /**
     * <p>Регистрирует все статические файлы из ресурсов из папки static.</p>
     * @since 1.0
     */
    private void regStatics() {
        for (String folder : Resources.getSubdirectories("static")) {
            for (String file : Resources.getFilesInDirectory("static/" + folder)) {
                server.add("/" + folder + "/" + file, Resources.getResourceAsBytes("static/" + folder + "/" + file));
                Variables.statics();
            }
        }

        for (String file : Resources.getFilesInDirectory("static")) {
            server.add("/static/" + file, Resources.getResourceAsBytes("static/" + file));
            Variables.statics();
        }
    }
}