package com.zhansy.ordermanage.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZHANSY on 2016/4/21.
 * 正则表达式
 * 参考 @link http://baike.baidu.com/link?url=BgOAl-fXA7hU7XtUAJ41uL0vhNE3pQEACXtuwojR3oxfdL0dsXWLOIoQLqGgX-hAy2Myun3Lw3a6rPpKtc3lE_
 *  ？匹配前面的子表达式零次或一次
 *  *是一个特殊字符,匹配任意个字符(包括0个字符) 0到多次
 *  +大于一次
 *  { n }n是一个非负整数。匹配确定的n次 例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o
 *  { n,} n是一个非负整数。至少匹配n次 “o{2,}”不能匹配“Bob”中的“o”，但能匹配“foooood”中的所有o。“o{1,}”等价于“o+”。“o{0,}”则等价于“o*”。
 *  {n , m} m和n均为非负整数，其中n<=m。最少匹配n次且最多匹配m次
 *  ^ 意义：表示匹配的字符必须在最前边 开始
 *  $   用来匹配字符串的 结束
 * \d 表示0-9 任意一个数字
 */
public class PattenUtil {
    /**
     * @param postcode 6位有效的邮政编码
     * @return
     */

    public static boolean validatePostcode(String postcode) {
        boolean result = false;
        Pattern p = Pattern.compile("^[0-9]\\d{5}$"); //在“”中要\一次，在执行正则匹配时，还要\一次，表达的是前面的任意字符
        Matcher m = p.matcher(postcode);
        result = m.matches();
        return result;
    }

    /**
     * @param password 正则表达式：检测密码由6-10字母和数字组成
     * @return
     */
    public static boolean validatePassword(String password) {
        boolean result = false;
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$");
        Matcher m = p.matcher(password);
        result = m.matches();
        return result;
    }


    /**
     * @param mobiles 手机号的正则
     * @return
     */
    public static boolean validateMobileNO(String mobiles) {
        boolean result = false;
//        Pattern p = Pattern.compile("^(((13[0-9])|(15[^4,\\D])|(18[0-9])|(147)|(170))|((0)(13[0-9])|(15[^4,\\D])|(18[0-9])|(147)|(170)))\\d{8}$");
        Pattern p = Pattern.compile("^[0-9]\\d{10}$");
        Matcher m = p.matcher(mobiles);
        result = m.matches();
        return result;
    }


}
