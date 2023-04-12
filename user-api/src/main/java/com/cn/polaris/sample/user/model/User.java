package com.cn.polaris.sample.user.model;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/1/12.
 */
@Getter
@Setter
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
    private Double account;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
