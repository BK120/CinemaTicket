package com.bk120.cinematicket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferencesUtils {
	private static SharedPreferences sp;
	private static Editor editor;
	private static String fileName="config";//���ļ���
	//��Ų������͵�ֵ
	public static void putBoolean(Context context,String key,boolean value){
		sp=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	//ȡ�ò���ֵ
	public static boolean getBoolean(Context context,String key,boolean defValue){
		sp=context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		boolean result=sp.getBoolean(key, defValue);
		return result;
	}
	//���ַ���
	public static void putString(Context c,String key,String value){
		sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	//ȡ�ַ���
	public static String getString(Context c,String key,String defValue){
		sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		String result=sp.getString(key, defValue);
		return result;
	}
	//����һ��Intֵ
	public static void putInt(Context c,String key,int value){
		sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		editor=sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	//ȡ��һ��Intֵ
	public static int getInt(Context c,String key,int defValue){
		sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		int result=sp.getInt(key, defValue);
		return result;
	}
	//����һ��Longֵ
	public static void putLong(Context c,String key,Long value){
			sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			editor=sp.edit();
			editor.putLong(key, value);
			editor.commit();
	}
	//ȡ��һ��Longֵ
	public static Long getLong(Context c,String key,Long defValue){
			sp=c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			Long result=sp.getLong(key, defValue);
			return result;
	}
	
}
