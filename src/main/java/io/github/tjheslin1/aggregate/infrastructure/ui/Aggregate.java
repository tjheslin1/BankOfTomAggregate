package io.github.tjheslin1.aggregate.infrastructure.ui;

import io.github.tjheslin1.aggregate.infrastructure.Wiring;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositServlet;
import io.github.tjheslin1.aggregate.infrastructure.application.web.status.StatusResponseJsonMarshaller;
import io.github.tjheslin1.aggregate.infrastructure.application.web.status.StatusServlet;
import io.github.tjheslin1.aggregate.infrastructure.domain.eventstore.BankingEventServer;
import io.github.tjheslin1.aggregate.infrastructure.domain.eventstore.BankingEventServerBuilder;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class Aggregate {

    public static void main(String[] args) {
        String env = getPropertiesFileEnv();

        Properties properties = loadProperties(env);
        Settings settings = new Settings(properties);
        Wiring wiring = new Wiring(settings);

        final DepositServlet depositServlet = new DepositServlet(wiring.depositFundsUseCase(), new DepositRequestJsonUnmarshaller());
        final StatusServlet statusServlet = new StatusServlet(wiring.statusUseCase(asList(wiring.mongoProbe())), new StatusResponseJsonMarshaller());
        BankingEventServer eventServer = new BankingEventServerBuilder(wiring.servletContextHandler(), settings)
                .withServlet(new ServletHolder(depositServlet), "/deposit")
                .withServlet(new ServletHolder(statusServlet), "/status")
                .build();

        try {
            eventServer.start();

            System.out.println(format("http://%s:%s/status", settings.host(), settings.serverPort()));

            while (true) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                eventServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Server stopped.");
        }
    }

    private static String getPropertiesFileEnv() {
        String env = System.getenv("bank-of-tom-env");
        if (env == null) {
            env = "localhost";
        }
        return env;
    }

    public static Properties loadProperties(String environment) {
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
