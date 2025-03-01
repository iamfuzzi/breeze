package me.fuzzi.breeze.core;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Класс-родитель для всех классов контроллеров.</p>
 * <p>Имеет в себе все элементы для создания новых контроллеров</p>
 * @author iamfuzzi
 * @version 1.0
 * @since 1.0
 */
public abstract class WebController {
    protected static Map<String, String> list = new HashMap<>();

    /**
     * <p>Метод для добавления нового плейсхолдера.</p>
     * @param id идентификатор плейсхлодера.
     * @param replacing то, на что будет заменен плейсхолдер.
     * @since 1.0
     */
    protected static void add(String id, String replacing) {
        list.put(id, replacing);
    }

    /**
     * @since 1.0
     */
    public abstract void init();

    /**
     * <p>Загружает класс контроллера.</p>
     * @since 1.0
     */
    public void load() {
        init();
        Console.out.println("Loading controllers...");
    }
}