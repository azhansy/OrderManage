package com.zhansy.ordermanage.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.order.OrderDetailActivity;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ExpandableOrderAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<OrderBean> orderBeanList;
    private LayoutInflater mInflater;
    private String typeAdmin = "user"; //admin,user

    public Context getmContext() {
        return mContext;
    }

    public List<OrderBean> getmList() {
        return orderBeanList;
    }

    public ExpandableOrderAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setTypeAdmin(String s){
        this.typeAdmin = s;
    }

    public void setOrderBeanList(List<OrderBean> list){
        if (orderBeanList != null){
            orderBeanList.clear();
        }
        this.orderBeanList = list;
    }

    public void addList(List<OrderBean> list){
        orderBeanList.addAll(list);
    }
    @Override
    public int getGroupCount() {
        return orderBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
//        return orderBeanList.size();
        return orderBeanList.get(i).getProductBeanList().size();
    }

    @Override
    public Object getGroup(int i) {
        return getGroup(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
//        return null;
        return orderBeanList.get(groupPosition).getProductBeanList().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        final OrderBean bean = orderBeanList.get(groupPosition);
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.order_main_head, null);
//            holder.cb_order_head = (CheckBox) view.findViewById(R.id.cb_order_head);
            holder.tv_order_code = (TextView) view.findViewById(R.id.tv_order_code);
            holder.tv_order_detail = (TextView) view.findViewById(R.id.tv_order_detail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_order_code.setText(bean.getOrder_code());
        holder.tv_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OrderDetailActivity.launch(mContext,OrderDetailActivity.class,groupPosition+"");
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("OrderBean",bean);
                intent.putExtras(bundle);
                intent.putExtra("typeAdmin", typeAdmin);
                StringBuffer product_code = new StringBuffer();
                for (int i=0; i<bean.getProductBeanList().size();i++){
                    product_code = product_code.append(bean.getProductBeanList().get(i).getProduct_code()+",");
                }
                product_code = product_code.deleteCharAt(product_code.length() - 1);
                intent.putExtra("product_code", product_code.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ViewHolder holderChild;
//        final ProductBean productBean = new ProductBean();
        final ProductBean productBean = orderBeanList.get(i).getProductBeanList().get(i1);
        if (view == null) {
            holderChild = new ViewHolder();
            view = mInflater.inflate(R.layout.order_main_item, null);
            holderChild.product_image = (ImageView) view.findViewById(R.id.product_image);
            holderChild.product_name = (TextView) view.findViewById(R.id.product_name);
            holderChild.product_code = (TextView) view.findViewById(R.id.product_code);
            holderChild.product_price = (TextView) view.findViewById(R.id.product_price);
            holderChild.product_number = (TextView) view.findViewById(R.id.product_number);
            view.setTag(holderChild);
        } else {
            holderChild = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(productBean.getImgurl(),holderChild.product_image);
        holderChild.product_name.setText(productBean.getProduct_name());
        holderChild.product_code.setText(productBean.getProduct_code());
        holderChild.product_price.setText(productBean.getPrice()+"");
        holderChild.product_number.setText(productBean.getOut_number()+"");
        return view;
    }

    class ViewHolder {
//        public CheckBox cb_order_head;
        public TextView tv_order_code;
        public TextView tv_order_detail;

        public ImageView product_image;
        public TextView product_name;
        public TextView product_code;
        public TextView product_price;
        public TextView product_number;

    }

    /**
     * ExpandableListView 如果子条目需要响应click事件,必需返回true
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
