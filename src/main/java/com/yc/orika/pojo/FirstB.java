package com.yc.orika.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Yanchen
 * @ClassName FirstA
 * @Date 2019/3/20 14:54
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FirstB {
    private int id;
    private String name;
    private Integer age;
    private Double priceB;
    private BigDecimal amountB;
    private Byte typeB;
    private Boolean flagB;
}
