
import function.*;
import mock.FormulaMock;
import mock.PointDataMock;
import mock.PointMock;
import com.liyz.common.calculate.service.CalculateService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CalculateTest
{
    private static final Logger log = LoggerFactory.getLogger(CalculateTest.class);

    @Test
    public void test1()
    {
        // 获取点列表与数据
        Map<String,Double> pointValueMap = new HashMap<>();
        pointValueMap.put("D_test1", 10d);
        pointValueMap.put("D_test2", 10d);
        pointValueMap.put("D_test3", 20d);

        // 获取公式
        Map<String,String> formulaMap = new HashMap<>();
        formulaMap.put("M_test1","a=D_test1+D_test2");
        formulaMap.put("M_test2","a=TEST(D_test3)");

        // 初始化计算服务
        CalculateService calculateService = new CalculateService();
        // 注册自定义函数
        calculateService.registerFunction(new TESTFunction());
        // 注册公式
        calculateService.registerFormula(formulaMap);
        // 计算出结果
        Map<String, Double> result = calculateService.calculate(pointValueMap,true);
        System.out.println(result);
    }

    @Test
    public void test2()
    {
        // 获取点列表与数据
        Map<String,Double> pointValueMap = PointMock.load();

        // 获取公式
        Map<String,String> formulaMap = FormulaMock.load();

        // 获取公式历史数据(用于累计)
        Map<String, Double> calculateHistoryData = PointDataMock.getHistoryDatas();
        pointValueMap.putAll(calculateHistoryData);

        // 初始化计算服务
        CalculateService calculateService = new CalculateService();

        // 注册自定义函数
        calculateService.registerFunction(new DAYFunction());
        calculateService.registerFunction(new FDAHPSFunction());
        calculateService.registerFunction(new FDAHPTFunction());
        calculateService.registerFunction(new FDASPTFunction());
        calculateService.registerFunction(new FDASPTFunction());
        calculateService.registerFunction(new FDPOWFunction());
        calculateService.registerFunction(new FDWHPTFunction());
        calculateService.registerFunction(new FSTSPFunction());
        calculateService.registerFunction(new MONTHFunction());
        calculateService.registerFunction(new NOWFunction());
        calculateService.registerFunction(new PERIODTIMEFunction());
        calculateService.registerFunction(new WEEKFunction());
        calculateService.registerFunction(new WP2HLFunction());
        calculateService.registerFunction(new WP2SLFunction());
        calculateService.registerFunction(new WT2HLFunction());

        // 注册公式
        calculateService.registerFormula(formulaMap);

        // 计算出结果
        Map<String, Double> result1 = calculateService.calculate(pointValueMap,true);
        System.out.println(result1);

        Map<String, Double> result2 = calculateService.calculate(pointValueMap,false);
        System.out.println(result2);
    }
}
