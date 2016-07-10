package io.github.tjheslin1.esb.domain.eventstore;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.MongoBalanceCommandWriter;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.settings.PropertiesReader;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.stream.Collectors.joining;

public class DepositServlet extends HttpServlet {

//    private Settings settings = new Settings();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO new up here? Can't use non-default constructor
        DepositRequestJsonUnmarshaller unmarshaller = new DepositRequestJsonUnmarshaller();
        DepositFundsUseCase depositFundsUseCase = new DepositFundsUseCase(
                new MongoBalanceCommandWriter(new MongoClient("localhost"), new Settings(new PropertiesReader("localhost"))));

        String body = request.getReader().lines().collect(joining(System.lineSeparator()));
        DepositRequest depositRequest = unmarshaller.unmarshall(body);

        response.setContentType("application/json");

        try {
            depositFundsUseCase.depositFunds(depositRequest, LocalDateTime.now());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
