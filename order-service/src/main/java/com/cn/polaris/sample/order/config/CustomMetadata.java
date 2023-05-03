package com.cn.polaris.sample.order.config;

import com.tencent.cloud.common.spi.InstanceMetadataProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义元数据
 *
 * @author Chen Nan
 */
@Component
public class CustomMetadata implements InstanceMetadataProvider {

    @Override
    public Map<String, String> getMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("custom_key1", "data");
        return metadata;
    }

    @Override
    public Set<String> getTransitiveMetadataKeys() {
        return null;
    }

    @Override
    public Set<String> getDisposableMetadataKeys() {
        return null;
    }

    @Override
    public String getRegion() {
        return null;
    }

    @Override
    public String getZone() {
        return null;
    }

    @Override
    public String getCampus() {
        return null;
    }
}
