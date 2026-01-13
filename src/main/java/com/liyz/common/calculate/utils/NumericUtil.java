package com.liyz.common.calculate.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * 数值工具类
 */
public class NumericUtil
{
    private static final Logger log = LoggerFactory.getLogger(NumericUtil.class);

    private static DecimalFormat df = new DecimalFormat("#.00");

    public static Integer parseInteger(String v)
    {
        Integer d = null;
        if(StringUtils.isNotBlank(v))
        {
            try
            {
                d = Integer.parseInt(v);
            }
            catch (Exception e)
            {
                log.error("parseInteger数值转换异常：" + v);
            }
        }
        return d;
    }

    public static Double parseDouble(String dv)
    {
        Double d = null;
        if(StringUtils.isNotBlank(dv))
        {
            try
            {
                d = Double.parseDouble(dv);
            }
            catch (Exception e)
            {
                //log.error("parseDouble数值转换异常：" + dv);
            }
        }
        return d;
    }

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

    public static String kee2DigitsStr(double val)
    {
        return df.format(val);
    }
}
