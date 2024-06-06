package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.AddMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.AddMemberResult;

public class AddMemberLambda
        extends LambdaActivityRunner<AddMemberRequest, AddMemberResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddMemberRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddMemberRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddMemberRequest request = input.fromBody(AddMemberRequest.class);
                    return input.fromUserClaims(claims ->
                        AddMemberRequest.builder()
                                .withMemberName(request.getMemberName())
                                .withJoinDate(request.getJoinDate())
                                .withPhoneNumber(request.getPhoneNumber())
                                .withCity(request.getCity())
                                .withBackground(request.getBackground())
                                .withRole(request.getRole())
                                .withMemberEmail(request.getMemberEmail())
                                .withManagerEmail(claims.get("email"))
                                .build());
                    },
                (request, serviceComponent) ->
                        serviceComponent.provideAddMemberActivity().handleRequest(request)
        );
    }
}