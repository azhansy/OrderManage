package com.zhansy.ordermanage.utils;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by ZHANSY on 2016/4/19.
 * 搜索字体高亮
 * 使用方法 holder.name.setText(TextHighlightUtil.getHighlightText(result.getName(), mSearchKey));
 */
public class TextHighlightUtil {
    private TextHighlightUtil() {
    }

    /**
     * @param text 显示的所有字符
     * @param queryString 需要高亮的字符
     * @return 返回数据
     */
    public static SpannableString setHighlightText(String text,String queryString){
        return getHighlightText(text, queryString, 0xffF35050);
    }

    public static SpannableString getHighlightText(String text, String queryString, int colorResource) {
        SpannableString spanText = new SpannableString(text);
        if (TextUtils.isEmpty(queryString)) return spanText;
        int startIndex = text.toLowerCase().indexOf(queryString.toLowerCase());
        int endIndex = startIndex + queryString.length();
        if (startIndex > -1) {
            spanText.setSpan(new ForegroundColorSpan(colorResource), startIndex,
                    endIndex, 0);
        }

        return spanText;
    }
}
