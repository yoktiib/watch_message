package com.pomohouse.message.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SITTIPONG on 4/9/2559.
 */
public class DateTime {

    public static String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(new Date());
    }
}
