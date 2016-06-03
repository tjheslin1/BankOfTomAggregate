package io.github.tjheslin1.settings;

public class TestSettings extends Settings {

    public TestSettings() {
        super(new PropertiesReader("localhost"));
    }
}
