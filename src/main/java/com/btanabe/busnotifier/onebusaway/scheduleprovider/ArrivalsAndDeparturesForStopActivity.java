package com.btanabe.busnotifier.onebusaway.scheduleprovider;

import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;

/**
 * Created by Brian on 11/27/16.
 */
public class ArrivalsAndDeparturesForStopActivity extends AbstractOneBusAwayApiInterface {

    public ArrivalsAndDeparturesForStopActivity(KeyProvider keyProvider) {
        super(keyProvider);
    }

    public ArrivalsAndDeparturesForStopModel getArrivalsAndDeparturesForStop(String stopId) throws Exception {
        validateInputs();

        return makeApiCall(new ArrivalsAndDeparturesForStopRequestUrlProvider(stopId), ArrivalsAndDeparturesForStopModel.class);
    }

    private void validateInputs() {

    }
}
