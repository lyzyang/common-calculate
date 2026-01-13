package com.liyz.common.calculate.entity;

/**
 * 计算公式的表达式定义
 */
public class Formula
{
	/**
	 * 计算公式的编码
	 */
	private String formulaCode;

	/**
	 * 能被表达式引擎解析的表达式
	 */
	private String formulaExpression;

	public String getFormulaCode() {
		return formulaCode;
	}

	public void setFormulaCode(String formulaCode) {
		this.formulaCode = formulaCode;
	}

	public String getFormulaExpression() {
		return formulaExpression;
	}

	public void setFormulaExpression(String formulaExpression) {
		this.formulaExpression = formulaExpression;
	}

}
