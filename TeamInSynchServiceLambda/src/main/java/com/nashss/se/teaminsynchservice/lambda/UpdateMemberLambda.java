package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.UpdateMemberRequest;
import com.nashss.se.teaminsynchservice.activity.results.UpdateMemberResult;

public class UpdateMemberLambda
        extends LambdaActivityRunner<UpdateMemberRequest, UpdateMemberResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateMemberRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateMemberRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    String memberId = input.getPathParameters().get("id");
                    UpdateMemberRequest request = input.fromBody(UpdateMemberRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateMemberRequest.builder()
                                    .withMemberId(memberId)
                                    .withMemberName(request.getMemberName())
                                    .withJoinDate(request.getJoinDate())
                                    .withPhoneNumber(request.getPhoneNumber())
                                    .withCity(request.getCity())
                                    .withBackground(request.getBackground())
                                    .withRole(request.getRole())
                                    .withMemberEmail(request.getMemberEmail())
                                    .withTeamName(request.getTeamName())
                                    .withManagerEmail(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateMemberActivity().handleRequest(request)
        );
    }
}