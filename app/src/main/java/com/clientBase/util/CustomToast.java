package com.clientBase.util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;


public class CustomToast {
	private static TextView mvtToastMessage;
	private static ImageView mivToastImg;

	public static void showToast(Context context, String message) {
		// 加载Toast布局
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.city_toast, null);
		// 初始化布局控件
		mvtToastMessage = (TextView) toastRoot.findViewById(R.id.mvtToastMessage);
		mivToastImg = (ImageView) toastRoot.findViewById(R.id.mivToastImg);
		// 为控件设置属性
		mvtToastMessage.setText(message);
//		mivToastImg.setImageResource(R.mipmap.ic_launcher);
		// Toast的初始化
		Toast toastStart = new Toast(context);
		// 获取屏幕高度
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		// Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
		toastStart.setDuration(Toast.LENGTH_SHORT);
		toastStart.setView(toastRoot);
		toastStart.show();
	}
}