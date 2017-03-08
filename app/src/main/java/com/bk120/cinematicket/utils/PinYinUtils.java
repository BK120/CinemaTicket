package com.bk120.cinematicket.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk120 on 2017/3/3.
 * 汉字转换成拼音工具类
 */

public class PinYinUtils {
    private static HanyuPinyinOutputFormat format = null;
    static {
        format = new HanyuPinyinOutputFormat();
        //拼音小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //无音标方式；WITH_TONE_NUMBER：1-4数字表示英标；WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //用v表示ü
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * @param str
     * @return
     * @Description: 返回字符串的拼音
     */
    public static String[] getCharPinYinString(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        //对字符串中的记录逐个分析
        for (int i = 0; i < str.length(); i++) {
            result = getCharPinYinString(str.charAt(i), result);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * @param c
     * @param list
     * @return
     * @Description: 将字符c的拼音拼接到list中的记录中
     */
    private static List<String> getCharPinYinString(char c, List<String> list) {
        String[] strs = getCharPinYinString(c);
        List<String> result = new ArrayList<String>();
        //如果解析出的拼音为空，判断字符C是否为英文字母，如果是英文字母则添加值拼音结果中
        if (strs == null) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                c = c <= 91 ? (char)(c + 32) : c;
                if (list == null || list.size() == 0) {
                    result.add(c + "");
                } else {
                    for (String s : list) {
                        result.add(s + c);
                    }
                }
                return result;
            }
            return list;
        }
        //将字符C的拼音首和已存在的拼音首组合成新的记录
        for (String str : strs) {
            if (list == null || list.size() == 0) {
                result.add(str);
            } else {
                for (String s : list) {
                    result.add(s + str);
                }
            }
        }
        return result;
    }

    /**
     * @param c
     * @return
     * @Description: 返回汉字的拼音
     */
    public static String[] getCharPinYinString(char c) {
        try {
            //返回字符C的拼音
            return PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param str
     * @return
     * @Description: 返回字符串的拼音的首字母
     */
    public static String[] getCharPinYinChar(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        //对字符串中的记录逐个分析
        for (int i = 0; i < str.length(); i++) {
            result = getCharPinYinChar(str.charAt(i), result);
        }
        return result.toArray(new String[result.size()]);
    }

    /**
     * @param c
     * @param list
     * @return
     * @Description: 将字符c的拼音首字母拼接到list中的记录中
     */
    private static List<String> getCharPinYinChar(char c, List<String> list) {
        char[] chars = getCharPinYinChar(c);
        List<String> result = new ArrayList<String>();
        //如果解析出的拼音为空，判断字符C是否为英文字母，如果是英文字母则添加值拼音结果中
        if (chars == null) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                c = c < 91 ? (char)(c + 32) : c;
                if (list == null || list.size() == 0) {
                    result.add(c + "");
                } else {
                    for (String s : list) {
                        result.add(s + c);
                    }
                }
                return result;
            }
            return list;
        }
        //将字符C的拼音首字母和已存在的拼音首字母组合成新的记录
        for (char ch : chars) {
            if (list == null || list.size() == 0) {
                result.add(ch + "");
            } else {
                for (String s : list) {
                    result.add(s + ch);
                }
            }
        }
        return result;
    }

    /**
     * @param c
     * @return
     * @Description:返回汉字拼音首字母
     */
    public static char[] getCharPinYinChar(char c) {
        //字符C的拼音
        String[] strs = getCharPinYinString(c);
        if (strs != null) {
            //截取拼音的首字母
            char[] chars = new char[strs.length];
            for(int i = 0; i <chars.length; i++) {
                chars[i] = strs[i].charAt(0);
            }
            return chars;
        }
        return null;
    }
}
