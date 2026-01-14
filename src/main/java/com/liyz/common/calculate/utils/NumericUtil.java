package com.liyz.common.calculate.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class NumericUtil
{
    private static final Logger log = LoggerFactory.getLogger(NumericUtil.class);

    private static DecimalFormat df = new DecimalFormat("#.00");

    public static Double keep2Digits(Double val)
    {
        Double result;
        if(val == null)
        {
            return null;
        }
        try
        {
            result = Double.parseDouble(df.format(val));
        }
        catch (Exception e)
        {
            //log.error("keep2Digits数值转换异常：" + val);
            result = val;
        }
        return result;
    }
}
