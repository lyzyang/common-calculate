package com.liyz.common.calculate.utils;

import com.liyz.common.calculate.entity.Formula;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FormulaUtil
{
    /**
     * 对表达式排序，表达式没有依赖其他表达式值的排在前面
     * @param expressions
     */
    public static <T extends Formula> void sort(List<T> expressions)
    {
        if (CollectionUtils.isEmpty(expressions))
        {
            return;
        }
        Set<String> codeSet = expressions.stream().map(Formula::getFormulaCode).collect(Collectors.toSet());
        Formula tmp;
        int i = 0, j = expressions.size() - 1;
        Formula head, tail;
        while (i < j)
        {
            boolean matchForHead = false;
            for (int k = i; k < j; k++)
            {
                head = expressions.get(k);
                if (match(head.getFormulaExpression(), codeSet))
                {
                    matchForHead = true;
                    i = k;
                    break;
                }
            }

            boolean matchForTail = false;
            for (int k = j; k > i; k--)
            {
                tail = expressions.get(k);
                if (match(tail.getFormulaExpression(), codeSet))
                {
                    matchForTail = true;
                    j = k;
                    break;
                }
            }
            if (matchForHead && matchForTail)
            {
                tmp = expressions.get(i);
                expressions.set(i, expressions.get(j));
                expressions.set(j, (T) tmp);
            }
            i++;
            j--;
        }
    }

    private static boolean match(String expression, Set<String> codeSet)
    {
        for (String code : codeSet)
        {
            if (expression.contains(code))
            {
                return true;
            }
        }
        return false;
    }
}