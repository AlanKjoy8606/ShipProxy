//package com.ship.proxy.master.request;
//
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Service
//@EnableIntegration
//@IntegrationComponentScan
//public class RequestHandler {
//
//    private WebClient webClient;
//
//    public void ProxyRequestHandler(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.build();
//    }
//
//    @ServiceActivator(inputChannel = "tcpRequestChannel")
//    public byte[] handleTcpRequest(Message<byte[]> message) {
//        String request = new String(message.getPayload(), StandardCharsets.UTF_8);
//        System.out.println("Received request: " + request);
//
//        // Extract the target URL from the received message (assume request contains URL)
//        String targetUrl = extractUrl(request);
//
//        // Forward request to target server
//        Mono<String> responseMono = webClient.get()
//                .uri(targetUrl)
//                .retrieve()
//                .bodyToMono(String.class);
//
//        // Block to get response (Alternatively, return Mono<String> for async handling)
//        String response = responseMono.block();
//        return response != null ? response.getBytes(StandardCharsets.UTF_8) : "Error".getBytes(StandardCharsets.UTF_8);
//    }
//
//    private String extractUrl(String request) {
//        // Implement logic to extract URL from the request payload
//        // Assume request is in format "GET https://example.com"
//        if (request.startsWith("GET ")) {
//            return request.substring(4).trim();
//        }
//        return "https://example.com"; // Default fallback URL
//    }
//}
//
