package com.chenyide.cache4redis.service;

import com.chenyide.cache4redis.entity.User;

/**
 * @author chenyide
 * @version v1.0
 * @className RedisService
 * @description
 * @date 2024/6/11 17:52
 **/

public interface RedisService {
    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return 操作结果
     */
    User saveOrUpdate(User user);

    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    User get(Long id);

    /**
     * 删除
     *
     * @param id key值
     */
    void delete(Long id);
}
