package org.example.service;

import org.example.client.PaymentClient;
import org.example.dto.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentClient paymentClient;

    public PaymentService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public Payment getPayment(String orderId) {
        logger.debug("PaymentService.getPayment called for id {}", orderId);
        return paymentClient.fetchPayment(orderId);
    }
}
