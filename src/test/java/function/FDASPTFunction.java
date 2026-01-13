package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知压力(Bar)和温度(℃)，求气比熵(kJ/(kg.℃))
 */
public class FDASPTFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure,AviatorObject temperature )  {
	    try
        {
            IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
            Number numberValue = FunctionUtils.getNumberValue(pressure, env);
            Number temp = FunctionUtils.getNumberValue(temperature, env);
            double value = if97.specificEntropyPT(numberValue.doubleValue(), temp.doubleValue());
            //double value = if97.specificEntropySaturatedVapourT(temp.doubleValue());
            return new AviatorDouble(value);
        }
        catch (Exception e)
        {
            return new AviatorDouble(0);
        }
    }

    @Override
    public String getName() {
        return "FDASPT";
    }
}