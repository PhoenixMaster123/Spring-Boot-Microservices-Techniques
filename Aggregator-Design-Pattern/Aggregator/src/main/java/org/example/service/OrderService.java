package org.example.service;

import org.example.client.OrderClient;
import org.example.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderClient orderClient;

    public OrderService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public Order getOrder(String orderId) {
        logger.debug("OrderService.getOrder called for id {}", orderId);
        return orderClient.fetchOrder(orderId);
    }
}
