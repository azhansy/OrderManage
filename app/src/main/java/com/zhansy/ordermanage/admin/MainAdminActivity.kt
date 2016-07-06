package com.zhansy.ordermanage.admin

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView

import com.nostra13.universalimageloader.core.ImageLoader
import com.zhansy.ordermanage.R
import com.zhansy.ordermanage.base.MVPBaseActivity
import com.zhansy.ordermanage.base.OMApplication
import com.zhansy.ordermanage.base.mvp.model.InformBean
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter
import com.zhansy.ordermanage.chart.MpChartActivity
import com.zhansy.ordermanage.db.LitePalUtil
import com.zhansy.ordermanage.download.DownLoadActivity
import com.zhansy.ordermanage.me.MeActivity
import com.zhansy.ordermanage.inform.InformActivity
import com.zhansy.ordermanage.order.OrderClassifyActivity
import com.zhansy.ordermanage.order.OrderCompleteActivity
import com.zhansy.ordermanage.order.FeedbackAdminActivity
import com.zhansy.ordermanage.order.OrderSearchActivity
import com.zhansy.ordermanage.product.ProductInputActivitity
import com.zhansy.ordermanage.product.StockActivity
import com.zhansy.ordermanage.index.BuyProductActivity
import com.zhansy.ordermanage.taste.TasteActivity
import com.zhansy.ordermanage.utils.ToastUtil

import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

import butterknife.InjectView
import butterknife.OnClick
import kotlinx.android.synthetic.main.layout_main_admin.*
import kotlinx.android.synthetic.main.layout_menu.*

class MainAdminActivity : MVPBaseActivity<MVPBasePresenter>(), View.OnClickListener{

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_order_shenhe -> OrderClassifyActivity.launch(this, OrderClassifyActivity::class.java, "rl_noDelivery")
            R.id.rl_order_search -> OrderSearchActivity.launch(this, OrderSearchActivity::class.java) //订单查询
            R.id.rl_buy_product -> BuyProductActivity.launch(this, BuyProductActivity::class.java)
            R.id.rl_2 -> ProductInputActivitity.launch(this, ProductInputActivitity::class.java)
            R.id.rl_order_complete -> OrderCompleteActivity.launch(this, OrderCompleteActivity::class.java, "lr_complete")//已完成订单
            R.id.rl_user_manage -> MemberActivity.launch(this, "")//会员查询
            R.id.rl_5 -> StockActivity.launch(this, StockActivity::class.java)
            R.id.rl_6 -> FeedbackAdminActivity.launch(this, FeedbackAdminActivity::class.java)
            R.id.rl_order_chart -> MpChartActivity.launch(this, MpChartActivity::class.java)
            R.id.rel_get_advice -> TasteActivity.launch(this, TasteActivity::class.java)
            R.id.rl_add_msg -> InformActivity.launch(this, InformActivity::class.java)
            R.id.rl_order_download -> DownLoadActivity.launch(this, DownLoadActivity::class.java)
            R.id.rel_me_setting, R.id.rel_setting -> MeActivity.launch(this, MeActivity::class.java)
        }
    }

    //        val tv_notice by lazy{find<TextView>(R.id.tv_notice)}

    override fun getLayoutResource(): Int {
        return R.layout.activity_admin_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        updateNotice()
    }

    private fun init() {
        val user = OMApplication.getInstance().currentUser
        ImageLoader.getInstance().displayImage(user.imgurl, biv_user_icon)
        tv_user_name.text = user.username + "  (" + user.user_type + ")"
        tv_user_phone.text = user.phone_number
        tv_user_address.text = user.user_address
        tv_user_company.text = user.company
        tv_postcode.text = user.postcode
        ic_manage.setImageResource(R.mipmap.ic_super_manage)
    }

    /**
     * 更新通知
     */
    private fun updateNotice() {
        var informBeanList: List<InformBean> = ArrayList()
        informBeanList = LitePalUtil.getNoticeBeanList()
        if (informBeanList.size != 0) {
            var content = ""
            for (i in informBeanList.indices) {
                content = content + informBeanList[i].content + "        "
            }
            tv_notice.text = content
        }
    }

    override fun usePresenter(): Boolean {
        return false
    }

    override fun createPresenter(): MVPBasePresenter? {
        return null
    }

    /**
     * 菜单、返回键响应
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click() //调用双击退出函数
        }
        return false
    }

    private fun exitBy2Click() {
        var tExit: Timer? = null
        if (isExit === false) {
            isExit = true // 准备退出
            ToastUtil.showToast(this, "再按一次退出程序")
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false // 取消退出
                }
            }, 2000) // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            appManager.ExitApp(this)
        }
    }

    companion object {
        /**
         * 双击退出函数
         */
        private var isExit: Boolean? = false
    }
}
