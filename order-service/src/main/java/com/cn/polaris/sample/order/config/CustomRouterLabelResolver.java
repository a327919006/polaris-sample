
package com.cn.polaris.sample.order.config;

import cn.hutool.json.JSONObject;
import com.tencent.cloud.polaris.router.spi.FeignRouterLabelResolver;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义路由标签
 * 由于服务路由-规则路由仅支持header、param参数、method、cookie、ip、路径等
 * 但不支持根据body类型参数进行路由，需要自定义实现
 * 如：根据body中的id字段，返回路由标签
 *
 * @author lepdou 2022-05-12
 */
@Component
public class CustomRouterLabelResolver implements FeignRouterLabelResolver {

    @Override
    public Map<String, String> resolve(RequestTemplate requestTemplate, Set<String> expressionLabelKeys) {
        Map<String, String> labels = new HashMap<>();
        String url = requestTemplate.url();
        if (!StringUtils.equals("/user/getByUser", url)) {
            return labels;
        }
        Map<String, Collection<String>> headers = requestTemplate.request().headers();
        String body = new String(requestTemplate.body());
        JSONObject userJson = new JSONObject(body);
        String id = userJson.getStr("id");
        if (StringUtils.equals("1", id)) {
            labels.put("version", "1.0.0");
        } else {
            labels.put("version", "2.0.0");
        }
        return labels;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
