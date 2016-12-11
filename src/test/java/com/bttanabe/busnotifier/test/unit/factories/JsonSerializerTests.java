package com.bttanabe.busnotifier.test.unit.factories;

import com.btanabe.busnotifier.factories.JsonSerializer;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.model.StopsForLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/26/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonSerializerTests {

    @Autowired
    @Qualifier("arrivalsAndDeparturesForStop1_12378")
    private String arrivalsAndDeparturesForStop1_12378Response;

    @Autowired
    @Qualifier("expectedArrivalsAndDeparturesForStop1_12378Model")
    private ArrivalsAndDeparturesForStopModel expectedArrivalsAndDeparturesForStop1_1237;

    @Autowired
    @Qualifier("stopForLocation-47.62-122.29")
    private String stopsForLocationResponse;

    @Autowired
    @Qualifier("expectedStopsForLocationModel")
    private StopsForLocation expectedStopsForLocation;

    @Test
    public void shouldBeAbleToDeserializeArrivalsAndDeparturesForStopModel() throws Exception {
        ArrivalsAndDeparturesForStopModel model = JsonSerializer.serializeResponse(arrivalsAndDeparturesForStop1_12378Response, ArrivalsAndDeparturesForStopModel.class);
        assertThat(model, is(equalTo(expectedArrivalsAndDeparturesForStop1_1237)));
    }

    @Test
    public void shouldBeAbleToDeserializeStopsForLocationModel() throws Exception {
        StopsForLocation model = JsonSerializer.serializeResponse(stopsForLocationResponse, StopsForLocation.class);
        assertThat(model, is(equalTo(expectedStopsForLocation)));
    }
}
