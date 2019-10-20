package com.tj.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String arg0){
        System.out.println("DateConverter    convert:"+arg0);
        SimpleDateFormat[] sdf = new SimpleDateFormat[4];
        sdf[0] = new SimpleDateFormat("yyyy/MM/dd");
        sdf[1] = new SimpleDateFormat("yyyy-MM-dd");
        sdf[2] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (SimpleDateFormat f : sdf) {
            try {
                return f.parse(arg0);
            } catch (ParseException e) {
                continue;
            }
        }
        return new Date();
    }


}
