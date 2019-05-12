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
public class SecondA {
    private Integer id;
    private String name;
    private Long age;
    private Double price;
    private BigDecimal amount;
    private Byte type;
    private Boolean flag;
    private Date date;
}
