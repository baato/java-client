package com.baato.baatolibrary.utilities;

import com.baato.baatolibrary.application.BaatoLib;
import com.baato.baatolibrary.models.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static ErrorResponse parseError(Response<?> response, String apiVersion, String apiBaseURL) {
        Converter<ResponseBody, ErrorResponse> converter =
                BaatoLib.retrofitV2(apiVersion,apiBaseURL)
                        .responseBodyConverter(ErrorResponse.class, new Annotation[0]);

        ErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorResponse();
        }

        return error;
    }
}
