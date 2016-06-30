package io.github.tjheslin1.esb.infrastructure.settings;

public class TestSettings extends Settings {

    public TestSettings() {
        super(new PropertiesReader("localhost"));
    }
}
