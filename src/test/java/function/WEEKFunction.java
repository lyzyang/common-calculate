package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Calendar;
import java.util.Map;

public class WEEKFunction  extends AbstractFunction
{
	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure )  {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_WEEK);
        return new AviatorBigInt(i);
    }

    @Override
    public String getName() {
        return "WEEK";
    }

}
