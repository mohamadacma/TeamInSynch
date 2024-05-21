package com.nashss.se.teaminsynchservice.Lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import main.java.com.nashss.se.teaminsynchservice.Lambda.LambdaRequest;

public class GetNewsLambda  implements RequestHandler<LambdaRequest<String>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<String> input, Context context) {
        return LambdaResponse.success();
    }


}
