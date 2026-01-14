# Common Calculate Library

## 简介
基于Aviator表达式的通用计算库。

## 功能特性
- 支持Aviator表达式计算

## 快速开始

### Maven依赖
```xml
<dependency>
    <groupId>com.liyz.common</groupId>
    <artifactId>common-calculate</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 使用说明
```bash
使用范例：
// 获取点列表与数据
Map<String,Double> pointValueMap = new HashMap<>();
// 获取公式
Map<String,String> formulaMap = new HashMap<>();
// 初始化自定义函数
CustomFunction func = new CustomFunction()

// 初始化计算服务
CalculateService calculateService = new CalculateService();
// 注册自定义函数
calculateService.registerFunction(func);
// 注册公式
calculateService.registerFormula(formulaMap);
// 计算出结果
Map<String, Double> result = calculateService.calculate(pointValueMap,true);
```
