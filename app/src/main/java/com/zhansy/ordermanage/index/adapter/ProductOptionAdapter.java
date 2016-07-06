package com.zhansy.ordermanage.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.mvp.model.ProductOptionsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class ProductOptionAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductOptionsBean> mList;
    private LayoutInflater mInflater;
    private GetPosion get;

    public ProductOptionAdapter(Context context) {
        this.mContext=context;
        this.mList=new ArrayList<ProductOptionsBean>();
        this.mInflater=LayoutInflater.from(context);
    }

    public void setList(List<ProductOptionsBean> list){
        this.mList.clear();
        this.mList.addAll(list);
    }
    /**
     * @param get the get to set
     */
    public void setGet(GetPosion get) {
        this.get = get;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int arg0, View converView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        if(converView==null){
            holder=new ViewHolder();
            if(mList.get(arg0).getChild_list()!=null) {
                converView = mInflater.inflate(R.layout.course_option, null);
            }else{
                converView = mInflater.inflate(R.layout.course_child_option, null);
            }
            holder.cateName=(TextView) converView.findViewById(R.id.cateName);
            converView.setTag(holder);
        }else{
            holder=(ViewHolder) converView.getTag();
        }
        holder.cateName.setText(mList.get(arg0).getType_name());
		/*if(arg0==0){
			holder.cateName.setTextColor(mContext.getResources().getColor(R.color.bg_title));
		}else{
			holder.cateName.setTextColor(mContext.getResources().getColor(R.color.course_list_value_color));
		}*/
        if(mList.get(arg0).getChild_list()!=null) {
            if (get.getLine() == arg0) {
                holder.cateName.setTextColor(mContext.getResources().getColor(R.color.bg_title));
            } else {
                holder.cateName.setTextColor(mContext.getResources().getColor(R.color.course_list_value_color));
            }
        }else{
            if (get.getChileCateLine() == arg0) {
                holder.cateName.setTextColor(mContext.getResources().getColor(R.color.bg_title));
            } else {
                holder.cateName.setTextColor(mContext.getResources().getColor(R.color.course_list_value_color));
            }
        }

        return converView;
    }

    public interface GetPosion{
        int getLine();
        int getChileCateLine();
    }

    class ViewHolder{
        TextView cateName;
    }
}
