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

        bankingEventServer.start();
        Thread.sleep(1000);
        bankingEventServer.stop();
    }
}
