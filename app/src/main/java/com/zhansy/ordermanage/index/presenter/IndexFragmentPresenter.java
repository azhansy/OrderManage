package com.zhansy.ordermanage.index.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface IndexFragmentPresenter {
    void Refresh(String status);
    void Load(String status);
    void getProductType();
    /**
     * 请求数据
     */
     void getData();
    void getCouse();
    void setCatePosition(int position);
    void setChileCatePosition(int position);
    void getallCourse();
    void showCates();
//    /**
//     * 设置精选商品
//     */
//     void setGoodProduct();
//    /**
//     * 设置精选厂家
//     */
////     void setGoodFactory();
//
//    /**
//     * 设置ViewPage
//     */
//     void setAd();
}
