package entity;

import com.liyz.common.calculate.entity.Point;

/**
 * 需要参与计算的测点
 */
public class PointTest extends Point
{

    private Double pointValue;

    public Double getPointValue() {
        return pointValue;
    }

    public void setPointValue(Double pointValue) {
        this.pointValue = pointValue;
    }
}
