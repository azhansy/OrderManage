package com.zhansy.ordermanage.alert.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.highlight.Highlight;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.model.AlertLowBean;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.utils.TextHighlightUtil;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class NoticeActivityAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<AlertLowBean> alertLowBeanList;

    public NoticeActivityAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_empty) // 设置图片下载期间显示的图
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
        setList(LitePalUtil.getAlertLowBeanList());
    }

    public void setList(List<AlertLowBean> list){
        if(alertLowBeanList != null){
            alertLowBeanList.clear();
        }
        this.alertLowBeanList = list;
    }
    public void addList(List<AlertLowBean> list){
        alertLowBeanList.addAll(list);
    }
    public void remove(AlertLowBean bean){
        alertLowBeanList.remove(bean);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return alertLowBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return alertLowBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.notice_item, null);
            holder.product_code = (TextView) convertView.findViewById(R.id.product_code);
            holder.notice_time = (TextView) convertView.findViewById(R.id.notice_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AlertLowBean bean = alertLowBeanList.get(position);
        holder.product_code.setText(TextHighlightUtil.getHighlightText(bean.getContent(), bean.getProduct_name(), OMApplication.getInstance().getResources().getColor(R.color.themeApp)));
        holder.notice_time.setText(bean.getCreatetime()+"");
        return convertView;
    }

    class ViewHolder{
        public TextView product_code;
        public TextView notice_time;
    }
}