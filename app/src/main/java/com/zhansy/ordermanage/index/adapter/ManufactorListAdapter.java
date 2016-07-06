//package com.zhansy.ordermanage.index.adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.mvp.model.UserBean;
//
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// *
// * 精选厂家适配器
// */
//public class ManufactorListAdapter extends BaseAdapter {
//    private DisplayImageOptions options;
//    private Context mContext;
//    private List<UserBean> userBeanList;
//    private LayoutInflater mInflater;
//
//    public ManufactorListAdapter(Context mContext) {
//        this.mContext = mContext;
//        this.mInflater = LayoutInflater.from(mContext);
//        //构造函数中
//        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_empty) // 设置图片下载期间显示的图
//                .showImageOnLoading(R.drawable.ic_empty)
//                .showImageForEmptyUri(R.drawable.tab_user_off) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_empty) // 设置图片加载或解码过程中发生错误显示的图
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                .resetViewBeforeLoading(true)
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图
//                .build(); // 创建配置过得DisplayImageOption对象
//    }
//
//    public void setList(List<UserBean> list) {
//        if (userBeanList != null) {
//            userBeanList.clear();
//        }
//        this.userBeanList = list;
//    }
//
//    @Override
//    public int getCount() {
//        return userBeanList.size() > 10 ? 10 : userBeanList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return userBeanList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = mInflater.inflate(R.layout.home_manufactor_item, null);
//            holder.tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
//            holder.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
//            holder.tv_company = (TextView) convertView.findViewById(R.id.tv_company);
//            holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        final UserBean bean = userBeanList.get(position);
//        holder.tv_grade.setText(bean.getUser_type()+"");
//        holder.tv_context.setText(bean.getPhone_number()+"");
//        holder.tv_company.setText(bean.getCompany()+"");
//        ImageLoader.getInstance().displayImage(bean.getImgurl(), holder.iv_user, options);
//        return convertView;
//    }
//
//    class ViewHolder {
//        ImageView iv_user;
//        TextView tv_company;
//        TextView tv_context;
//        TextView tv_grade;
//    }
//}
