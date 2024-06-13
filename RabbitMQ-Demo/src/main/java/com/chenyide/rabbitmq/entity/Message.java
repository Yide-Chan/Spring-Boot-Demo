package com.chenyide.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenyide
 */
@Data
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 392365881428311047L;
    private String id;
    private T content;

}
