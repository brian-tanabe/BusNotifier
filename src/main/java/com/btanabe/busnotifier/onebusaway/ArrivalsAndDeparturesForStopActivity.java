package com.btanabe.busnotifier.onebusaway;

import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.call.OneBusAwayApiInterface;
import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.github.rholder.retry.Retryer;

/**
 * Created by Brian on 11/27/16.
 */
public class ArrivalsAndDeparturesForStopActivity extends OneBusAwayApiInterface<ArrivalsAndDeparturesForStopModel> {

    public ArrivalsAndDeparturesForStopActivity(KeyProvider keyProvider, Retryer<ArrivalsAndDeparturesForStopModel> retryStrategy) {
        super(keyProvider, retryStrategy);
    }

    public Model getArrivalsAndDeparturesForStop(String stopId) throws Throwable {
        validateInputs();

        return makeApiCall(new ArrivalsAndDeparturesForStopRequestUrlProvider(stopId), ArrivalsAndDeparturesForStopModel.class);
    }

    private void validateInputs() {

    }
}
