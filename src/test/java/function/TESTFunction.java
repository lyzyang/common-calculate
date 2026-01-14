package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class TESTFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure)
    {
        Number numberValue = FunctionUtils.getNumberValue(pressure, env);
        Double resultValue = numberValue.doubleValue() + 100d;
        return new AviatorDouble(resultValue);
    }

    @Override
    public String getName()
    {
        return "TEST";
    }
}

