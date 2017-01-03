package com.bttanabe.busnotifier.test.factories;

import com.btanabe.busnotifier.configuration.TravelWindow;

import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Brian on 1/2/17.
 */
public class MockTravelWindowFactory {

    public static TravelWindow createMockTravelWindow(TravelWindow travelWindow) {
        TravelWindow mockTravelWindow = mock(TravelWindow.class);
        when(mockTravelWindow.getRouteAtStopToMonitor()).thenReturn(travelWindow.getRouteAtStopToMonitor());
        when(mockTravelWindow.getMinutesBeforeArrivalToStartNotifying()).thenReturn(travelWindow.getMinutesBeforeArrivalToStartNotifying());
        when(mockTravelWindow.isTimeWithinWindow(any(LocalDateTime.class))).thenReturn(true);
        when(mockTravelWindow.shouldSendNotification(any(Long.class))).thenReturn(true);

        return mockTravelWindow;
    }
}
