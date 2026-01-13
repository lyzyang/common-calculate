package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知压力(Bar)和温度(℃)，求水比焓(kJ/kg)
 */
public class FDWHPTFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;

	@Override
    public AviatorObject call(Map<String, Object> env, AviatorObject pressure, AviatorObject temperature)
    {
        try
        {
            //TODO 需要修改, 沒有找到对应的函数，暂时使用和FDASPTFunction一樣的函數
            IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
            Number numberValue = FunctionUtils.getNumberValue(pressure, env);
            Number temp = FunctionUtils.getNumberValue(temperature, env);

            // 计算误差大
            double value = if97.specificEnthalpyPT(numberValue.doubleValue(), temp.doubleValue());
            // 计算会报错
            // double value = if97.specificEnthalpySaturatedLiquidP(numberValue.doubleValue());

            // double value = if97.specificEnthalpySaturatedLiquidT(temp.doubleValue());
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
        return "FDWHPT";
    }

//    public static void main(String[] args) {
//        IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
//        System.out.println(if97.specificEnthalpyPT(8.009799999999999d, 139.7d));
//    }
}
