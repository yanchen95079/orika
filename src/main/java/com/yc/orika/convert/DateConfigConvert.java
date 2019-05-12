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
public class DateConfigConvert extends BidirectionalConverter<Date,String> {

    @Override
    public String convertTo(Date date, Type<String> type, MappingContext mappingContext) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);

    }

    @Override
    public Date convertFrom(String s, Type<Date> type, MappingContext mappingContext) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
