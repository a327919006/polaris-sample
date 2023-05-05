package com.cn.polaris.sample.order.controller;

import com.cn.polaris.sample.common.constant.Constants;
import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.cn.polaris.sample.user.service.UserClient;
import com.tencent.cloud.common.metadata.MetadataContextHolder;
import com.tencent.cloud.common.metadata.StaticMetadataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private StaticMetadataManager staticMetadataManager;

    @GetMapping("/config")
    public RspBase<User> getConfig() {
        log.info("【配置】开始获取");
        User user = new User();
        user.setAge(age);
        user.setName(name);
        log.info("【配置】获取成功");
        return RspBase.data(user);
    }

    @GetMapping("/metadata")
    public RspBase<String> getMetadata() {
        log.info("【元数据】开始获取");
        // 方式一：MetadataContextHolder
        log.info("【元数据】获取可传递的元数据映射表:{}", MetadataContextHolder.get().getTransitiveMetadata());
        log.info("【元数据】获取上游传递过来的一次性元数据:{}", MetadataContextHolder.getAllDisposableMetadata(true));
        log.info("【元数据】获取上游传递过来的一次性元数据:{}", MetadataContextHolder.getAllDisposableMetadata(false));
        log.info("【元数据】获取本地配置的一次性元数据:{}", MetadataContextHolder.get().getDisposableMetadata());
        log.info("【元数据】TransHeaders:{}", MetadataContextHolder.get().getTransHeaders());
        log.info("【元数据】CustomMetadata:{}", MetadataContextHolder.get().getCustomMetadata());
        log.info("【元数据】LoadbalancerMetadata:{}", MetadataContextHolder.get().getLoadbalancerMetadata());

        // 方式二：注入StaticMetadataManager
        log.info("【元数据】获取region:{}", staticMetadataManager.getRegion());
        log.info("【元数据】获取zone:{}", staticMetadataManager.getZone());
        log.info("【元数据】获取campus:{}", staticMetadataManager.getCampus());
        log.info("【元数据】获取所有地理信息元数据映射表:{}", staticMetadataManager.getLocationMetadata());
        log.info("【元数据】获取所有环境变量读取到的元数据映射表:{}", staticMetadataManager.getAllEnvMetadata());
        log.info("【元数据】获取环境变量中可传递的元数据映射表:{}", staticMetadataManager.getEnvTransitiveMetadata());
        log.info("【元数据】获取环境变量中可传递的一次性（一跳）元数据映射表:{}", staticMetadataManager.getEnvDisposableMetadata());
        log.info("【元数据】获取所有配置文件读取到的元数据映射表:{}", staticMetadataManager.getAllConfigMetadata());
        log.info("【元数据】获取配置文件中可传递的元数据映射表:{}", staticMetadataManager.getConfigTransitiveMetadata());
        log.info("【元数据】获取配置文件中可传递的一次性（一跳）元数据映射表:{}", staticMetadataManager.getConfigDisposableMetadata());
        log.info("【元数据】获取InstanceMetadataProvider实现类读取到的元数据映射表:{}", staticMetadataManager.getAllCustomMetadata());
        log.info("【元数据】获取InstanceMetadataProvider实现类中可传递的元数据映射表:{}", staticMetadataManager.getCustomSPITransitiveMetadata());
        log.info("【元数据】获取InstanceMetadataProvider实现类中可传递的一次性（一跳）元数据映射表:{}", staticMetadataManager.getCustomSPIDisposableMetadata());
        log.info("【元数据】获取所有读取到的元数据映射表:{}", staticMetadataManager.getMergedStaticMetadata());
        log.info("【元数据】获取所有读取到的可传递的元数据映射表:{}", staticMetadataManager.getMergedStaticTransitiveMetadata());
        log.info("【元数据】获取所有读取到的可传递的一次性（一跳）元数据映射表:{}", staticMetadataManager.getMergedStaticDisposableMetadata());

        userClient.metadata();
        log.info("【元数据】获取成功");
        return RspBase.success();
    }

    @GetMapping("/feign/{id}")
    public RspBase<User> feignGet(@PathVariable String id) {
        log.info("【订单】开始获取");
        RspBase<User> rspBase = userClient.get(id);
        log.info("【订单】获取成功");
        return rspBase;
    }

    @GetMapping("/getById/{id}")
    public RspBase<User> getById(@PathVariable String id) {
        log.info("【订单】开始获取用户");
        RspBase<User> rspBase = userClient.getById(id);
        log.info("【订单】获取用户成功");
        return rspBase;
    }

    @GetMapping("/getByUser/{id}")
    public RspBase<User> getByUser(@PathVariable String id) {
        log.info("【订单】开始获取用户");
        User user = new User();
        user.setId(id);
        RspBase<User> rspBase = userClient.getByUser(user);
        log.info("【订单】获取用户成功");
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
