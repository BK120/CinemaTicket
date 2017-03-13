package com.bk120.cinematicket.utils;
/**
 * 工具类，动态的改变软键盘的状态
 * 通过传入的宿主Activity和Handler来改变
 * 所需控件的位置
 */
import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class KeyBoardUtils {
	private Handler mHandler;
	private Activity mActivity;
	private OnGlobalLayoutListener mLayoutChangeListener;
		//当前的键盘状态：显示隐藏
	private boolean mIsSoftKeyboardShowing;
	private int screenHeight;
	public KeyBoardUtils(Activity a,Handler myHandler){
		this.mActivity=a;
		this.mHandler=myHandler;
		getHeightDifference();
	}
	public void getHeightDifference(){
		DisplayMetrics dm=new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight=dm.heightPixels;
		//获取屏幕高度
		mIsSoftKeyboardShowing=false;
		mLayoutChangeListener=new OnGlobalLayoutListener() {
					
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				//判断窗口可见区域大小
				Rect r=new Rect();
				mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
				int heightDifference=screenHeight-(r.bottom-r.top);
				//由于获取的heightDifference在设置键盘是存在些许误差，适当调整距离
				heightDifference=heightDifference-50;
				boolean isKeyboardShowing=heightDifference>screenHeight/3;
				System.out.println(heightDifference+";"+mIsSoftKeyboardShowing);
				//软键盘的状态是否改变
				if(mIsSoftKeyboardShowing&&!isKeyboardShowing||(!mIsSoftKeyboardShowing&&isKeyboardShowing)){
					mIsSoftKeyboardShowing=isKeyboardShowing;
					if(mIsSoftKeyboardShowing){
						//键盘显示时
						Message m=Message.obtain();
						m.what=0x110;m.arg1=heightDifference;
						mHandler.sendMessage(m);
					}else{
						///键盘隐藏时
						mHandler.sendEmptyMessage(0x111);
					}
				}
			}
		};
		//当前注册布局全局监听
		mActivity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
	}
}
