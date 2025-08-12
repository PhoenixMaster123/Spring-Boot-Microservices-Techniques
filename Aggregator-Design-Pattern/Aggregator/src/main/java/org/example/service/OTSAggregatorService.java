package org.example.service;

import org.example.dto.Order;
import org.example.dto.OrderDetails;
import org.example.dto.Payment;
import org.example.dto.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OTSAggregatorService {

    private static final Logger logger = LoggerFactory.getLogger(OTSAggregatorService.class);

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;

    public OTSAggregatorService(OrderService orderService,
                                PaymentService paymentService,
                                ShippingService shippingService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.shippingService = shippingService;
    }

    public OrderDetails getOrderDetails(String orderId) {
        logger.info("Aggregating details for orderId {}", orderId);
        Order order = orderService.getOrder(orderId);
        Shipping shipping = shippingService.getShipping(orderId);
        Payment payment = paymentService.getPayment(orderId);
        return new OrderDetails(order, payment, shipping);
    }
}
