package com.bttanabe.busnotifier.test.unit.factories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/27/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FileStringFactoryTests {

    @Autowired
    @Qualifier("arrivalsAndDeparturesForStop1_12378")
    private String fileString;

    @Test
    public void shouldBeAbleToAutowireFilesAsStrings() throws Exception {
        assertNotNull(fileString);
        assertThat(fileString, startsWith("{\"code\":200,\"currentTime\":1480191545300,\"data\":{\"entry\":{\"arrivalsAndDepartures\":[{\"arrivalEnabled\":true,\"blockTripSequence\":6,\"departureEnabled\":"));
    }
}
