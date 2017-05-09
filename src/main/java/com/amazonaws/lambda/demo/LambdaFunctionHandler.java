package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<RequestClass, Boolean> {

    @Override
    public Boolean handleRequest(RequestClass input, Context context) {
    	String output = String.format("%s %s %s", input.latitude, input.longitude, input.accuracy);
        context.getLogger().log("Input: " + output);
        return true;
    }

}
