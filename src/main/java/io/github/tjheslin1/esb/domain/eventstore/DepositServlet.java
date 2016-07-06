package io.github.tjheslin1.esb.domain.eventstore;

import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequestJsonUnmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class DepositServlet extends HttpServlet {

    private DepositRequestJsonUnmarshaller unmarshaller = new DepositRequestJsonUnmarshaller();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        DepositRequest depositRequest = unmarshaller.unmarshall(body);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
