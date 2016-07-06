//package com.zhansy.ordermanage.base.business;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// */
//public abstract class BaseNoHttpBusiness {
//    protected Context mContext;
//
//    public BaseNoHttpBusiness(Context context) {
//        this.mContext = context;
//    }
//
//    public void execute() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                BaseNoHttpBusiness.this.doInBackground();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                BaseNoHttpBusiness.this.onPostExecute();
//            }
////        }.execute();
//        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }
//
//    /**
//     * 耗时操作放这里
//     */
//    protected abstract void doInBackground();
//
//
//    protected void onPostExecute() {}
//}
