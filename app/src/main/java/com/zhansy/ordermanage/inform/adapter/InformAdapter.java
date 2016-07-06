package com.zhansy.ordermanage.inform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.InformBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class InformAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<InformBean> informBeanList;

    public InformAdapter(Context context){
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

    public void delete(InformBean bean) {
        informBeanList.remove(bean);
        notifyDataSetChanged();
    }

    public void setList(List<InformBean> list){
        if(informBeanList != null){
            informBeanList.clear();
        }
        this.informBeanList = list;
    }
//    public void setSQLiteList(){
//        if(tasteReturnBeanList != null){
//            tasteReturnBeanList.clear();
//        }
//        this.tasteReturnBeanList = LitePalUtil.getDownLoadBeanList();
//    }

    public void addList(List<InformBean> list){
        informBeanList.addAll(list);
    }

    public List<InformBean> getList(){
        return informBeanList;
    }

    @Override
    public int getCount() {
        return informBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return informBeanList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_inform, null);
//            holder.image = (RoundedImageView) convertView.findViewById(R.id.image);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.inform_txt = (TextView) convertView.findViewById(R.id.inform_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InformBean bean = informBeanList.get(position);
        holder.tv_time.setText(bean.getCreatetime());
        holder.inform_txt.setText(bean.getContent());
        return convertView;
    }

    class ViewHolder{
        public TextView tv_time;
        public TextView inform_txt;
    }
}
