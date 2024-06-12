package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.GetWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetWeatherResult;

public class GetWeatherLambda
        extends LambdaActivityRunner<GetWeatherRequest, GetWeatherResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetWeatherRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWeatherRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String memberId = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            GetWeatherRequest.builder()
                                    .withMemberId(memberId)
                                    .withManagerEmail(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetWeatherActivity().handleRequest(request)
        );
    }
}
