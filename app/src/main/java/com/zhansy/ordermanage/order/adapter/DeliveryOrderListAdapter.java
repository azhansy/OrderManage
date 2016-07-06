package com.zhansy.ordermanage.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.OrderBean;
import com.zhansy.ordermanage.order.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com

public class DeliveryOrderListAdapter extends BaseAdapter{
    private DisplayImageOptions options;
    private Context mContext;
    private List<OrderBean> mList = new ArrayList<OrderBean>();
    private LayoutInflater mInflater;

    public DeliveryOrderListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        //构造函数中
        options =  new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_empty) // 设置图片下载期间显示的图
                .showImageOnLoading(R.drawable.ic_empty)
                .showImageForEmptyUri(R.drawable.tab_user_off) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_empty) // 设置图片加载或解码过程中发生错误显示的图
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .resetViewBeforeLoading(true)
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图
                .build(); // 创建配置过得DisplayImageOption对象
    }

    public void setList(List<OrderBean> list){
        if(mList != null){
            mList.clear();
        }
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_order_list_adapter, null);
            holder.order_code  = (TextView) convertView.findViewById(R.id.order_code);
            holder.order_detail  = (TextView) convertView.findViewById(R.id.order_detail);
            holder.order_name  = (TextView) convertView.findViewById(R.id.order_name);
            holder.order_userName  = (TextView) convertView.findViewById(R.id.order_userName);
            holder.order_total_money  = (TextView) convertView.findViewById(R.id.order_total_money);
            holder.order_time  = (TextView) convertView.findViewById(R.id.order_time);
            holder.order_type  = (TextView) convertView.findViewById(R.id.order_type);
//            holder.order_image = (ImageView) convertView.findViewById(R.id.order_image);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final OrderBean orderBean=mList.get(position);
        holder.order_code.setText(orderBean.getOrder_code());
        holder.order_name.setText(orderBean.getOrder_name());
        holder.order_userName.setText(orderBean.getUserName());
        holder.order_total_money.setText(orderBean.getTotal_price());
        holder.order_time.setText(orderBean.getCreatetime());
        holder.order_type.setText(orderBean.getOrder_type());
//        ImageLoader.getInstance().displayImage(orderBean.getOrder_image(),holder.order_image,options);
        holder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.launch(mContext,OrderDetailActivity.class,String.valueOf(orderBean.getOrder_id()));
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView order_code;
        TextView order_detail;
        TextView order_name;
        TextView order_userName;
        TextView order_total_money;//总价格
        TextView order_time;//时间
        TextView order_type;
//        ImageView order_image;
    }
}*/
