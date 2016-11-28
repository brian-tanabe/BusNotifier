package com.bttanabe.busnotifier.test.unit.secrets;

import com.btanabe.busnotifier.secrets.KeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/24/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GoogleMapsKeyProviderTests {

    @Autowired
    @Qualifier("googleMapsKeyProvider")
    private KeyProvider keyProvider;

    @Test
    public void shouldBeAbleToDecodeTheApiKey() {
        assertThat(keyProvider.getApiKey(), startsWith("AIzaSyDU"));
    }
}
