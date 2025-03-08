package me.fuzzi.breeze.core;

import me.fuzzi.breeze.util.Console;

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

    /**
     * <p>Список всех плейсхолдеров и их замен.</p>
     * @since 1.0
     */
    static Map<String, String> list = new HashMap<>();

    /**
     * <p>Метод для добавления нового плейсхолдера.</p>
     * @param id идентификатор плейсхлодера.
     * @param replacing то, на что будет заменен плейсхолдер.
     * @since 1.0
     */
    protected static void add(String id, String replacing) {
        list.put(id, replacing);
        Variables.placeholder();
    }

    /**
     * <p>Абстрактный метод всей логики контроллеров.</p>
     * @since 1.0
     */
    public abstract void init();

    /**
     * <p>Загружает класс контроллера.</p>
     * @since 1.0
     */
    public void load() {
        init();
        Console.out.println("Loading " + getClass().getSimpleName() + " controller...");
        Variables.controller();
    }
}