package com.liujinli.accountms.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import com.liujinli.accountms.R;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.liujinli.accountms.dao.InaccountDAO;
import com.liujinli.accountms.dao.OutaccountDAO;

import java.util.ArrayList;
import java.util.Map;

public class TotalChart extends Activity {


    String[] types = null;
    private String passType = "";
    private float[] changes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.totalchart);


        Intent intent = getIntent();        //获取Intent对象
        Bundle bundle = intent.getExtras();        //获取传递的数据包
        passType = bundle.getString("passType");
        Resources res = getResources();    //获取Resources对象
        if ("outinfo".equals(passType)) {
            types = res.getStringArray(R.array.outtype);    //获取支出类型数组
            changes = getMoney(passType);
        } else if ("ininfo".equals(passType)) {
            types = res.getStringArray(R.array.intype);    //获取收入类型数组
            changes = getMoney(passType);
        }

        BarChart mChart = findViewById(R.id.bar_chart);
        initBarChart(mChart);
        setBarChartData(types.length, mChart);
    }

    private void initBarChart(BarChart mBarChart) {
        mBarChart.setBackgroundColor(Color.WHITE);
        mBarChart.setDrawGridBackground(false); //网格
        mBarChart.getDescription().setEnabled(false);//描述
        mBarChart.getViewPortHandler().setMaximumScaleX(1.0f);//限制缩放
        mBarChart.getViewPortHandler().setMaximumScaleY(1.0f);
        //背景阴影
        mBarChart.setDrawBarShadow(false);

        //显示边界
        mBarChart.setDrawBorders(false);

        //设置动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear);
        mBarChart.animateX(1000, Easing.EasingOption.Linear);

        //折线图例 标签 设置
        Legend l = mBarChart.getLegend();
        l.setEnabled(false);

        YAxis leftAxis = mBarChart.getAxisLeft();
        YAxis rightAxis = mBarChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        leftAxis.setEnabled(false);
        rightAxis.setEnabled(false);

        XAxis xAxis = mBarChart.getXAxis();

        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        xAxis.setTextColor(0xff74828F);
        xAxis.setTextSize(10f);
        xAxis.setAxisLineColor(0xffe0e0e0);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int idx = (int) value;
                return types[idx];
            }
        });
    }

    private void setBarChartData(int count, BarChart mChart) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        int[] colors = new int[count];

        for (int i = 0; i < count; i++) {
            float val = changes[i];

            if (val > 0) {
                colors[i] = 0xffF04933;
            }

            if (val < 0) {
                colors[i] = 0xff2BBE53;
            }

            yVals.add(new BarEntry(i, Math.abs(val)));
        }

        BarDataSet mBarDataSet = new BarDataSet(yVals, "金额");
        mBarDataSet.setDrawIcons(false);
        mBarDataSet.setColors(colors);
        mBarDataSet.setValueTextSize(12f);
        mBarDataSet.setValueTextColor(0xff74828F);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(mBarDataSet);

        BarData mBarData = new BarData(dataSets);
        mBarData.setBarWidth(0.6f);

        mBarData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int idx = (int) entry.getX();
                return String.valueOf(changes[idx]);
            }
        });

        mChart.setData(mBarData);
    }

    //获取收支数据
    float[] getMoney(String flagType) {
        Map mapMoney = null;
        System.out.println(flagType);
        if ("ininfo".equals(flagType)) {
            InaccountDAO inaccountinfo = new InaccountDAO(TotalChart.this);// 创建TotalChart对象
            mapMoney = inaccountinfo.getTotal();  //获取收入汇总信息
        } else if ("outinfo".equals(flagType)) {
            OutaccountDAO outaccountinfo = new OutaccountDAO(TotalChart.this);// 创建TotalChart对象
            mapMoney = outaccountinfo.getTotal();    //获取支出汇总信息
        }
        int size = types.length;
        float[] money1 = new float[size];
        for (int i = 0; i < size; i++) {
            money1[i] = (mapMoney.get(types[i]) != null ? ((Float) mapMoney.get(types[i])) : 0);
        }
        return money1;
    }
}
