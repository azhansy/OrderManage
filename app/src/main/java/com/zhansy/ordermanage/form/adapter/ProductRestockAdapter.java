package com.zhansy.ordermanage.form.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.ProductBean;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductRestockAdapter extends BaseAdapter {
    private DisplayImageOptions options;
    private LayoutInflater mInflater;
    private List<ProductBean> productBeanList = new ArrayList<ProductBean>();
    private CallBack callBack; //回调是否为全选
//    private Map<Integer, Boolean> checkStatusMap = new HashMap<Integer, Boolean>();

    public ProductRestockAdapter(Context context,CallBack callBack){
        this.callBack = callBack;
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

    public void setList(List<ProductBean> list){
        if(productBeanList != null){
            productBeanList.clear();
        }
        productBeanList.addAll(list);
//        int pos = 0;
//        for(int i = 0; i < productBeanList.size(); i++){
//            checkStatusMap.put(pos++, false);
//        }
    }

    /**
     * @return 选中的产品
     */
    public List<ProductBean> getChoosedProduct(){
        List<ProductBean> list = new  ArrayList<>();
        if (productBeanList ==null |productBeanList.size()==0){
            return list;
        }
        for (int i=0; i<productBeanList.size();i++){
            if (productBeanList.get(i).isChoosed()){
                list.add(productBeanList.get(i));
            }
        }
        return list;
    }

    /**移除购物车
     * @param position
     */
    public void remove(int position){
        LitePalUtil.deleteProduct(productBeanList.get(position).getId());
        productBeanList.remove(position);
        notifyDataSetChanged();
    }
    public void clear(){
        if (productBeanList != null){
            LitePalUtil.deleteAllProduct();
            productBeanList.clear();
        }
        notifyDataSetChanged();
    }
    public List<ProductBean> getList(){
        return productBeanList;
    }
    private boolean allItemSelect(){
        for (int i = 0; i < productBeanList.size(); i++) {
            if (!productBeanList.get(i).isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 设置点击全选按钮后，这个item的选中状态
     */
    public void WeatherCheckedAll(boolean isAllTrue){
        if (productBeanList == null)
            return;
        for (int i = 0; i < productBeanList.size(); i++) {
            productBeanList.get(i).setChoosed(isAllTrue);
        }
        notifyDataSetChanged();
    }
//    /**
//     * @param b 设置item想checkbox状态
//     */
//    public void triggerSetCheck(boolean b){
//        if (productBeanList == null)
//            return;
//        int pos = 0;
//        if (b){
//            for(int i = 0; i < productBeanList.size(); i++){
//                checkStatusMap.put(pos++, true);
//            }
//        }else {
//            for(int i = 0; i < productBeanList.size(); i++){
//                checkStatusMap.put(pos++, false);
//            }
//        }
//        notifyDataSetChanged();
//    }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            CommonUtil.showLog("重新加载view----------");
            convertView = mInflater.inflate(R.layout.product_restock_item, null);
            holder.cb_product = (CheckBox) convertView.findViewById(R.id.cb_product);
            holder.product_image = (ImageView) convertView.findViewById(R.id.product_image);
            holder.product_name = (TextView) convertView.findViewById(R.id.product_name);
            holder.product_remark = (TextView) convertView.findViewById(R.id.product_remark);
            holder.total_product_price = (TextView) convertView.findViewById(R.id.total_product_price);
            holder.product_price = (TextView) convertView.findViewById(R.id.product_price);
            holder.product_out_number = (TextView) convertView.findViewById(R.id.product_out_number);
//            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(holder);
        } else {
            CommonUtil.showLog("复用view----------");
            holder = (ViewHolder) convertView.getTag();
        }
        final ProductBean bean = productBeanList.get(position);
        ImageLoader.getInstance().displayImage(bean.getImgurl(), holder.product_image);
        holder.product_name.setText(bean.getProduct_name());
        holder.product_remark.setText(bean.getRemark());
        holder.product_price.setText(bean.getPrice()+"");
        if (String.valueOf(bean.getOut_number()).equals("")){
            holder.product_out_number.setText("0");
            holder.total_product_price.setText("0");
        }else {
            holder.product_out_number.setText(bean.getOut_number()+"");
            holder.total_product_price.setText(bean.getPrice() * bean.getOut_number()+"");
        }
        //checkbox操作
        holder.cb_product.setChecked(bean.isChoosed());
        holder.cb_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bean.setChoosed(true);
                }else {
                    bean.setChoosed(false);
                }
//                checkStatusMap.put(position, isChecked);
                    //是否显示标题全选按钮
                    callBack.triggerSetCheck(allItemSelect());
            }
        });
//        //删除操作
//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //删除子item
//
//            }
//        });
        return convertView;
    }

    class ViewHolder{
        public CheckBox cb_product;
        public ImageView product_image;
        public TextView product_name;
        public TextView total_product_price;
        public TextView product_price;
        public TextView product_remark;
        public TextView product_out_number;
    }

    public interface CallBack{
        void triggerSetCheck(boolean b);
    }
}