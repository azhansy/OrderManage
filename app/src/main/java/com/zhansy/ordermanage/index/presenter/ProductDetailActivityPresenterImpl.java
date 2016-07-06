package com.zhansy.ordermanage.index.presenter;

import android.os.Handler;
import android.os.Message;

import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.index.view.ProductDetailActivityView;
import com.zhansy.ordermanage.order.business.OrderBusiness;
import com.zhansy.ordermanage.order.controller.OrderController;

import java.lang.ref.WeakReference;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductDetailActivityPresenterImpl extends MVPBasePresenter implements ProductDetailActivityIpresenter {
    private OrderController orderController;
    private ProductDetailActivityHandler handler = new ProductDetailActivityHandler(this);
    public ProductDetailActivityPresenterImpl(){
        orderController = new OrderController(getContext(), handler);
    }

    @Override
    public void getData() {
//        orderController.getTestBaidu();
    }

    @Override
    public void setToshoppingCart(int i) {
        orderController.updateProductById(i);
    }

    public static class ProductDetailActivityHandler extends Handler{
        private WeakReference<ProductDetailActivityIpresenter> presenter;

        public ProductDetailActivityHandler(ProductDetailActivityIpresenter presenter){
            this.presenter = new WeakReference<ProductDetailActivityIpresenter>(presenter);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null){
                ProductDetailActivityPresenterImpl p = (ProductDetailActivityPresenterImpl) presenter.get();
                p.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == OrderBusiness.STATE_PRODUCT_UPDATE_DATA_ERROR) {
            ProductDetailActivityView iview = getActualUi();
            if (iview == null) return;
//            FeedBackAdapter adapter = new FeedBackAdapter(getContext());
//            List<FeedBackBean> feedbackList = LitePalUtil.getFeedBackBeanList(1);
//            adapter.setList(feedbackList);
//            iview.setFeedBackAdapter(adapter);
        }
        if (msg.what == OrderBusiness.STATE_PRODUCT_UPDATE_DATA_SUCCESS) {
            ProductDetailActivityView iview = getActualUi();
            if (iview == null) return;
            iview.setToShoppingCartSuccess();
        }
        if (msg.what == OrderBusiness.STATE_PRODUCT_UPDATE_DATA_ERROR) {
            ProductDetailActivityView iview = getActualUi();
            if (iview == null) return;
            iview.setToShoppingCartFail();
        }
    }
}
