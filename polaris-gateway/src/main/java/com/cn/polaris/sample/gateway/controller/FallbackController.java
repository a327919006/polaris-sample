package com.cn.polaris.sample.gateway.controller;

import com.cn.polaris.sample.common.model.RspBase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 熔断回调
 *
 * @author Chen Nan
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public Mono<RspBase<String>> getFallback() {
        return Mono.just(RspBase.fail("fallback"));
    }
}
