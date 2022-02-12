package com.mmt.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.mmt.wiremock.transformers.BodyTransformer;

@SpringBootApplication
public class WiremockServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(WiremockServiceApplication.class, args);
		WireMockConfiguration wireMockConfig = new WireMockConfiguration();
		wireMockConfig.extensions(new BodyTransformer());
		wireMockConfig.port(8089);
		WireMockServer wireMockServer = new WireMockServer(wireMockConfig);
		wireMockServer.start();
		WireMock WM = new WireMock("localhost", 8089);

		// Stub for Pro-Booking Request
		WM.register(post(urlEqualTo("/api/v1/booking/confirm-booking")).withRequestBody(containing("\"requestType\": \"provisional\""))
				.willReturn(aResponse().withHeader("Content-Type", "application/json")
						.withBodyFile("proBooking_response.json").withTransformers("body-transformer")));
	

		// Stub for Booking Request
		
		  WM.register(post(urlEqualTo("/api/v1/booking/confirm-booking"))
		  .withRequestBody(containing("provisionalBookingId")).willReturn(
		  aResponse() .withHeader("Content-Type",
		  "application/json").withBodyFile("Booking_response.json").withTransformers("body-transformer")));


		
	}
}