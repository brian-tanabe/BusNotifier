package com.bttanabe.busnotifier.test.unit.secrets;

import com.btanabe.busnotifier.secrets.GoogleMapsKeyProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void shouldBeAbleToDecodeTheApiKey() {
        GoogleMapsKeyProvider keyProvider = new GoogleMapsKeyProvider();
        assertThat(keyProvider.getApiKey(), startsWith("AIzaSyDU"));
    }
}
