package com.cn.polaris.sample.order.controller;

import com.cn.polaris.sample.common.constant.Constants;
import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.cn.polaris.sample.user.service.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test/error")
    public RspBase<User> testError(String id) {
        log.info("【订单】error");
        log.info("【订单】开始获取");
        RspBase<User> rspBase = userClient.testError(id);
        log.info("【订单】获取成功");
        return rspBase;
    }

    @GetMapping("/test/error2")
    public ResponseEntity testError2(@RequestParam("id") String id) {
        log.info("【订单】error2");
        if (id.length() > 5) {
            return new ResponseEntity<>(RspBase.fail(Constants.MSG_FALLBACK), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(RspBase.success(), HttpStatus.OK);
    }

    @GetMapping("healthCheck")
    public String healthCheck(HttpServletResponse response) {
        // 模拟健康检查失败，返回BAD_REQUEST，则实例在北极星为异常状态
        // response.setStatus(HttpStatus.BAD_REQUEST.value());
        return "ok";
    }
}
