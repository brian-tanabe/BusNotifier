package com.bttanabe.busnotifier.test.unit.factories;

import com.btanabe.busnotifier.configuration.RouteAtStopToMonitor;
import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.factories.JsonDeserializer;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.model.StopsForLocation;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/26/16.
 */
@ContextConfiguration("classpath:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonDeerializerTests {

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

    @Autowired
    @Qualifier("singleRouteAtStopToMonitor")
    private String routeAtStopToMonitorString;

    @Autowired
    @Qualifier("expectedSingleRouteAtStopToMonitor")
    private RouteAtStopToMonitor expectedRouteAtStopToMonitor;

    @Autowired
    @Qualifier("singleTravelWindow")
    private String travelWindowString;

    @Autowired
    @Qualifier("seventeenThirtyToTwoThirtyRoute11TravelWindow")
    private TravelWindow expectedTravelWindow;

    @Autowired
    @Qualifier("travelWindowList")
    private String traveWindowListString;

    @Resource(name = "expectedTravelWindowList")
    private List<TravelWindow> expectedTravelWindowList;

    @Test
    public void shouldBeAbleToDeserializeArrivalsAndDeparturesForStopModel() throws Exception {
        ArrivalsAndDeparturesForStopModel model = JsonDeserializer.deserializeResponse(arrivalsAndDeparturesForStop1_12378Response, ArrivalsAndDeparturesForStopModel.class);
        assertThat(model, is(equalTo(expectedArrivalsAndDeparturesForStop1_1237)));
    }

    @Test
    public void shouldBeAbleToDeserializeStopsForLocationModel() throws Exception {
        StopsForLocation model = JsonDeserializer.deserializeResponse(stopsForLocationResponse, StopsForLocation.class);
        assertThat(model, is(equalTo(expectedStopsForLocation)));
    }

    @Test
    public void shouldBeAbleToDeserializeRouteAtStopToMonitor() throws Exception {
        RouteAtStopToMonitor testRouteAtStopToMonitor = JsonDeserializer.deserializeResponse(routeAtStopToMonitorString, RouteAtStopToMonitor.class);
        assertThat(testRouteAtStopToMonitor, is(equalTo(expectedRouteAtStopToMonitor)));
    }

    @Test
    public void shouldBeAbleToDeserializeSingleTravelWindows() throws Exception {
        TravelWindow testTravelWindow = JsonDeserializer.deserializeResponse(travelWindowString, TravelWindow.class);
        assertThat(testTravelWindow, is(equalTo(expectedTravelWindow)));
    }

    @Test
    public void shouldBeAbleToDeserializeTravelWindowLists() throws Exception {
        List<TravelWindow> testTravelWindows = JsonDeserializer.deserializeResponse(traveWindowListString, new TypeReference<List<TravelWindow>>() {
        });
        assertThat(testTravelWindows, is(equalTo(expectedTravelWindowList)));
    }
}
