package com.zhansy.ordermanage.admin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import com.zhansy.ordermanage.R
import com.zhansy.ordermanage.base.MVPBaseActivity
import com.zhansy.ordermanage.base.mvp.presenter.MVPBasePresenter
import com.zhansy.ordermanage.base.mvp.view.IBaseUi

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
class MemberActivity : MVPBaseActivity<MVPBasePresenter>() {
    override fun getLayoutResource(): Int {
        return R.layout.activity_base
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().add(R.id.container, MemberAdminFragment.getInstance()).commit()
    }

    override fun usePresenter(): Boolean {
        return false
    }

    override fun createPresenter(): MVPBasePresenter? {
        return null
    }
    //静态方法，变量
    companion object {
        fun launch(context: Context,gid: String){
            val intent = Intent(context, MemberActivity::class.java)
            if (!TextUtils.isEmpty(gid)) {
                intent.putExtra(IBaseUi.KEY_GID, gid)
            }
            if(context !is Activity){
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

}
