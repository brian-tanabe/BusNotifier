package com.btanabe.busnotifier.utilities;

import com.btanabe.busnotifier.configuration.RouteAtStopToMonitor;
import com.btanabe.busnotifier.configuration.TravelWindow;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Strings;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Created by Brian on 12/28/16.
 */
public class TravelWindowJsonDeserializer extends StdDeserializer<TravelWindow> {

    protected TravelWindowJsonDeserializer() {
        this(null);
    }

    protected TravelWindowJsonDeserializer(Class<TravelWindow> item) {
        super(item);
    }

    @Override
    public TravelWindow deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        LocalTime startTime = createLocalTime(node.get(TravelWindow.WINDOW_START_TIME).asText());
        LocalTime endTime = createLocalTime(node.get(TravelWindow.WINDOW_END_TIME).asText());
        Long minutesBeforeArrivalToStartNotifying = node.get(TravelWindow.MINUTES_BEFORE_ARRIVAL).asLong();
        RouteAtStopToMonitor routeAtStopToMonitor = createRouteAtStopToMonitor(node.get(TravelWindow.ROUTE_AT_STOP_TO_MONITOR));

        return new TravelWindow(routeAtStopToMonitor, startTime, endTime, minutesBeforeArrivalToStartNotifying);
    }

    private LocalTime createLocalTime(String localTimeString) {
        String paddedTimeString = Strings.padStart(localTimeString, 5, '0');
        return LocalTime.parse(paddedTimeString);
    }

    private RouteAtStopToMonitor createRouteAtStopToMonitor(JsonNode routeAtStopToMonitorNode) throws IOException {
        JsonParser routeAtStopToMonitorParser = routeAtStopToMonitorNode.traverse();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(routeAtStopToMonitorParser, RouteAtStopToMonitor.class);
    }
}
