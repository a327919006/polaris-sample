package com.cn.polaris.sample.user.service.fallback;

import com.cn.polaris.sample.common.constant.Constants;
import com.cn.polaris.sample.common.model.RspBase;
import com.cn.polaris.sample.user.model.User;
import com.cn.polaris.sample.user.service.UserClient;
import org.springframework.stereotype.Component;

/**
 * @author Chen Nan
 */
@Component
public class UserClientFallback implements UserClient {

    @Override
    public RspBase<User> get(String id) {
        return RspBase.fail(Constants.MSG_FALLBACK);
    }

    @Override
    public RspBase<User> getById(String id) {
        return RspBase.fail(Constants.MSG_FALLBACK);
    }

    @Override
    public RspBase<User> getByUser(User user) {
        return RspBase.fail(Constants.MSG_FALLBACK);
    }

    @Override
    public RspBase<User> testError(String id) {
        return RspBase.fail("熔断");
    }

    @Override
    public RspBase<String> metadata() {
        return RspBase.fail("获取元数据失败");
    }

}
