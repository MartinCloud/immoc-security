package com.immoc.security.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MockServer {

    public static void main(String[] args) {
        WireMock.configureFor(8062);
        WireMock.removeAllMappings();
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/order/1"))
                .willReturn(WireMock.aResponse().withBody("{\"id\":1}").withStatus(200)));
    }
}
