package com.zhansy.ordermanage.index.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.base.widget.RoundedImageView;

import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<ProductBean> productBeanList;

    public ProductAdapter(Context context){
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
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图
                .build(); // 创建配置过得DisplayImageOption对象
    }

    public void setList(List<ProductBean> list){
        if(productBeanList != null){
            productBeanList.clear();
        }
        this.productBeanList = list;
    }

    public void addList(List<ProductBean> list){
        productBeanList.addAll(list);
    }

    public List<ProductBean> getList(){
        return productBeanList;
    }

    @Override
    public int getCount() {
        return productBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return productBeanList.get(position);
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
            convertView = mInflater.inflate(R.layout.product_item, null);
            holder.product_image = (RoundedImageView) convertView.findViewById(R.id.product_image);
            holder.product_name = (TextView) convertView.findViewById(R.id.product_name);
            holder.product_code = (TextView) convertView.findViewById(R.id.product_code);
            holder.product_price = (TextView) convertView.findViewById(R.id.product_price);
            holder.product_stock = (TextView) convertView.findViewById(R.id.product_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductBean bean = productBeanList.get(position);
        ImageLoader.getInstance().displayImage(bean.getImgurl(), holder.product_image,options);
        holder.product_name.setText(bean.getProduct_name());
        holder.product_code.setText(bean.getProduct_code());
        holder.product_price.setText(bean.getPrice()+"");
        holder.product_stock.setText(bean.getStock()+"");
        return convertView;
    }

    class ViewHolder{
        public RoundedImageView product_image;
        public TextView product_name;
        public TextView product_code;
        public TextView product_price;
        public TextView product_stock;
    }
}