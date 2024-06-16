package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.GetNewsRequest;
import com.nashss.se.teaminsynchservice.activity.requests.GetWeatherRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetNewsResult;
import com.nashss.se.teaminsynchservice.activity.results.GetWeatherResult;

public class GetNewsLambda
        extends LambdaActivityRunner<GetNewsRequest, GetNewsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetNewsRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetNewsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String memberId = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            GetNewsRequest.builder()
                                    .withMemberId(memberId)
                                    .withManagerEmail(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetNewsActivity().handleRequest(request)
        );
    }
}
