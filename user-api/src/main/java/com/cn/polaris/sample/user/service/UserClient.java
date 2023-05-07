package com.cn.polaris.sample.user.service;

import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.cn.polaris.sample.user.service.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chen Nan
 */
@FeignClient(value = "user-service", fallback = UserClientFallback.class)
public interface UserClient {
    String PREFIX = "/user";

    @GetMapping(PREFIX + "/{id}")
    RspBase<User> get(@PathVariable("id") String id);

    @PostMapping(PREFIX + "/getById")
    RspBase<User> getById(@RequestParam("id") String id);

    @PostMapping(PREFIX + "/getByUser")
    RspBase<User> getByUser(@RequestBody User user);

    @GetMapping(PREFIX + "/test/error")
    RspBase<User> testError(@RequestParam("id") String id);

    @GetMapping(PREFIX + "/metadata")
    RspBase<String> metadata();
}
