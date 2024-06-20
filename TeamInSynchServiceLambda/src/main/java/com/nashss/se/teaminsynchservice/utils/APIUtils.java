package com.nashss.se.teaminsynchservice.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.io.IOException;

public  class APIUtils {

    public static String getSecret() {

        String secretName = "newsApiKeyV2";
        Region region = Region.of("us-east-2");

        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(region)
                .build();

        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }
        String secretString = getSecretValueResponse.secretString();

        // Parse the secret string to extract the API key
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode secretJson = objectMapper.readTree(secretString);
            return secretJson.get("news_api_key").asText();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse secret string", e);
        }
    }
}
