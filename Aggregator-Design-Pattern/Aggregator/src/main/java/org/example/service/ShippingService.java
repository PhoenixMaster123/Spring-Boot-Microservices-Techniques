package org.example.service;

import org.example.client.ShippingClient;
import org.example.dto.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);

    private final ShippingClient shippingClient;

    public ShippingService(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

    public Shipping getShipping(String orderId) {
        logger.debug("ShippingService.getShipping called for id {}", orderId);
        return shippingClient.fetchShippingDetails(orderId);
    }
}
