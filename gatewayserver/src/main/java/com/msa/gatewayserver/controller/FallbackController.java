package com.msa.gatewayserver.controller;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class FallbackController {
    @GetMapping("/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("Error occured you loser");
    }
    

}
