package com.zhansy.ordermanage.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.zhansy.ordermanage.R;
import com.zhansy.ordermanage.alert.NoticeActivity;
import com.zhansy.ordermanage.download.DownLoadActivity;

/**
 * Created by Administrator on 2016/4/11.
 */
public class NotificationUtil {
//    Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
//    Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
//    Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
//    Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
//    Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
//    Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
//    Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务

    public static void getAlertNotification(Context context,String tip){
        //获取通知栏管理
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //实例化通知栏构造器
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //对Builder进行配置
        mBuilder.setContentTitle("低库存报警")//设置通知栏标题
                .setContentText(tip) //设置通知栏显示内容
                .setContentIntent(getAlertIntent(context,Notification.FLAG_ONLY_ALERT_ONCE)) //设置通知栏点击意图
                //  .setNumber(number) //设置通知集合的数量
                .setTicker("低库存报警触发") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        notificationManager.notify(1, mBuilder.build());
    }
    public static PendingIntent getAlertIntent(Context context,int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(context,NoticeActivity.class), flags);
        return pendingIntent;
    }

    /**
     * 下载通知栏
     * @param context 上下文
     * @param tip 成功或失败 信息
     */
    public static void getDownloadNotification(Context context,String tip){
        //获取通知栏管理
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //实例化通知栏构造器
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        //对Builder进行配置
        mBuilder.setContentTitle("下载提醒")//设置通知栏标题
                .setContentText(tip) //设置通知栏显示内容
                .setContentIntent(getDownloadIntent(context,Notification.FLAG_ONLY_ALERT_ONCE)) //设置通知栏点击意图
                //  .setNumber(number) //设置通知集合的数量
                .setTicker("下载提醒") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        notificationManager.notify(1, mBuilder.build());
    }
    public static PendingIntent getDownloadIntent(Context context,int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(context,DownLoadActivity.class), flags);
        return pendingIntent;
    }
}
