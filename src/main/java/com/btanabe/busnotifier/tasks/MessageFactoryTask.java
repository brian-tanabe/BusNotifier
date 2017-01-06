package com.btanabe.busnotifier.tasks;

import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.model.comparators.ArrivalsAndDeparturesComparator;
import com.btanabe.busnotifier.model.comparators.DepartureTimeHelper;
import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;
import com.btanabe.busnotifier.onebusaway.ArrivalsAndDeparturesForStopActivity;
import com.btanabe.busnotifier.utilities.TimeHelper;
import com.google.common.eventbus.EventBus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

/**
 * Created by Brian on 12/11/16.
 */
@Slf4j
@RequiredArgsConstructor
public class MessageFactoryTask implements Runnable {

    @NonNull
    private final EventBus eventBus;

    @NonNull
    private final ArrivalsAndDeparturesForStopActivity arrivalsAndDeparturesForStopActivity;

    @NonNull
    private final List<TravelWindow> travelWindowList;

    @Override
    public void run() {
        log.info(String.format("Checking schedules for the following travel windows=%s", travelWindowList));

        for (TravelWindow travelWindow : travelWindowList) {
            List<ArrivalsAndDepartures> arrivalsAndDeparturesForRouteAtStop = getArrivalsAndDeparturesForStopAndRouteId(travelWindow);
            List<ArrivalsAndDepartures> sortedArrivalsAndDeparturesForRouteAtStop = sortArrivalsAndDeparturesByDepartureTime(arrivalsAndDeparturesForRouteAtStop);
            List<ArrivalsAndDepartures> tripsToDisplay = filterOutTripsWhichAreNotInTheTravelWindowOrAreNotDepartingSoonEnough(travelWindow, sortedArrivalsAndDeparturesForRouteAtStop);
            List<BusArrivalMessage> arrivalMessages = createBusArrivalMessages(travelWindow, tripsToDisplay);
            postBusArrivalMessagesToMessageBus(arrivalMessages);
        }
    }

    private List<ArrivalsAndDepartures> getArrivalsAndDeparturesForStopAndRouteId(TravelWindow travelWindow) {
        try {
            ArrivalsAndDeparturesForStopModel model = (ArrivalsAndDeparturesForStopModel) arrivalsAndDeparturesForStopActivity.getArrivalsAndDeparturesForStop(travelWindow.getRouteAtStopToMonitor().getStopId());
            List<ArrivalsAndDepartures> arrivalsAndDepartures = model.getData().getEntry().getArrivalsAndDepartures();
            return arrivalsAndDepartures.stream().filter(arrivalAndDeparture -> arrivalAndDeparture.getRouteId().equals(travelWindow.getRouteAtStopToMonitor().getRouteId())).collect(toList());
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private List<ArrivalsAndDepartures> sortArrivalsAndDeparturesByDepartureTime(List<ArrivalsAndDepartures> arrivalsAndDepartures) {
        return arrivalsAndDepartures.stream().sorted(new ArrivalsAndDeparturesComparator()).collect(toList());
    }

    /**
     * This helper function filters out any trip whose scheduled or predicted arrival time do not fall
     * within the specified TravelWindow
     *
     * @param travelWindow
     * @param arrivalsAndDepartures
     * @return
     */
    private List<ArrivalsAndDepartures> filterOutTripsWhichAreNotInTheTravelWindowOrAreNotDepartingSoonEnough(TravelWindow travelWindow, List<ArrivalsAndDepartures> arrivalsAndDepartures) {
        return arrivalsAndDepartures.stream().filter(trip -> {
            boolean isWithinTimeWindow = travelWindow.isTimeWithinWindow(DepartureTimeHelper.getDepartureLocalDateTime(trip));
            boolean isDepartingSoonEnough = travelWindow.shouldSendNotification(TimeHelper.getTimeDifferenceInMinutes(now(), DepartureTimeHelper.getDepartureLocalDateTime(trip)));

            return isWithinTimeWindow && isDepartingSoonEnough;
        }).collect(toList());
    }

    private List<BusArrivalMessage> createBusArrivalMessages(TravelWindow travelWindow, List<ArrivalsAndDepartures> arrivalsAndDepartures) {
        List<BusArrivalMessage> busArrivalMessages = new ArrayList<>();
        for (ArrivalsAndDepartures trip : arrivalsAndDepartures) {
            LocalDateTime expectedArrivalTime = DepartureTimeHelper.getDepartureLocalDateTime(trip);
            String routeName = trip.getRouteShortName();
            String stopLocation = travelWindow.getRouteAtStopToMonitor().getStopName();

            busArrivalMessages.add(new BusArrivalMessage(expectedArrivalTime, routeName, stopLocation));
        }

        return busArrivalMessages;
    }

    /**
     * Posts all messages to the EventBus.  They'll be displayed by each registered listener later.
     *
     * @param busArrivalMessages
     */
    private void postBusArrivalMessagesToMessageBus(List<BusArrivalMessage> busArrivalMessages) {
        busArrivalMessages.forEach(message -> eventBus.post(message));
    }
}
