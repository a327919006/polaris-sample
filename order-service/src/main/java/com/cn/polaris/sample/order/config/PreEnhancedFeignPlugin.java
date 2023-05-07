package com.cn.polaris.sample.order.config;

import com.tencent.cloud.rpc.enhancement.plugin.EnhancedPlugin;
import com.tencent.cloud.rpc.enhancement.plugin.EnhancedPluginContext;
import com.tencent.cloud.rpc.enhancement.plugin.EnhancedPluginType;
import com.tencent.cloud.rpc.enhancement.plugin.EnhancedRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Set;

/**
 * RCP增强-Feign插件
 *
 * @author Chen Nan
 */
@Slf4j
@Component
public class PreEnhancedFeignPlugin implements EnhancedPlugin {
    @Override
    public String getName() {
        return "PreEnhancedFeignPlugin";
    }

    @Override
    public EnhancedPluginType getType() {
        // PRE（前置插件）、POST（后置插件）、EXCEPTION（异常处理插件）、FINALLY（最终执行插件）
        return EnhancedPluginType.PRE;
    }

    /**
     * EnhancedFeignContext：该类包装了Feign增强插件所需的主要参数，
     * 包括Request（请求）、Options（请求控制参数）、Response（响应）、Exception（异常）
     */
    @Override
    public void run(EnhancedPluginContext context) throws Throwable {
        EnhancedRequestContext request = context.getRequest();
        HttpHeaders httpHeaders = request.getHttpHeaders();
        Set<String> keySet = httpHeaders.keySet();
        HttpMethod httpMethod = request.getHttpMethod();
        String name = httpMethod.name();
        URI url = request.getUrl();
        String path = url.getPath();
    }

    /**
     * 异常处理
     */
    @Override
    public void handlerThrowable(EnhancedPluginContext context, Throwable throwable) {
        log.error("【Feign】调用异常：", throwable);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
