package com.bttanabe.busnotifier.test.unit.secrets;

import com.btanabe.busnotifier.secrets.OneBusAwayKeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/20/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OneBusAwayKeyProviderTests {

    @Test
    public void shouldBeAbleToDecodeTheApiKey() {
        OneBusAwayKeyProvider keyProvider = new OneBusAwayKeyProvider();
        assertThat(keyProvider.getApiKey(), startsWith("63cdecc4"));
    }
}
