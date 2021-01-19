package com.waldecleber.globoplay.subscription.controller;

import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.service.SubscriptionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "Save a subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Subscription saved"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody SubscriptionDTO subscription) {
        subscriptionService.sendSubscriptionToRabbit(subscription);
    }

}
