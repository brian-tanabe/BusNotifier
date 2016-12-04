package com.bttanabe.busnotifier.test.unit.web;

import com.btanabe.busnotifier.tasks.WebRequest;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Brian on 5/15/16.
 */
public class WebRequestTests extends MockWebRequestBase {

    @Test
    public void shouldBeAbleToMockAllPageDownloadCorrectly() {
        ((Set<String>) mockWebRequestCreation).forEach(mockedUrl -> {
            try {
                assertThat(WebRequest.getPage(mockedUrl), is(notNullValue()));
            } catch (Exception e) {
                e.printStackTrace();
                fail(String.format("WebRequestTask failed to mock url=[%s] correctly!", mockedUrl));
            }
        });
    }

    @Test
    public void shouldHaveMoreTheOneTestPageReady() {
        assertThat(((Set<String>) mockWebRequestCreation).size(), is(not(equalTo(0))));
    }
}
