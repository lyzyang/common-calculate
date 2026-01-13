package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知压力(MPa)，求对应饱和水比熵(kJ/(kg.℃))
 */
public class WP2SLFunction extends AbstractFunction
{
	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure )  {

	    try
        {
            IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
            Number numberValue = FunctionUtils.getNumberValue(pressure, env);
            double value = if97.specificEntropySaturatedLiquidP(numberValue.doubleValue());
            return new AviatorDouble(value);
        }
        catch (Exception e)
        {
            return new AviatorDouble(0);
        }
    }

    @Override
    public String getName() {
        return "W_P2SL";
    }
}
