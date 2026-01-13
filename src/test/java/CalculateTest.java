
import entity.FormulaTest;
import entity.PointTest;
import function.*;
import com.liyz.common.calculate.utils.FormulaUtil;
import mock.FormulaMock;
import mock.PointDataMock;
import mock.PointMock;
import com.liyz.common.calculate.service.CalculateService;
import com.liyz.common.calculate.utils.NumericUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class CalculateTest
{
    private static final Logger log = LoggerFactory.getLogger(CalculateTest.class);

    @Test
    public void test() throws IOException
    {
        // 获取点列表
        List<PointTest> pointList = PointMock.load();

        // 获取点数据
        Map<String, Double> pointValueMap = new HashMap<>();
        for (PointTest point : pointList)
        {
            pointValueMap.put(point.getPointCode(), NumericUtil.keep2Digits(point.getPointValue()));
            //log.info(point.getPointCode()+"-------"+point.getPointValue());
        }

        // 获取公式
        List<FormulaTest> formulaList = FormulaMock.load();

        // 对表达式排序，表达式没有依赖其他表达式值的排在前面
        FormulaUtil.sort(formulaList);

        // 组合公式列表
        Map<String,String> formulaMap = new TreeMap<>();
        for(FormulaTest formulaTest : formulaList)
        {
            formulaMap.put(formulaTest.getFormulaCode(),formulaTest.getFormulaExpression());
        }

        // 获取公式历史数据(用于累计)
        Map<String, Double> calculateHistoryData = PointDataMock.getHistoryDatas();
        pointValueMap.putAll(calculateHistoryData);

        // 初始化计算服务
        CalculateService calculateService = new CalculateService();

        // 注册自定义函数
        FDASPTFunction func1 = new FDASPTFunction();
        FDAHPTFunction func2 = new FDAHPTFunction();
        FSTSPFunction func3 = new FSTSPFunction();
        WP2SLFunction func4 = new WP2SLFunction();
        FDWHPTFunction func5 = new FDWHPTFunction();
        FDPOWFunction func6 = new FDPOWFunction();
        FDAHPSFunction func7 = new FDAHPSFunction();
        NOWFunction func8 = new NOWFunction();
        WEEKFunction func9 = new WEEKFunction();
        DAYFunction func10 = new DAYFunction();
        MONTHFunction func11 = new MONTHFunction();
        PERIODTIMEFunction func12 = new PERIODTIMEFunction();
        WP2HLFunction func13 = new WP2HLFunction();
        WT2HLFunction func14 = new WT2HLFunction();
        calculateService.registerCustomFunction(func1);
        calculateService.registerCustomFunction(func2);
        calculateService.registerCustomFunction(func3);
        calculateService.registerCustomFunction(func4);
        calculateService.registerCustomFunction(func5);
        calculateService.registerCustomFunction(func6);
        calculateService.registerCustomFunction(func7);
        calculateService.registerCustomFunction(func8);
        calculateService.registerCustomFunction(func9);
        calculateService.registerCustomFunction(func10);
        calculateService.registerCustomFunction(func11);
        calculateService.registerCustomFunction(func12);
        calculateService.registerCustomFunction(func13);
        calculateService.registerCustomFunction(func14);

        // 计算出结果
        Map<String, Double> result = calculateService.calculate(formulaMap,pointValueMap,true);
        System.out.println(result);
    }
}
