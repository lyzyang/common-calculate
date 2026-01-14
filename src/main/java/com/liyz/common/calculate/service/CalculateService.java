package com.liyz.common.calculate.service;

import com.liyz.common.calculate.utils.FormulaUtil;
import com.liyz.common.calculate.utils.NumericUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CalculateService
{
    private static final Logger log = LoggerFactory.getLogger(CalculateService.class);

    private String splitPatten="\\+|\\-|\\*|/|\\s+|\\t|\\r|\\n|=|\\(|\\)|,|&&|\\|\\||>|>=|<|<=|<\\s+=|>\\s+=|==|:|\\?";

    private Set<String> functionNameSet = new HashSet<>();

    private Map<String, String> formulaLinkMap = new LinkedHashMap<>();

    public CalculateService()
    {
    }

    /**
     * 注册自定义函数。
     * <p>
     * 注册的函数可以在公式中通过名称调用。支持同步和异步函数。
     * </p>
     *
     * @param func 函数配置对象，包含函数名、实现类和参数定义，不能为null
     */
    public void registerFunction(AviatorFunction func)
    {
        if(func != null)
        {
            functionNameSet.add(func.getName());
            AviatorEvaluator.addFunction(func);
            log.info("注册自定义函数<" + func.getName() + ">成功");
        }
    }

    /**
     * 注册公式映射。
     * <p>
     * 公式会根据依赖关系自动排序，确保计算顺序正确。
     * 注册成功的公式可用于后续的批量计算。
     * </p>
     *
     * @param formulaMap 公式映射表，键为公式ID，值为公式表达式
     */
    public void registerFormula(Map<String, String> formulaMap)
    {
        formulaLinkMap.putAll(FormulaUtil.sort(formulaMap));
        log.info("注册公式<" + formulaMap.size() + ">条成功");
    }

    /**
     * 批量计算公式。
     * <p>
     * 根据注册的公式映射和提供数据点值，批量计算所有公式的结果。
     * 支持调试模式，开启后会记录详细的中间计算过程。
     * </p>
     *
     * @param pointValueMap 数据点值映射，键为数据点名称，值为对应的数值。
     *                      例如：{@code {"length": 10, "width": 5, "price": 100, "quantity": 2}}
     *                      不能为null
     * @param debug 是否启用调试模式。true：记录详细计算日志；false：只返回最终结果
     * @return 计算结果映射，键为公式标识符，值为计算结果。
     *         如果计算过程中发生错误，对应的值可能是null或NaN
     */
	public Map<String, Double> calculate(Map<String, Double> pointValueMap, Boolean debug)
	{
		if(formulaLinkMap.size()==0 || pointValueMap.size()==0)
		{
			return Collections.emptyMap();
		}

        // 测点或计算公式对应的值，作为参数参与运算
        final Map<String, Double> paramMap = new LinkedHashMap<>();
        paramMap.putAll(pointValueMap);
        
        final Set<String> formulaCodeHasExecuted = new HashSet<>(1024);

        int formulaNameHasExecutedCount = 0;

        // 计算公式的值
        long start = System.currentTimeMillis();
        Map<String, Double> result = new LinkedHashMap<>();
        do
        {
            formulaNameHasExecutedCount = formulaCodeHasExecuted.size();
            Map<String, Double> result1 = doEvaludate(paramMap, formulaCodeHasExecuted, debug, formulaNameHasExecutedCount);
            if (result1.size() != 0)
            {
                result.putAll(result1);
            }
        }
        while (formulaNameHasExecutedCount != formulaCodeHasExecuted.size());
        long end = System.currentTimeMillis();
        log.info("耗时: {} ms", end - start);

        if(debug)
        {
            log.info("解析表达式总数: {}", formulaCodeHasExecuted.size());
            List<String> notExecutedList = formulaLinkMap.keySet().stream().collect(Collectors.toList());
            notExecutedList.removeAll(formulaCodeHasExecuted);
            log.info("没有解析的表达式: {}", notExecutedList.stream().collect(Collectors.joining(" | ")));
        }

        return result;
	}

    private Map<String, Double> doEvaludate(final Map<String, Double> paramMap,
                                            final Set<? super String> formulaCodeHasExecuted,
                                            boolean debug,
                                            int formulaNameHasExecutedCount)
    {
        // 记录缺少参数运算的公式
        MultiValuedMap<String, String> formulaOfLackParamMap = new ArrayListValuedHashMap<>();
        // 保存计算公式的运算结果
        Map<String, Double> expResult=new LinkedHashMap<>();
        formulaLinkMap.forEach((expName,expression)->
        {
            if(formulaCodeHasExecuted.contains(expName))
            {
                return;
            }

            if(expression.length() != 0)
            {
                String[] exps = expression.split(";");
                Object finalResult=null;
                Map<String,Object> env=new HashMap<>();
                for(String expString : exps)
                {
                    expString = expString.trim();
                    //获取当前表达式执行后的变量名称
                    int equIndex = expString.indexOf("=");
                    if(equIndex == -1)
                    {
                        log.error("{} : 表达式不正确", expression);
                        return;
                    }

                    //变量名
                    String varName = expString.substring(0, equIndex);
                    String finalExpStr = expString.substring(equIndex+1, expString.length());

                    //提取变量
                    String[] splits = finalExpStr.split(splitPatten);
                    Set<String> vars=new HashSet<String>();
                    for(String varable:splits)
                    {
                        String varableTmp = varable.trim();
                        if(varableTmp.length() != 0)
                        {
                            // 正数、负数、和小数
                            String reg = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
                            Matcher matcher = Pattern.compile(reg).matcher(varableTmp);
                            if(!matcher.matches())
                            {
                                vars.add(varableTmp);
                            }
                        }
                    }
                    //判断变量是否有值
                    if(vars.size()>0)
                    {
                        for (String item : vars)
                        {
                            if(env.get(item) != null || functionNameSet.contains(item) || paramMap.get(item) != null)
                            {
                                if(debug)
                                {
                                    if (formulaOfLackParamMap.containsKey(expName))
                                    {
                                        formulaOfLackParamMap.get(expName).remove(item);
                                    }
                                }
                                continue;
                            }

                            if(debug)
                            {
                                formulaOfLackParamMap.put(expName, item);
                            }

                            return;
                        }
                    }

                    //赋值表达式变量
                    if(vars.size()>0)
                    {
                        for (String item : vars)
                        {
                            if(functionNameSet.contains(item))
                            {
                                continue;
                            }
                            Double bigDecimal = paramMap.get(item);
                            if(bigDecimal == null && env.get(item) != null)
                            {
                                bigDecimal = new Double(env.get(item).toString());
                            }

                            if(bigDecimal!=null)
                            {
                                env.put(item,bigDecimal);
                            }
                            else
                            {
                                log.error("没有找到变量的值: {} -> {}", expName, item);
                                return;
                            }
                        }
                    }

                    //log.info("解析表达式:{} -> {}", expName, finalExpStr);
                    Object result;
                    try
                    {
                        result = AviatorEvaluator.execute(finalExpStr,env);
                    }
                    catch (Exception e)
                    {
                        log.error("解析表达是失败：{} -> {}", expName, finalExpStr, e);
                        throw e;
                    }
                   // log.info(finalExpStr+"执行的结果:"+result);

                    //赋值当前操作的变量名
                    env.put(varName,result);
                    finalResult=result;
                }
                //log.info(expName+" -> 执行的最终结果: "+finalResult);

                Double bigDecimal = new Double(finalResult.toString());

                if(Double.isNaN(bigDecimal) || Double.isInfinite(bigDecimal))
                {
                    log.error("公式："+expName+" 数值："+bigDecimal+ " 参与："+env);
                }

                bigDecimal = NumericUtil.keep2Digits(bigDecimal);

                expResult.put(expName,bigDecimal);

                // 计算结果放到参数集合
                paramMap.put(expName,bigDecimal);
                // 记录已成功计算的公式
                formulaCodeHasExecuted.add(expName);
            }
        });

        if(debug && formulaNameHasExecutedCount == formulaCodeHasExecuted.size())
        {
            if(!formulaOfLackParamMap.isEmpty())
            {
                log.info("========formulaOfLackParamMap Start======================================");
                for (Map.Entry<String, Collection<String>> entry : formulaOfLackParamMap.asMap().entrySet()) {
                    String key = entry.getKey();
                    Collection<String> values = entry.getValue();
                    log.info(key + ":" + values);
                }
                log.info("========formulaOfLackParamMap End======================================");
            }
            else
            {
                log.info("所有表达式已计算结束");
            }
        }

        return expResult;
    }
}