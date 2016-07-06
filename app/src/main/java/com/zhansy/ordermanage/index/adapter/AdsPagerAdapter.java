//package com.zhansy.ordermanage.index.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
//import com.zhansy.ordermanage.R;
//import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
//import com.zhansy.ordermanage.index.WebActivity;
//import com.zhansy.ordermanage.utils.ToastUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public class AdsPagerAdapter extends PagerAdapter{
//    private List<TasteReturnBean> adinfoBeanList;
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private ImageLoader mImageLoader = ImageLoader.getInstance();
//    private DisplayImageOptions mOptions;
//
//    public AdsPagerAdapter(List<TasteReturnBean> list,Context mContext) {
//        this.adinfoBeanList = list;
//        this.mContext = mContext;
//        mInflater = LayoutInflater.from(mContext);
////        mOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_empty) // 设置图片下载期间显示的图
////                .showImageOnLoading(R.drawable.ic_empty)
////                .showImageForEmptyUri(R.drawable.tab_user_off) // 设置图片Uri为空或是错误的时候显示的图片
////                .showImageOnFail(R.drawable.ic_empty) // 设置图片加载或解码过程中发生错误显示的图
////                .bitmapConfig(Bitmap.Config.RGB_565)
////                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
////                .cacheInMemory(true) // 设置下载的图片是否缓存在内存
////                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
////                .resetViewBeforeLoading(true)
////                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图
////                .build(); // 创建配置过得DisplayImageOption对象
//    }
//
//    @Override
//    public int getCount() {
//        return adinfoBeanList.size();
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((ViewPager)container).removeView((View)object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, final int position) {
////        return super.instantiateItem(container, position);
//        View imageLayout = mInflater.inflate(R.layout.home_ads_view, null);
//        ImageView imageView = (ImageView) imageLayout .findViewById(R.id.ads_view);
//        if (adinfoBeanList.size() == 0) {
//            return imageLayout;
//        }
//        final TasteReturnBean homeAdsVo = adinfoBeanList.get(position);
//
//        mImageLoader.displayImage(homeAdsVo.getPic(), imageView);
//        imageView.setTag(homeAdsVo);
//        imageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                ToastUtil.showToast(mContext,"点击广告页"+position);
//                WebActivity.launch(mContext,WebActivity.class,homeAdsVo.getUrl());
//            }
//        });
//        container.addView(imageLayout);
//        return imageLayout;
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//}
