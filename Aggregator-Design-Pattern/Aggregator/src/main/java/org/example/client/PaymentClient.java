package org.example.client;

import org.example.dto.Order;
import org.example.dto.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {
    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(PaymentClient.class);

    @Autowired
    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Payment fetchPayment(String orderId) {
        try {
            logger.info("Fetching payment details for order id: {}", orderId);
            Payment payment = restTemplate.getForObject("http://localhost:8083/payments/" + orderId, Payment.class);
            logger.info("Successfully fetched payment details {}", payment);
            return payment;
        } catch (Exception e) {
            logger.error("Error occurred while fetching payment details {}", e.getMessage());
            return null;
        }
    }
}
