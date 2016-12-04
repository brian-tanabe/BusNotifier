package com.btanabe.busnotifier.onebusaway.scheduleprovider;

import com.btanabe.busnotifier.exceptions.MalformedRequestUrlException;
import com.btanabe.busnotifier.factories.JsonSerializer;
import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.requestparamter.OneBusAwayRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.btanabe.busnotifier.tasks.WebRequest;
import com.google.common.base.Strings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

/**
 * Created by Brian on 11/26/16.
 */
@RequiredArgsConstructor
public class AbstractOneBusAwayApiInterface {
    private static final String ONE_BUS_AWAY_API_URL_PREFIX = "http://api.pugetsound.onebusaway.org/api/";

    @NonNull
    private final KeyProvider keyProvider;

    protected <ApiResponseModel extends Model> ApiResponseModel makeApiCall(OneBusAwayRequestUrlProvider urlProvider, Class<ApiResponseModel> outputClassType) throws IOException {
        final String requestUrl = urlProvider.getRequestUrl(keyProvider);

        validateInputParameters(requestUrl);

        final ApiResponseModel responseModel = JsonSerializer.serializeResponse(WebRequest.getPage(requestUrl), outputClassType);

        validateResponse(responseModel);

        return responseModel;
    }

    private void validateInputParameters(String requestUrl) {
        if (Strings.isNullOrEmpty(requestUrl)) {
            throw new MalformedRequestUrlException();
        }

        if (!requestUrl.startsWith(ONE_BUS_AWAY_API_URL_PREFIX)) {
            throw new MalformedRequestUrlException();
        }
    }

    private void validateResponse(Model responseModel) {
        if (!responseModel.getCode().equals(200)) {

        }
    }
}
