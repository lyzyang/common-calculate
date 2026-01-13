# Common Calculate Library

## 简介
基于Aviator表达式的通用计算库。

## 功能特性
- 支持Aviator表达式计算
- 提供常用数学计算工具
- 线程安全

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

#### 表达式排序
- 方法：FormulaUtil.sort
- 参数: List<T extends Formula> expressions

```bash
使用范例：
List<FormulaTest> formulaList = FormulaMock.load();
FormulaUtil.sort(formulaList);
```
