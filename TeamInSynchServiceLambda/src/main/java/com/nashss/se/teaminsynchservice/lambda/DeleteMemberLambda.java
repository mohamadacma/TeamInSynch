package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.DeleteMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.DeleteMemberResult;

public class DeleteMemberLambda
extends LambdaActivityRunner<DeleteMemberRequest, DeleteMemberResult>
implements RequestHandler<AuthenticatedLambdaRequest<DeleteMemberRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteMemberRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String memberId = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            DeleteMemberRequest.builder()
                                    .withMemberId(memberId)
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteMemberActivity().handleRequest(request)
        );
    }
}

