package io.github.tjheslin1.acceptance.givens;

import io.github.theangrydev.yatspecfluent.Given;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import io.github.tjheslin1.acceptance.TestInfrastructure;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class GivenTheStatusPage implements Given {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;

    public GivenTheStatusPage(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
    }

    public GivenTheStatusPage containsProbe(HttpServlet servlet, String path) {
        testInfrastructure.eventServerBuilder().withServlet(new ServletHolder(servlet), path);
        return this;
    }

    @Override
    public void prime() {

    }
}
