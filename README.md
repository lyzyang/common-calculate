# Common Calculate Library

## 简介
基于Aviator表达式的通用计算库。

## 功能特性
- 支持Aviator表达式计算

## 快速开始

### Maven依赖
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.lyzyang</groupId>
    <artifactId>common-calculate</artifactId>
    <version>v1.0.0</version>
</dependency>
```

### 使用范例
```bash
public class CalculateTest
{
    @Test
    public void test()
    {
        // 获取点列表与数据
        Map<String,Double> pointValueMap = new HashMap<>();
        pointValueMap.put("D_test1", 10d);
        pointValueMap.put("D_test2", 10d);
        pointValueMap.put("D_test3", 20d);

        // 公式累算需要历史数据
        pointValueMap.put("M_test4", 20d);

        // 获取公式
        Map<String,String> formulaMap = new HashMap<>();
        formulaMap.put("M_test1","a=D_test1+D_test2");
        formulaMap.put("M_test2","a=TEST(D_test3)");
        formulaMap.put("M_test3","a=M_test1+M_test2");
        formulaMap.put("M_test4","a=M_test4+10");
        formulaMap.put("M_test5","a=(M_test1>20) ? 0 : M_test2/M_test3;b=a*1000");

        // 初始化计算服务
        CalculateService calculateService = new CalculateService();
        // 注册自定义函数
        calculateService.registerFunction(new TESTFunction());
        // 注册公式
        calculateService.registerFormula(formulaMap);
        // 计算出结果
        Map<String, Double> result1 = calculateService.calculate(pointValueMap,true);
        System.out.println(result1);

        // 将计算结果加入，再次参与计算
        pointValueMap.putAll(result1);
        Map<String, Double> result2 = calculateService.calculate(pointValueMap,false);
        System.out.println(result2);
    }
}

// 自定义函数
class TESTFunction extends AbstractFunction
{
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
```
