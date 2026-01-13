package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * PERIODTIM(0) - 返回1秒
 * 
 * @author HuangZhiBiao
 *
 */
public class PERIODTIMEFunction extends AbstractFunction
{
	
	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure )  {
        return new AviatorDouble(1);
    }

	@Override
	public String getName()
	{
		return "PERIODTIME";
	}

}
