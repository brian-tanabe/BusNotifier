package com.btanabe.busnotifier.onebusaway.tasks;

import com.btanabe.busnotifier.exceptions.InternalServerException;
import com.btanabe.busnotifier.exceptions.InvalidRequestException;
import com.btanabe.busnotifier.exceptions.ResourceNotFoundException;
import com.btanabe.busnotifier.exceptions.ThrottledRequestException;
import com.btanabe.busnotifier.exceptions.UnableToDeserializeOutputResponseException;
import com.btanabe.busnotifier.exceptions.UnknownErrorCodeException;
import com.btanabe.busnotifier.factories.JsonDeserializer;
import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.call.ResponseCodes;
import com.btanabe.busnotifier.onebusaway.requestparamter.OneBusAwayRequestUrlProvider;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.btanabe.busnotifier.tasks.WebRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Brian on 12/4/16.
 */
@Slf4j
@RequiredArgsConstructor
public class OneBusAwayWebTask<ApiOutputType extends Model> implements Callable<ApiOutputType> {

    @NonNull
    private final KeyProvider keyProvider;

    @NonNull
    private final OneBusAwayRequestUrlProvider urlProvider;

    @NonNull
    private final Class<ApiOutputType> outputClassType;

    private <ApiResponseModel extends Model> ApiResponseModel validateResponse(@NonNull String requestUrl, @NonNull ApiResponseModel responseModel) throws InternalServerException, ThrottledRequestException {
        if (responseModel.getCode().equals(ResponseCodes.SUCCESS.getResponseCode())) {
            return responseModel;
        } else if (responseModel.getCode().equals(ResponseCodes.THROTTLED_REQUEST_V2.getResponseCode())) {
            log.error(String.format("Request throttled! requestUrl=[%s], response=[%s]", requestUrl, responseModel));
            throw new ThrottledRequestException();
        } else if (responseModel.getCode().equals(ResponseCodes.THROTTLED_REQUEST.getResponseCode())) {
            log.error(String.format("Missing or invalid OneBusAway API key or the request was throttled! requestUrl=[%s]", requestUrl));
            throw new ThrottledRequestException();
        } else if (responseModel.getCode().equals(ResponseCodes.SERVER_ERROR.getResponseCode())) {
            log.error(String.format("OneBusAway threw a 500, InternalServerError! requestUrl=[%s], response=[%s]", requestUrl, responseModel));
            throw new InternalServerException();
        } else if (responseModel.getCode().equals(ResponseCodes.INVALID_REQUEST.getResponseCode())) {
            log.error(String.format("Invalid request or request could not be understood! requestUrl=[%s], response=[%s]", requestUrl, responseModel));
            throw new InvalidRequestException();
        } else if (responseModel.getCode().equals(ResponseCodes.RESOURCE_NOT_FOUND.getResponseCode())) {
            log.error(String.format("Resource not found! requestUrl=[%s], response=[%s]", requestUrl, responseModel));
            throw new ResourceNotFoundException();
        } else {
            log.error(String.format("An unknown error occurred! requestUrl=[%s], responseCode=[%d], response=[%s]", requestUrl, responseModel.getCode(), responseModel));
            throw new UnknownErrorCodeException();
        }
    }

    @Override
    public ApiOutputType call() throws Exception {
        final String requestUrl = urlProvider.getRequestUrl(keyProvider);
        final String responseHtml = WebRequest.getPage(requestUrl);

        try {
            final ApiOutputType responseModel = JsonDeserializer.deserializeResponse(responseHtml, outputClassType);
            return validateResponse(requestUrl, responseModel);
        } catch (IOException ex) {
            log.error(String.format("Unable to deserialize API output from requestUrl=[%s], response=[%s]", requestUrl, responseHtml));
            throw new UnableToDeserializeOutputResponseException();
        }
    }
}
