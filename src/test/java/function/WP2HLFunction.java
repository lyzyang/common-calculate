/**
 * File Name  : WP2HLFunction.java
 * Authors    : HuangZhiBiao
 * Stage      : Implementation
 * Created    : Jan 7, 2022 8:10:26 PM
 * Copyright  : Copyright © 2022 OneCloud Info,Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * OneCloud Info, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with OneCloud.
 *
 */
package function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.hummeling.if97.IF97;

import java.util.Map;

/*
已知压力(MPa)，求对应饱和水比焓(kJ/(kg.℃))
 */
public class WP2HLFunction extends AbstractFunction
{

	private static final long serialVersionUID = 1L;
	
	@Override
    public AviatorObject call(Map<String,Object> env, AviatorObject pressure )  {
		try
		{
			IF97 if97 = new IF97(IF97.UnitSystem.ENGINEERING);
			Number numberValue = FunctionUtils.getNumberValue(pressure, env);
			double value = if97.specificEnthalpySaturatedLiquidP(numberValue.doubleValue());
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
		return "W_P2HL";
	}

}
