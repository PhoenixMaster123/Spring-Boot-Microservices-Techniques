package org.example.controller;

import org.example.dto.OrderDetails;
import org.example.service.OTSAggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-aggregator")
public class OTSAggregatorController {

    private final OTSAggregatorService aggregatorService;

    public OTSAggregatorController(OTSAggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/{orderId}")
    public OrderDetails getOrderDetails(@PathVariable String orderId) {
        return aggregatorService.getOrderDetails(orderId);
    }

    // Note: This is not a good idea to expose the clients to the outside world.
    // We should use a service layer to hide the clients.
    // We can use Feign or Retrofit to hide the clients.
    // We can also use Spring Cloud Netflix Feign.
}
