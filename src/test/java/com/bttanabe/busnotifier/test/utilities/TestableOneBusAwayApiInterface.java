package com.bttanabe.busnotifier.test.utilities;

import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.call.OneBusAwayApiInterface;
import com.btanabe.busnotifier.onebusaway.requestparamter.OneBusAwayRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.github.rholder.retry.Retryer;

/**
 * I want to keep the makeApiCall protected; however, this makes it hard to test.  To avoid making this access
 * modifier public, I chose to create a wrapper class for this call with a public makeApiCall pass-through function
 *
 * @param <ApiOutputType>
 */
public class TestableOneBusAwayApiInterface<ApiOutputType extends Model> extends OneBusAwayApiInterface<ApiOutputType> {

    public TestableOneBusAwayApiInterface(KeyProvider keyProvider, Retryer<ApiOutputType> retryer) {
        super(keyProvider, retryer);
    }

    @Override
    public Model makeApiCall(OneBusAwayRequestUrlProvider urlProvider, Class<ApiOutputType> outputClassType) throws Throwable {
        return super.makeApiCall(urlProvider, outputClassType);
    }
}
