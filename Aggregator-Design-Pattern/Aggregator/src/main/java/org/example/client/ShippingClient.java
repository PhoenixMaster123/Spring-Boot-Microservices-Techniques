package org.example.client;

import org.example.dto.Order;
import org.example.dto.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShippingClient {

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(ShippingClient.class);

    @Autowired
    public ShippingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Shipping fetchShippingDetails(String orderId) {
        try {
            logger.info("Fetching shipping details for orderId: {}", orderId);
            Shipping shipping = restTemplate.getForObject("http://localhost:8082/shipping/" + orderId, Shipping.class);
            logger.info("Successfully fetched order with id: {}", shipping);
            return shipping;
        } catch (Exception e) {
            logger.error("Error occurred while fetching shipping details {}", e.getMessage());
            return null;
        }
    }
}
