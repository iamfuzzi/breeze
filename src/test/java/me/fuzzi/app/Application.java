package me.fuzzi.app;

import me.fuzzi.tundraweb.WebApplication;

public class Application extends WebApplication {
    public static void main(String[] args) {
        launch(new Application(), args);
    }
    @Override
    protected void actions() {
        new FileController().load();
    }
}