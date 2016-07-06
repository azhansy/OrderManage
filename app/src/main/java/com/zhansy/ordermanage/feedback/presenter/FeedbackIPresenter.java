package com.zhansy.ordermanage.feedback.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface FeedbackIPresenter {
    void getData(String key,String productCode);
    void deleteFeedBack(String msg_id);
    void Refresh(String status);
    void Load(String status);
}
