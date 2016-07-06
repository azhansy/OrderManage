package com.zhansy.ordermanage.download.adpater;

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
import com.zhansy.ordermanage.base.OMApplication;
import com.zhansy.ordermanage.base.mvp.model.DownLoadBean;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.widget.RoundedImageView;
import com.zhansy.ordermanage.db.LitePalUtil;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class DownLoadAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DownLoadBean> downLoadBeenList;

    public DownLoadAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void delete(DownLoadBean downLoadBean) {
        downLoadBeenList.remove(downLoadBean);
        notifyDataSetChanged();
    }

    public void setList(List<DownLoadBean> list){
        if(downLoadBeenList != null){
            downLoadBeenList.clear();
        }
        this.downLoadBeenList = list;
    }
    public void setSQLiteDownLoadBeanList(){
        if(downLoadBeenList != null){
            downLoadBeenList.clear();
        }
        this.downLoadBeenList = LitePalUtil.getDownLoadBeanList();
    }

    public void addList(List<DownLoadBean> list){
        downLoadBeenList.addAll(list);
    }

    public List<DownLoadBean> getList(){
        return downLoadBeenList;
    }

    @Override
    public int getCount() {
        return downLoadBeenList.size();
    }

    @Override
    public Object getItem(int position) {
        return downLoadBeenList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_download, null);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.download_name = (TextView) convertView.findViewById(R.id.download_name);
            holder.download_time = (TextView) convertView.findViewById(R.id.download_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DownLoadBean bean = downLoadBeenList.get(position);
        if ("Excel".equals(bean.getType())){
            holder.download_name.setTextColor(OMApplication.getInstance().getResources().getColor(R.color.themeApp));
            holder.image.setImageResource(R.mipmap.ic_excel);
        }
        if ("app".equals(bean.getType())){
            holder.download_name.setTextColor(OMApplication.getInstance().getResources().getColor(R.color.colorPrimary));
            holder.image.setImageResource(R.mipmap.ic_apk);
        }
        holder.download_name.setText(bean.getName());
        holder.download_time.setText(bean.getTime());
        return convertView;
    }

    class ViewHolder{
        public TextView download_name;
        public TextView download_time;
        public ImageView image;
    }
}