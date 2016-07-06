//package com.zhansy.ordermanage.db;
//
//import com.zhansy.ordermanage.base.mvp.model.TasteReturnBean;
//import com.zhansy.ordermanage.base.mvp.model.AlertLowBean;
//import com.zhansy.ordermanage.base.mvp.model.OrderBean;
//import com.zhansy.ordermanage.base.mvp.model.ProductBean;
//import com.zhansy.ordermanage.base.mvp.model.UserBean;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by ZHANSY
// * 邮箱：azhansy@hotmail.com
// *
// * 添加本地数据
// */
//public class TestData {
////    public static List<ProductBean> productBeanList = addProduct(50);
////    public static List<OrderBean> orderBeanList = addOrder(20);
////    public static List<UserBean> userBeanList = addUser(10);
////    public static List<FeedBackBean> feedBackBeanList = addFeedBack(20);
//
//    public static void addData(){
//        textAd();
//        addProduct(10);
////        addOrder(30);
//        addUser(20);
////        addFeedBack(50);
////        addNoticeBean(20);
//
//
//    }
//
//    /**
//     * @param number 添加用户
//     */
//    private static List<UserBean> addUser(int number){
//        List<UserBean> list = new ArrayList<UserBean>();
//        for (int j = 0; j < number; j++) {
//            UserBean userBean = new UserBean();
//            userBean.setUser_id(201241+j);
//            userBean.setUser_code("20124140221"+j);
//            if (j == 4){
//                userBean.setUser_type("admin");
//            }else if (j<6){
//                userBean.setUser_type("user");
//            }else {
//                userBean.setUser_type("user");
//            }
//            userBean.setUsername("123"+j);
//            userBean.setActual_name("暂无真实名字");
//            userBean.setPassword("123"+j);
//            userBean.setPhone_number("1375133019"+j);
//            userBean.setUser_address("东莞理工学院，宿舍号为 " +j + " 号");
//            userBean.setImgurl("http://tx.haiqq.com/uploads/allimg/141124/1G3591291-6.jpg");
//            userBean.setInput_user("管理员"+j+"号");
//            userBean.save();
//            list.add(userBean);
//        }
//        return list;
//    }
//
//    /**
//     * @param number 警报列表
//     */
//    private static List<AlertLowBean> addNoticeBean(int number){
//        List<AlertLowBean> list = new ArrayList<AlertLowBean>();
//        for (int i = 0; i < number; i++) {
//            AlertLowBean noticeBean = new AlertLowBean();
//            noticeBean.setCreatetime(new Date());
//            noticeBean.setAlert_id(100+i);
//            if (i%2 == 0) {
//                noticeBean.setContent("快快下单，库存快没有啦啦");
//            }else {
//                noticeBean.setContent("产品大特卖，最后剩余不多啦啦");
//            }
//            noticeBean.save();
//            list.add(noticeBean);
//        }
//        return list;
//    }
////    /**
////     * @param number 添加反馈
////     */
////    private static List<FeedBackBean> addFeedBack(int number){
////        List<FeedBackBean> list = new ArrayList<FeedBackBean>();
////        for (int i = 0; i < number; i++) {
////            FeedBackBean feedBackBean = new FeedBackBean();
////            feedBackBean.setCreatetime(new Date());
////            if (i%2 == 0) {
////                feedBackBean.setDescribe("这个纸的质量，我回家用几天就坏了" + i);
////                feedBackBean.setProduct_issue("烧坏了" + i);
////                feedBackBean.setImgurl("http://tupian.enterdesk.com/2013/lxy/12/30/2/5.jpg");
////            }else {
////                feedBackBean.setDescribe("什么破玩意，用用就坏了" + i);
////                feedBackBean.setProduct_issue("太薄了" + i);
////                feedBackBean.setImgurl("http://tx.haiqq.com/uploads/allimg/141124/1G3591291-6.jpg");
////            }
////            feedBackBean.save();
////            list.add(feedBackBean);
////        }
////        return list;
////    }
//    /**
//     * @param number 添加产品
//     */
//    private static List<ProductBean>  addProduct(int number){
//        List<ProductBean> list = new ArrayList<ProductBean>();
//        for (int j = 0; j < number; j++) {
//            ProductBean productBean = new ProductBean();
//            productBean.setProduct_id(number+100+j);
//            productBean.setProduct_code(number+"100" + j);
//            productBean.setGeneration(number+"20150301" +j);
//            productBean.setProduct_name(number+"优质作业薄" + j);
//            productBean.setPrice(4 + j);
//            if (j<6){
//            }
////            productBean.setIn_number(0);
////            productBean.setOut_number(0);
//            productBean.setOut_time(new Date());
//            productBean.setInput_user("员工00"+j+"号");
//            productBean.setImgurl("http://tupian.enterdesk.com/2013/lxy/12/30/2/5.jpg");
//            productBean.setOut_time(new Date());
//            productBean.setStock(100000000);
//            productBean.setRemark("这是产品的录入情况，默认进出货数量为0，库存一个亿"+j);
//            productBean.save();
//            list.add(productBean);
//        }
//        return list;
//    }
//    /**
//     * @param number 添加订单
//     */
//    private static List<OrderBean> addOrder(int number){
//        List<OrderBean> list = new ArrayList<OrderBean>();
//        for (int j = 0; j < number; j++) {
//            //订单数据
//            OrderBean orderBean = new OrderBean();
//            orderBean.setOrder_id(801272101+j);
//            orderBean.setOrder_code("10011" + j);
//            orderBean.setCreatetime(new Date()+"");
//            if (j == 1){
//                orderBean.setOrder_type("待付款");
//                orderBean.setPay_mode("微信支付");
//            }else if(j == 2){
//                orderBean.setOrder_type("待发货");
//                orderBean.setPay_mode("支付宝支付");
//            }else if (j % 3 == 0){
//                orderBean.setOrder_type("待收货");
//                orderBean.setPay_mode("微信支付");
//            }else if (j % 4 == 0){
//                orderBean.setOrder_type("待评价");
//                orderBean.setPay_mode("支付宝支付");
//            }else {
//                orderBean.setOrder_type("已完成");
//                orderBean.setPay_mode("支付宝支付");
//            }
//            orderBean.setPostcode("524051"+j);
//            orderBean.setRemark("备注"+j+"默认总价1999");
//            orderBean.setTotal_price(1999);
//            orderBean.setInput_user("工作人员"+j+"号");
//            orderBean.save();
//            list.add(orderBean);
//        }
//        return list;
//    }
//}
