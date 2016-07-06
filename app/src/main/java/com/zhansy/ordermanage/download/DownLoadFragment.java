package com.zhansy.ordermanage.download;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.base.MVPBaseFragment;
import com.zhansy.ordermanage.base.dialog.MyDialog;
import com.zhansy.ordermanage.base.mvp.model.DownLoadBean;
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter;
import com.zhansy.ordermanage.db.LitePalUtil;
import com.zhansy.ordermanage.download.adpater.DownLoadAdapter;
import com.zhansy.ordermanage.utils.CommonUtil;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemLongClick;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class DownLoadFragment extends MVPBaseFragment {
    @InjectView(R.id.list_download)
    ListView list_download;

    @OnClick(R.id.btn_back)
    void OnClick(){
        getActivity().finish();
    }
    public static DownLoadFragment newInstance() {
        DownLoadFragment fragment = new DownLoadFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DownLoadAdapter adapter = new DownLoadAdapter(getActivity());
        adapter.setSQLiteDownLoadBeanList();
        list_download.setAdapter(adapter);
        //点击打开
        list_download.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownLoadBean downLoadBean = (DownLoadBean)adapter.getItem(position);
                File file = new File(downLoadBean.getPath());
                if (!file.exists()){
                    return;
                }
                CommonUtil.openFile(file);
            }
        });
        //长按删除
        list_download.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final DownLoadBean downLoadBean = (DownLoadBean)adapter.getItem(position);
                MyDialog.showCustomDialog(getActivity(), "确定删除下载文件：" + downLoadBean.getName() + "  ？", new OnBtnRightClickL() {
                    @Override
                    public void onBtnRightClick() {
                        File file = new File(downLoadBean.getPath());
                        if (!file.exists()){
                            return ;
                        }
                        file.delete();
                        LitePalUtil.deletetDownLoadBean(downLoadBean.getId());
                        adapter.delete(downLoadBean);
                        MyDialog.dimiss();
                    }
                });

                return false;
            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_download;
    }

    @Override
    protected boolean usePresenter() {
        return false;
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }
}
