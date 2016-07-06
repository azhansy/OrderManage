package com.zhansy.ordermanage.chart;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseActivity;
import com.zhansy.ordermanage.base.mvp.model.ChartBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.chart.presenter.MpChartPresenterImpl;
import com.zhansy.ordermanage.chart.view.MpChartView;
import com.zhansy.ordermanage.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *图文分析
 */
public class MpChartActivity extends MVPBaseActivity<MpChartPresenterImpl> implements MpChartView {
    @InjectView(R.id.pie_chart)
    PieChart mChart;
    @InjectView(R.id.spinner_chart_type)
    Spinner spinner_chart_type;

    @OnClick(R.id.btn_back)
    void OnClick(){
        finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_mp_chart;
    }

    private List<ChartBean> chartBeanList = new ArrayList<>();
    private String searchKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chartBeanList.add(new ChartBean("未审核",30));
        chartBeanList.add(new ChartBean("备货中",10));
        chartBeanList.add(new ChartBean("已发货",60));
        chartBeanList.add(new ChartBean("已确认",20));
        chartBeanList.add(new ChartBean("已完成",80));
        PieData mPieData = getPieData(chartBeanList);
        showChart(mChart, mPieData);
        mPresenter.getData(searchKey);
        spinner_chart_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchKey = spinner_chart_type.getSelectedItem().toString();
                mPresenter.getData(searchKey);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(40f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("订单分析图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("统计订单类型");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     */
    private PieData getPieData(List<ChartBean> list) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>(); //饼状颜色


        for (int i = 0; i < list.size(); i++) {
//            xValues.add("订单数据" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
            xValues.add(list.get(i).getName());
            yValues.add(new Entry(list.get(i).getNumber(),i));
            if (i%2 == 0){
                colors.add(Color.rgb(205-i*30, 205-i*10, 205+i*10));
            }else {
                colors.add(Color.rgb(205+i*30, 205+i*10, 205-i*10));
            }
        }


        // 饼图数据
//        /**
//         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
//         * 所以 14代表的百分比就是14%
//         */
//        float quarterly1 = 14;
//        float quarterly2 = 14;
//        float quarterly3 = 34;
//        float quarterly4 = 38;
//
//        yValues.add(new Entry(quarterly1, 0));
//        yValues.add(new Entry(quarterly2, 1));
//        yValues.add(new Entry(quarterly3, 2));
//        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "统计截止于："+ TimeUtil.getStandardFullTime(System.currentTimeMillis()) /*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        // 饼图颜色
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected MpChartPresenterImpl createPresenter() {
        return new MpChartPresenterImpl();
    }

    @Override
    public void callbackSuccess(List<ChartBean> list) {
        chartBeanList = list;
        PieData mPieData = getPieData(chartBeanList);
        showChart(mChart, mPieData);
    }

    @Override
    public void callbackFailed() {

    }

    @Override
    public Context getPresenterContext() {
        return this;
    }
}
