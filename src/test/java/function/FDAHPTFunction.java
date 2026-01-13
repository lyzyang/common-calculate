package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知压力(Bar)和温度(℃)，求气比焓(kJ/kg)
 */
public class FDAHPTFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String, Object> env, AviatorObject pressure, AviatorObject temperature)
    {
        try
        {
            IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
            Number numberValue = FunctionUtils.getNumberValue(pressure, env);
            Number temp = FunctionUtils.getNumberValue(temperature, env);
            double value = if97.specificEnthalpyPT(numberValue.doubleValue(), temp.doubleValue());
            // double value = if97.specificEnthalpySaturatedVapourT(temp.doubleValue());
            return new AviatorDouble(value);
        }
        catch (Exception e)
        {
            return new AviatorDouble(0);
        }
    }

    @Override
    public String getName()
    {
        return "FDAHPT";
    }

//    public static void main(String[] args) {
//        IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
//        System.out.println(if97.specificEnthalpyPT(6.21098 * 10, 356.2d));
//        System.out.println(if97.specificEnthalpyPT(6.21098d * 10, 358.65d));
//    }
}
