package com.qgwy.springcloud.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "microservice-eureka-order")
public interface FeignApi {

    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable(value = "id") String id);

}