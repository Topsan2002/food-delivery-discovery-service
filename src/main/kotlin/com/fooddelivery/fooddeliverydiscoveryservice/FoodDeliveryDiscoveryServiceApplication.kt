package com.fooddelivery.fooddeliverydiscoveryservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class FoodDeliveryDiscoveryServiceApplication

fun main(args: Array<String>) {
    runApplication<FoodDeliveryDiscoveryServiceApplication>(*args)
}
