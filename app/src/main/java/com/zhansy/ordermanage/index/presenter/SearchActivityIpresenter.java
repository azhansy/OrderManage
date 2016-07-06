package com.zhansy.ordermanage.index.presenter;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public interface SearchActivityIpresenter {

    void triggerSearch(String key,String key_word);
    void getProduct10();

    /**
     * 刷新
     */
    public void Refresh(String status);

    /**
     * 加载
     */
    public void Load(String status);

}
