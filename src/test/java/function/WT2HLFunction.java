package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知温度(℃)，求水比焓(kJ/kg)
 */
public class WT2HLFunction extends AbstractFunction
{
    private static final long serialVersionUID = 1L;

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject temperature)
    {
        try
        {
            IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
            Number temp = FunctionUtils.getNumberValue(temperature, env);

            double value = if97.specificEnthalpySaturatedLiquidT(temp.doubleValue());
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
        return "W_T2HL";
    }

}
