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
//import com.zhansy.ordermanage.base.mvp.model.ProductBean;
//
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class RecyclerViewAdapter extends BaseAdapter {
//    private DisplayImageOptions options;
//    private LayoutInflater mInflater;
//    private List<ProductBean> productBeanList;
//
//    public RecyclerViewAdapter(Context context){
//        mInflater = LayoutInflater.from(context);
//        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_empty) // 设置图片下载期间显示的图
//                .showImageOnLoading(R.drawable.ic_empty)
//                .showImageForEmptyUri(R.drawable.tab_user_off) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.ic_empty) // 设置图片加载或解码过程中发生错误显示的图
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                .resetViewBeforeLoading(true)
//                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图
//                .build(); // 创建配置过得DisplayImageOption对象
//    }
//
//    public void setList(List<ProductBean> list){
//        if(productBeanList != null){
//            productBeanList.clear();
//        }
//        this.productBeanList = list;
//    }
//    @Override
//    public int getCount() {
//        return productBeanList.size() > 24 ? 24 : productBeanList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return productBeanList.get(position);
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
//            convertView = mInflater.inflate(R.layout.night_item, null);
//            holder.iv_product_item = (ImageView) convertView.findViewById(R.id.iv_product_item);
//            holder.tv_product_item = (TextView) convertView.findViewById(R.id.tv_product_item);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        ProductBean bean = productBeanList.get(position);
//        holder.tv_product_item.setText(bean.getProduct_name());
//        ImageLoader.getInstance().displayImage(bean.getImgurl(), holder.iv_product_item, options);
//        return convertView;
//    }
//
//    class ViewHolder{
//        TextView tv_product_item;
//        ImageView iv_product_item;
//    }
//}
