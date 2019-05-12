package com.yc.orika.util.pojo;

import lombok.Getter;
import lombok.Setter;
import ma.glasnost.orika.converter.BidirectionalConverter;

import java.util.List;
import java.util.Map;

/**
 * @author Yanchen
 * @ClassName OrikaVo
 * @Date 2019/3/19 18:10
 */
@Setter
@Getter
public class OrikaVo {

    /**
    * 需要配置转换源
    */
    private Class<?> source;
    /**
    * 需要配置目标
    */
    private Class<?> target;
    /**
     *  转换字段
     */
    private Map<String,String> map;
    /**
     * 是否自动匹配非转换字段
     */
    private boolean flag=true;

 }

