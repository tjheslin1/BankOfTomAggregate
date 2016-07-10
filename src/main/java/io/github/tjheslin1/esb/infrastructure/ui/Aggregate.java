package io.github.tjheslin1.esb.infrastructure.ui;

import io.github.tjheslin1.esb.domain.eventstore.DepositServlet;
import io.github.tjheslin1.esb.infrastructure.domain.eventstore.BankingEventServer;
import io.github.tjheslin1.esb.infrastructure.settings.PropertiesReader;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

public class Aggregate {

    public static void main(String[] args) throws Exception {
        Settings settings = new Settings(new PropertiesReader("localhost"));

        BankingEventServer bankingEventServer = new BankingEventServer(settings)
                .withContext(DepositServlet.class, "/deposit");

        try {
            bankingEventServer.start();

            while (true) {
            }
        } finally {
            bankingEventServer.stop();
            System.out.println("Server stopped.");
        }
    }
}
