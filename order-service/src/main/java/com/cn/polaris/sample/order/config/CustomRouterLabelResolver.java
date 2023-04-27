
package com.cn.polaris.sample.order.config;

import com.tencent.cloud.polaris.router.spi.FeignRouterLabelResolver;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Customize the business tag information obtained from the request
 *
 * @author lepdou 2022-05-12
 */
@Component
public class CustomRouterLabelResolver implements FeignRouterLabelResolver {

    @Override
    public Map<String, String> resolve(RequestTemplate requestTemplate, Set<String> expressionLabelKeys) {
        Map<String, String> labels = new HashMap<>();
        Map<String, Collection<String>> headers = requestTemplate.request().headers();
        labels.put("env", "dev1");
        return labels;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
