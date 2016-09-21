package io.github.tjheslin1.acceptance;

import com.googlecode.yatspec.junit.SpecRunner;
import io.github.theangrydev.yatspecfluent.FluentTest;
import org.assertj.core.api.WithAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public abstract class AcceptanceTest<Request, Response> extends FluentTest<Request, Response> implements WithAssertions {

    protected final TestInfrastructure testInfrastructure = new TestInfrastructure();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
