package com.cn.polaris.sample.order.controller;

import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.cn.polaris.sample.user.service.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/1/12.
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("order")
public class OrderController {

    @Value("${user.age}")
    private Integer age;
    @Value("${user.name}")
    private String name;

    @Autowired
    private UserClient userClient;

    @GetMapping("/config")
    public RspBase<User> getConfig() {
        log.info("【配置】开始获取");
        User user = new User();
        user.setAge(age);
        user.setName(name);
        log.info("【配置】获取成功");
        return RspBase.data(user);
    }

    @GetMapping("/feign/{id}")
    public RspBase<User> feignGet(@PathVariable String id) {
        log.info("【订单】开始获取");
        RspBase<User> rspBase = userClient.get(id);
        log.info("【订单】获取成功");
        return rspBase;
    }

    @GetMapping("healthCheck")
    public String healthCheck(HttpServletResponse response) {
        // 模拟健康检查失败，返回BAD_REQUEST，则实例在北极星为异常状态
        // response.setStatus(HttpStatus.BAD_REQUEST.value());
        return "ok";
    }
}
