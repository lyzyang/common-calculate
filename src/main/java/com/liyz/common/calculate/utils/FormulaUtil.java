package com.liyz.common.calculate.utils;

import org.apache.commons.collections4.MapUtils;

import java.util.*;

public class FormulaUtil
{
    /**
     * 对表达式列表进行排序，使得表达式按照依赖关系排序，并返回一个新的排序后的列表。
     *
     * 排序规则：如果表达式 A 依赖于表达式 B 的值，那么表达式 B 将排在表达式 A 的前面。
     * 换句话说，没有依赖其他表达式值的表达式将排在前面。
     *
     * 该方法使用拓扑排序算法。如果表达式之间存在循环依赖，将抛出异常。
     *
     * 示例：
     * 假设表达式列表为：
     *   expressions = ["a = b + c", "b = 5", "c = d * 2", "d = 1"]
     *
     * 排序后，返回的新列表可能为：
     *   ["d = 1", "b = 5", "c = d * 2", "a = b + c"]
     *
     * 注意：b 和 d 没有依赖关系，它们的顺序可能不确定，但都会在依赖它们的表达式之前。
     *
     * @param formulaMap 要排序的表达式列表。列表中的每个表达式应该能够被解析，以确定依赖关系。
     *                    该方法不会修改传入的列表，而是返回一个新的列表。
     * @return 按照依赖关系排序后的表达式列表。
     */
    public static LinkedHashMap<String, String> sort(Map<String, String> formulaMap)
    {
        if (MapUtils.isEmpty(formulaMap))
        {
            return new LinkedHashMap<>(formulaMap);
        }

        Set<String> codeSet = formulaMap.keySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(formulaMap.entrySet());

        Map.Entry<String, String> tmp;
        int i = 0, j = entryList.size() - 1;
        Map.Entry<String, String> head, tail;

        while (i < j)
        {
            boolean matchForHead = false;
            for (int k = i; k < j; k++)
            {
                head = entryList.get(k);
                if (match(head.getValue(), codeSet))
                {
                    matchForHead = true;
                    i = k;
                    break;
                }
            }

            boolean matchForTail = false;
            for (int k = j; k > i; k--)
            {
                tail = entryList.get(k);
                if (match(tail.getValue(), codeSet))
                {
                    matchForTail = true;
                    j = k;
                    break;
                }
            }
            if (matchForHead && matchForTail)
            {
                tmp = entryList.get(i);
                entryList.set(i, entryList.get(j));
                entryList.set(j, tmp);
            }
            i++;
            j--;
        }

        // 创建新的LinkedHashMap保持顺序
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entryList)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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