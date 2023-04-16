package com.cn.polaris.sample.order.config;

import com.tencent.cloud.polaris.config.annotation.PolarisConfigKVFileChangeListener;
import com.tencent.cloud.polaris.config.listener.ConfigChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 配置变更监听器
 *
 * @author Chen Nan
 */
@Slf4j
@Component
public final class PersonConfigChangeListener {

    /**
     * 按需监听
     */
    @PolarisConfigKVFileChangeListener(interestedKeys = {"user.age", "user.name"})
    // @PolarisConfigKVFileChangeListener(interestedKeyPrefixes = "user")
    public void onChange(ConfigChangeEvent event) {
        Set<String> changedKeys = event.changedKeys();

        for (String changedKey : changedKeys) {
            log.info("changedKey={} {}", changedKey, event.getChange(changedKey));
        }
    }

}