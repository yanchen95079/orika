package com.yc.orika.convert;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yanchen
 * @ClassName DateConfigConvert
 * @Date 2019/3/20 16:23
 */
public class BooleanConfigConvert extends BidirectionalConverter<Boolean,Integer> {


    @Override
    public Integer convertTo(Boolean aBoolean, Type<Integer> type, MappingContext mappingContext) {
        if(aBoolean){
            return 1;
        }
        return 0;
    }

    @Override
    public Boolean convertFrom(Integer i, Type<Boolean> type, MappingContext mappingContext) {
        if(i==1){
            return true;
        }
        return false;
    }
}
