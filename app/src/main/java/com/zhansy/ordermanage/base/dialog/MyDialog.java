package com.zhansy.ordermanage.base.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.FlipEnter.FlipVerticalSwingEnter;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.MaterialTipDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.zhansy.ordermanage.R;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class MyDialog{

    public static NormalDialog myDialog;
    public static MaterialTipDialog materialTipDialog;

    public static void dimiss(){
        if (myDialog != null){
            myDialog.dismiss();
        }
        if (materialTipDialog != null){
            materialTipDialog.dismiss();
        }
    }
    /**提示对话框
     * @param title
     */
    public static void showDialog(Context context,String title){
        final MaterialTipDialog dialog = new MaterialTipDialog(context);
        FlipVerticalSwingEnter bas_in = new FlipVerticalSwingEnter();
        FadeExit bas_out = new FadeExit();
        dialog.content(/*"请选择产品后再提交"*/title)
                .cornerRadius(5)
                .showAnim(bas_in)//弹出形式动画
                .dismissAnim(bas_out)
                .show();
        dialog.setCanceledOnTouchOutside(false);
        materialTipDialog = dialog;
    }
    /**确认对话框
     * @param title
     */
    public static void showRightDialog(Context context,String title,OnBtnClickL btnClickL){
        final MaterialTipDialog dialog = new MaterialTipDialog(context);
        FlipVerticalSwingEnter bas_in = new FlipVerticalSwingEnter();
        FadeExit bas_out = new FadeExit();
        dialog.content(title)
                .cornerRadius(5)
                .showAnim(bas_in)//弹出形式动画
                .dismissAnim(bas_out)
                .show();
        dialog.setOnBtnClickL(btnClickL);
        dialog.setCanceledOnTouchOutside(false);
        materialTipDialog = dialog;
    }
    /**普通的对话框 "取消", "确定"
     * @param title
     * @param rightClickL
     */
    public static void showCustomDialog(Context context,String title,OnBtnRightClickL rightClickL){
        final NormalDialog dialog = new NormalDialog(context);
        FlipVerticalSwingEnter bas_in = new FlipVerticalSwingEnter();
        FadeExit bas_out = new FadeExit();
        dialog.content(/*"请选择产品后再提交"*/title)
                .btnText("取消", "确定")
                .cornerRadius(5)
                .showAnim(bas_in)//弹出形式动画
                .dismissAnim(bas_out)
                .show();
        dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                dialog.dismiss();
            }
        });
        dialog.setOnBtnRightClickL(rightClickL);
        dialog.setCanceledOnTouchOutside(false);
        myDialog = dialog;
    }

    /**复写的对话框
     * @param title
     * @param rightClickL
     */
    public static void showDoubleDialog(Context context,String title,String leftText,String rightText,OnBtnLeftClickL leftClickL,OnBtnRightClickL rightClickL){
        NormalDialog dialog = new NormalDialog(context);
        FlipVerticalSwingEnter bas_in = new FlipVerticalSwingEnter();
        FadeExit bas_out = new FadeExit();
        dialog.content(/*"请选择产品后再提交"*/title)
                .btnText(leftText,rightText)
                .cornerRadius(5)
                .showAnim(bas_in)//弹出形式动画
                .dismissAnim(bas_out)
                .show();
        dialog.setOnBtnLeftClickL(leftClickL);
        dialog.setOnBtnRightClickL(rightClickL);
        dialog.setCanceledOnTouchOutside(false);
        myDialog = dialog;
    }

    public static NormalListDialog  showNormalListDialog(Context context,String title,String[] stringItems) {
        final NormalListDialog dialog = new NormalListDialog(context, stringItems);
        dialog.title(title)//
                .titleTextSize_SP(24)//
                .titleBgColor(Color.parseColor("#409ED7"))//
                .itemPressColor(Color.parseColor("#85D3EF"))//
                .itemTextColor(Color.parseColor("#303030"))//
                .itemTextSize(18)//
                .cornerRadius(8)//
                .widthScale(0.8f)//
//                .setOnOperItemClickL()
                .show(R.style.myDialogAnim);
        return dialog;
//        dialog.setOnOperItemClickL(new OnOperItemClickL() {
//            @Override
//            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dialog.dismiss();
//            }
//        });
    }

//
//    public static void showThreeDialog(Context context,String title,String leftText,String rightText,OnBtnLeftClickL leftClickL,OnBtnRightClickL rightClickL){
//        final MaterialDialog dialog = new MaterialDialog(context);
////        ActionSheetDialog dialog = new ActionSheetDialog(context);
//        dialog.isTitleShow(false)//
////                .btnNum(3)
////                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
////                .btnText("确定", /*"取消",*/ "知道了")//
//                .showAnim(mBasIn)//
//                .dismissAnim(mBasOut)//
//                .show();
//
//        dialog.setUiBeforShow(
//                new OnBtnClickL() {//left btn click listener
//                    @Override
//                    public void onBtnClick() {
//                        T.showShort(mContext, "left");
//                        dialog.dismiss();
//                    }
//                },
//                new OnBtnClickL() {//right btn click listener
//                    @Override
//                    public void onBtnClick() {
//                        T.showShort(mContext, "right");
//                        dialog.dismiss();
//                    }
//                }
//                ,
//                new OnBtnClickL() {//middle btn click listener
//                    @Override
//                    public void onBtnClick() {
//                        T.showShort(mContext, "middle");
//                        dialog.dismiss();
//                    }
//                }
//        );
//    }


}
