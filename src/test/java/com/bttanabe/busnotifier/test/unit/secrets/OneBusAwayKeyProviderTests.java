package com.bttanabe.busnotifier.test.unit.secrets;

import com.btanabe.busnotifier.secrets.KeyProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/20/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OneBusAwayKeyProviderTests {

    @Autowired
    @Qualifier("oneBusAwayKeyProvider")
    private KeyProvider keyProvider;

    @Test
    @Ignore
    public void shouldBeAbleToDecodeTheApiKey() {
        assertThat(keyProvider.getApiKey(), startsWith("63cdecc4"));
    }

    @Test
    public void shouldBeAbleToDecodeTheTestApiKey() {
        assertThat(keyProvider.getApiKey(), is(equalTo("TEST")));
    }
}
