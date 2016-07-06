package com.zhansy.ordermanage.chart.view;

import com.zhansy.ordermanage.base.mvp.model.ChartBean;
import com.zhansy.ordermanage.base.mvp.view.IBaseUi;

import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface MpChartView extends IBaseUi {
    void callbackSuccess(List<ChartBean> list);
    void callbackFailed();

}
