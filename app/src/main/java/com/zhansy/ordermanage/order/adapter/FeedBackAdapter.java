package com.zhansy.ordermanage.order.adapter;

import android.content.Context;
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
import com.zhansy.ordermanage.base.mvp.model.FeedBackBean;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.widget.RoundedImageView;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class FeedBackAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<FeedBackBean> feedBackBeanList;

    public FeedBackAdapter(Context context){
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
    }

    public void setList(List<FeedBackBean> list){
        if(feedBackBeanList != null){
            feedBackBeanList.clear();
        }
        this.feedBackBeanList = list;
    }

    public void addList(List<FeedBackBean> list){
        feedBackBeanList.addAll(list);
    }
    @Override
    public int getCount() {
        return feedBackBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedBackBeanList.get(position);
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
            convertView = mInflater.inflate(R.layout.feedback_item, null);
            holder.imgurl = (ImageView) convertView.findViewById(R.id.imgurl);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.describe = (TextView) convertView.findViewById(R.id.describe);
            holder.product_issue = (TextView) convertView.findViewById(R.id.product_issue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FeedBackBean bean = feedBackBeanList.get(position);
        ImageLoader.getInstance().displayImage(bean.getImgurl(), holder.imgurl,options);
        if (!bean.getUsername().isEmpty()){
            holder.username.setText(bean.getUsername());
        }
        holder.describe.setText(bean.getDescribe());
        holder.product_issue.setText(bean.getProduct_issue());
        return convertView;
    }

    class ViewHolder{
        public ImageView imgurl;
        public TextView username;
        public TextView describe;
        public TextView product_issue;
    }
}