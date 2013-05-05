package com.airad.athena;

import com.airad.athena.util.ImageFetcher;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

/**
 * ȫ�ֿؼ�
 * 
 * @author Panyi
 * 
 */
public class MyApplication extends Application {
	private ImageFetcher imageFetcher;// ͼƬ��ʾ�ؼ�
	private AlertDialog alert = null;// ȷ�϶Ի���
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
		builder.setMessage("��ȷ��Ҫ�˳���?").setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				}).setNegativeButton("ȡ��", null);
		alert = builder.create();
		return alert;
	}
}
