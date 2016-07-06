package com.zhansy.ordermanage.admin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.zhansy.ordermanage.base.mvp.model.UserBean;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class MemberAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<UserBean> userBeanList;

    private Drawable ic_user_manage,ic_super_manage;

    public MemberAdapter(Context context){
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
        ic_user_manage = context.getResources().getDrawable(R.mipmap.ic_user_manage);
        ic_user_manage.setBounds(0, 0, ic_user_manage.getMinimumWidth(), ic_user_manage.getMinimumHeight());
        ic_super_manage = context.getResources().getDrawable(R.mipmap.ic_super_manage);
        ic_super_manage.setBounds(0, 0, ic_super_manage.getMinimumWidth(), ic_super_manage.getMinimumHeight());
    }

    public void setList(List<UserBean> list){
        if(userBeanList != null){
            userBeanList.clear();
        }
        this.userBeanList = list;
    }
    public void addList(List<UserBean> list){
        userBeanList.addAll(list);
    }
    public List<UserBean> getList(){
        return this.userBeanList;
    }
    @Override
    public int getCount() {
        return userBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return userBeanList.get(position);
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
            convertView = mInflater.inflate(R.layout.fragment_user_member_item, null);
            holder.user_image = (ImageView) convertView.findViewById(R.id.user_image);
            holder.company = (TextView) convertView.findViewById(R.id.company);
            holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            holder.phone_number = (TextView) convertView.findViewById(R.id.phone_number);
            holder.user_code = (TextView) convertView.findViewById(R.id.user_code);
            holder.input_user = (TextView) convertView.findViewById(R.id.input_user);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserBean bean = userBeanList.get(position);
        ImageLoader.getInstance().displayImage(bean.getImgurl(),holder.user_image,options);
        holder.company.setText(bean.getCompany());
        holder.user_name.setText(bean.getUsername());
        if (bean.isManage()){
            holder.user_name.setCompoundDrawables(null, null, ic_super_manage, null); //设置右图标
        }else {
            holder.user_name.setCompoundDrawables(null, null, ic_user_manage, null); //设置右图标
        }
        holder.phone_number.setText(bean.getPhone_number());
        holder.user_code.setText(bean.getUser_code());
        holder.input_user.setText(bean.getInput_user());
        return convertView;
    }

    class ViewHolder{
        ImageView user_image;
        TextView company;
        TextView user_name;
        TextView phone_number;
        TextView user_code;
        TextView input_user;

    }
}