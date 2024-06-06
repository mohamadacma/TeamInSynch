package com.nashss.se.teaminsynchservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.teaminsynchservice.activity.requests.SearchMembersRequest;
import com.nashss.se.teaminsynchservice.activity.results.SearchMembersResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchMembersLambda
        extends LambdaActivityRunner<SearchMembersRequest, SearchMembersResult>
        implements RequestHandler<LambdaRequest<SearchMembersRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchMembersRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromQuery(query ->
                        SearchMembersRequest.builder()
                                .withTeamName(query.get("teamName"))
                                .withCity(query.get("city"))
                                .withMemberName(query.get("memberName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideSearchMembersActivity().handleRequest(request)
        );
    }
}
