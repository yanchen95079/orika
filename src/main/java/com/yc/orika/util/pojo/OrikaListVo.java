package com.yc.orika.util.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author Yanchen
 * @ClassName OrikaVo
 * @Date 2019/3/19 18:10
 */
@Setter
@Getter
public class OrikaListVo {


    public OrikaListVo() {

    }

    /**
    * 转换源
    */
    private List<Object> source;
    /**
    * 目标
    */
    private List<Object> target;
    /**
     *  转换字段
     */
    private Map<String,String> map;
    /**
     * 是否自动匹配非转换字段
     */
    private boolean flag=true;

 }

