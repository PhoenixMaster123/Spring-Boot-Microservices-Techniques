package org.example.client;


import org.example.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class OrderClient {

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(OrderClient.class);

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order fetchOrder(String orderId) {
        try {
            logger.info("Fetching order with id: {}", orderId);
            Order order = restTemplate.getForObject("http://localhost:8081/orders/" + orderId, Order.class);
            logger.info("Successfully fetched order with id: {}", orderId);
            return order;
        } catch (Exception e) {
            logger.error("Error occurred while fetching order details {}", e.getMessage());
            return null;
        }
    }
}
