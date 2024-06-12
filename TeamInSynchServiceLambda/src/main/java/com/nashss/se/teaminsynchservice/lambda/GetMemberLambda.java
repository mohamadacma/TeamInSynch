package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.GetMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.GetMemberResult;

public class GetMemberLambda
        extends LambdaActivityRunner<GetMemberRequest, GetMemberResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetMemberRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetMemberRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String memberId = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            GetMemberRequest.builder()
                                    .withMemberId(memberId)
                                    .withManagerEmail(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetMemberActivity().handleRequest(request)
        );
    }
}

