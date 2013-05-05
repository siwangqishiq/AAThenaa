package com.airad.athena;

import com.airad.athena.util.ImageFetcher;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 全局控件
 * 
 * @author Panyi
 * 
 */
public class MyApplication extends Application {
	private ImageFetcher imageFetcher;// 图片显示控件
	private AlertDialog alert = null;// 确认对话框
	public int SCR_WIDTH, SCR_HEIGHT;

	public ImageFetcher getImageFetcher() {
		return imageFetcher;
	}

	public void setImageFetcher(ImageFetcher imageFetcher) {
		this.imageFetcher = imageFetcher;
	}

	public AlertDialog getAlert(Context context) {
		if (alert != null) {
			return alert;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("你确认要退出吗?").setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				}).setNegativeButton("取消", null);
		alert = builder.create();
		return alert;
	}
}
