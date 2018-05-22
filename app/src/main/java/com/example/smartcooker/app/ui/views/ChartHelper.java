package com.example.smartcooker.app.ui.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.example.smartcooker.app.dal.model.ChartArgsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by ke on 2018/5/5.
 */

public class ChartHelper {
    public ChartHelper(LineChartView lineChart) {
        this.lineChart = lineChart;
    }

    private LineChartView lineChart;

    private List<String> xList;
    int[] xLable = {15, 55, 55, 55, 100, 100};//温度的数据
    int[] init_data = {20, 40, 60, 80, 100, 104};//时间的数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private LineChartData data;
    private TreeMap<Integer, Float> map;
    private Set<Integer> keys;


    /**
     * 初始化LineChart的一些设置
     */
    public void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#ff6600"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setStrokeWidth(2);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        line.setPointRadius(4);
        line.setAreaTransparency(100);
        line.setPointColor(Color.parseColor("#ff6600"));
        lines.add(line);

        data = new LineChartData();
        data.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
        data.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
        data.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
        data.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        data.setValueLabelTextSize(12);// 设置数据文字大小
        data.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        data.setLines(lines);
        data.setBaseValue(Float.NEGATIVE_INFINITY);


        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
      //  axisX.setTextColor(Color.parseColor("#D6D6D9"));//灰色
        axisX.setTextColor(Color.parseColor("#a0ff6600"));//灰色


//	    axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(12);//设置字体大小
        axisX.setMaxLabelChars(1); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //  data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线

        Axis axisY = new Axis();  //Y轴
        axisY.setHasLines(false);
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.parseColor("#a0ff6600"));
        data.setAxisYLeft(axisY);  //Y轴设置在左边

        lineChart.setInteractive(false);
        lineChart.setVisibility(View.VISIBLE);
        lineChart.setLineChartData(data);
        lineChart.setViewportCalculationEnabled(false);
        resetViewport();
    }

    public void setMap(TreeMap<Integer, Float> mMap) {
        this.map = mMap;
        xList = new ArrayList<>();
        keys = map.keySet();
        Iterator<Integer> itor = keys.iterator();//获取key的Iterator便利
        while (itor.hasNext()) {//存在下一个值
            xList.add(itor.next() + "m");//当前key值
        }
        getInitAxisPoints();
        getAxisXLables();//获取x轴的标注
        initLineChart();
    }

    public void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 105;
        v.left = 0;
        v.right = xList.size() - 1;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewportWithAnimation(v);
        Log.i("miao", "resetViewport: " + lineChart.getMaximumViewport().top);
    }

    /**
     * X 轴的显示
     */
    public void getAxisXLables() {
        for (int i = 0; i < xList.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(xList.get(i)));
        }
    }

    /**
     * 图表的每个点的显示
     */

    public void getInitAxisPoints() {
        Iterator<Integer> itor = keys.iterator();//获取key的Iterator便利
        int i = 0;
        while (itor.hasNext()) {//存在下一个值
            mPointValues.add(new PointValue(i, map.get(itor.next())));
            i++;
        }
    }


    public void prepareDataAnimation() {
        Line line = data.getLines().get(0);
        for (int i = 0; i < init_data.length; i++) {
            line.getValues().get(i).setTarget(i, xLable[i]);
        }
    }

    public TreeMap<Integer, Float> toMap(List<ChartArgsModel> argsModels) {
        TreeMap<Integer, Float> treeMap = new TreeMap<>();
        treeMap.put(0, (float) 22);
        for (int i=0;i<argsModels.size();i++){
           treeMap.put(treeMap.lastKey()+argsModels.get(i).getGrow_heat_time(),argsModels.get(i).getTemp());
           treeMap.put(treeMap.lastKey()+argsModels.get(i).getHeating_time(),argsModels.get(i).getTemp());
       }
        return treeMap;
    }
}
