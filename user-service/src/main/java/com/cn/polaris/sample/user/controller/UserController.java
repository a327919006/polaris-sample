package com.cn.polaris.sample.user.controller;

import cn.hutool.core.util.IdUtil;
import com.cn.polaris.sample.common.constant.Constants;
import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.tencent.cloud.common.metadata.MetadataContextHolder;
import com.tencent.cloud.common.metadata.StaticMetadataManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2023/4/12.
 */
@RefreshScope
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${user.age}")
    private String age;
    @Autowired
    private StaticMetadataManager staticMetadataManager;

    @GetMapping("/{id}")
    public RspBase<User> get(@PathVariable("id") String id,
                             @RequestHeader(value = "env", required = false) String env) {
        Map<String, String> transitiveMetadata = MetadataContextHolder.get().getTransitiveMetadata();
        log.info("【用户】开始获取id={} env={} metadata={}", id, env, transitiveMetadata);
        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setAge(11);
        log.info("【用户】获取成功" + user);
        return RspBase.data(user);
    }

    @PostMapping("/get")
    public RspBase<User> getByUser(@RequestHeader(value = "env", required = false) String env,
                                   User param) {
        log.info("【用户】开始获取" + param.getId());
        User user = new User();
        user.setId(param.getId());
        user.setName("张三");
        user.setAge(11);
        log.info("【用户】获取成功" + user);
        return RspBase.data(user);
    }

    @GetMapping("/test/error")
    public ResponseEntity testError(@RequestParam("id") String id) {
        if (id.length() > 5) {
            return new ResponseEntity<>(RspBase.fail(Constants.MSG_FALLBACK), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(RspBase.success(), HttpStatus.OK);
    }

    @GetMapping("")
    public Object findList(@ModelAttribute User user) {
        log.info("【用户】开始获取列表" + user);
        List<User> list = new ArrayList<>();
        list.add(user);
        log.info("【用户】获取列表成功");
        return RspBase.data(list);
    }

    @PostMapping("")
    public Object add(@RequestBody User user) {
        log.info("【用户】开始添加" + user);
        user.setId(IdUtil.simpleUUID());
        log.info("【用户】添加成功" + user);
        return RspBase.data(user);
    }

    @GetMapping("/config/age")
    public RspBase<String> getAge() {
        log.info("【配置】开始获取age");
        log.info("【配置】获取成功age");
        return RspBase.data(age);
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

        log.info("【元数据】获取成功");
        return RspBase.success();
    }
}
