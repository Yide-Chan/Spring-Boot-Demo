package com.chenyide.cache4redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenyide
 * @version v1.0
 * @className User
 * @description
 * @date 2024/6/11 17:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;

    private long Id;

    private String name;
}
