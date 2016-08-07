package io.github.tjheslin1.esb.infrastructure.ui;

import io.github.tjheslin1.esb.infrastructure.Wiring;
import io.github.tjheslin1.esb.infrastructure.application.web.deposit.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.application.web.deposit.DepositServlet;
import io.github.tjheslin1.esb.infrastructure.application.web.status.StatusResponseJsonMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.web.status.StatusServlet;
import io.github.tjheslin1.esb.infrastructure.domain.eventstore.BankingEventServer;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class Aggregate {

    public static void main(String[] args) {
        Properties properties = loadProperties("localhost");
        Settings settings = new Settings(properties);
        Wiring wiring = new Wiring(settings);

        BankingEventServer bankingEventServer = new BankingEventServer(settings)
                .withServlet(new DepositServlet(new DepositRequestJsonUnmarshaller(), wiring.depositFundsUseCase()), "/deposit")
                .withServlet(new StatusServlet(wiring.statusUseCase(), new StatusResponseJsonMarshaller()), "/status");

        try {
            bankingEventServer.start();

            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bankingEventServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Server stopped.");
        }
    }

    static Properties loadProperties(String environment) {
        try (InputStream resourceAsStream = Aggregate.class.getClassLoader().getResourceAsStream(propertiesFileName(environment))) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read file: " + propertiesFileName(environment));
        }
    }

    private static String propertiesFileName(String environment) {
        return format("%s.properties", environment);
    }
}
