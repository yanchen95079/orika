package com.yc.orika.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Yanchen
 * @ClassName FirstA
 * @Date 2019/3/20 14:54
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecondB {
    private Long id;
    private String name;
    private Integer age;
    private BigDecimal price;
    private Double amount;
    private Byte type;
    private Integer flag;
    private String date;
}
