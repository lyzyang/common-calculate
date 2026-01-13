package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * 幂函数
 * 
 * @author HuangZhiBiao
 *
 */
public class FDPOWFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject a,AviatorObject b)  {
		try
		{
			Number numberValue = FunctionUtils.getNumberValue(a, env);
			Number temp = FunctionUtils.getNumberValue(b, env);

			double value = Math.pow(numberValue.doubleValue(), temp.doubleValue());
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
		return "FDPOW";
	}

}
