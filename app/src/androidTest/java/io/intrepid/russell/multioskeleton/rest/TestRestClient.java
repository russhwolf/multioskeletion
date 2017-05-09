package io.intrepid.russell.multioskeleton.rest;

import io.intrepid.russell.multioskeleton.rules.MockServerRule;

public class TestRestClient {
    public static RestApi getRestApi(MockServerRule mockServer) {
        return RetrofitClient.getTestApi(mockServer.getServerUrl());
    }
}
