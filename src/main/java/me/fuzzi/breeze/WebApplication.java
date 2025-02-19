package me.fuzzi.breeze;

import me.fuzzi.mytoml.TOMLObject;

/**
 * <p>The main class for inheritance by all web applications of the Breeze framework.</p>
 * <p>It contains all the necessary tools for creating and editing your own web server.</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public abstract class WebApplication {

    /**
     * <p>The main method for launching the web application.</p>
     * @param application to specify the required launch class
     * @param args for processing launch arguments
     */
    protected static void launch(WebApplication application, String[] args) {
        for (String arg : args) {
            if (arg.equals("-breeze")) {
                System.out.println("""
                         
                           __   __   ___  ___ __  ___
                          |__) |__) |__  |__   / |__\s
                          |__) |  \\ |___ |___ /_ |___
                        """);
                System.out.println("Loading Breeze version 1.0...");
                System.out.println();

                Console.out.println("Initializing web server...");
                application.init();

                Console.out.println("Performing custom actions...");
                application.actions();

                Console.out.println("Server launch!");
                application.start();

                return;
            }
        }
    }

    /**
     * <p>Method for adding additional actions before the server starts.</p>
     * <p>This is the method where classes inheriting from WebController should be loaded.</p>
     */
    protected abstract void actions();

    protected WebServer server;
    protected TOMLObject config;
    private void init() {
        config = new TOMLObject(Resources.getResourceAsContent("config.toml"));
        server = new WebServer(config.getString("main.ip"), config.getInt("main.port"));
    }

    private void reg() {
        for (String name : Resources.getSubdirectories("web")) {
            if (config.hasKey("resource." + name + ".registered")) { // Если имеет регистрацию

                if (config.getBoolean("resource." + name + ".registered")) { // Если зарегистрирован

                    String content = Resources.getResourceAsContent("web/" + name + "/" + name + ".html");

                    if (content.contains("$tu:")) {
                        String[] values = WebController.list.values().toArray(new String[0]);
                        String[] keys = WebController.list.keySet().toArray(new String[0]);

                        for (int i = 0; i < values.length; i++) {
                            content = content.replace("$tu:" + keys[i] + "$", values[i]);
                        }
                    }

                    server.add(config.getString("resource." + name + ".web"), content);
                }
            }
        }
    }
    private void regStatics() {
        for (String fileName : Resources.getFilesInDirectory("static")) {
            server.add("/static/" + fileName, Resources.getResourceAsContent("static/" + fileName));
        }
    }

    private void start() {
        reg();
        regStatics();

        server.start();
    }
}