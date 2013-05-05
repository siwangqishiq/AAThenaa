package com.airad.athena;

import com.airad.athena.util.ImageCache;
import com.airad.athena.util.ImageFetcher;
import com.airad.athena.util.NetUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * 载入页
 * 
 * @author Panyi
 * 
 */
public class LoadActivity extends FragmentActivity {
	// private Handler hander;
	private LoadActivity mContext;
	private static final String IMAGE_CACHE_DIR = "athena_images";
	private ImageFetcher mImageFetcher;
	MyApplication app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_load_layout);
		init();
	}

	private void init() {
		app = (MyApplication) getApplication();
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		int width = displayMetrics.widthPixels;
		app.SCR_WIDTH = width;
		app.SCR_HEIGHT = height;
		final int longest = (height > width ? height : width) / 2;
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
				mContext, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(mContext, 0.25f); // 设置一级缓存大小
		mImageFetcher = new ImageFetcher(mContext, longest);
		mImageFetcher.addImageCache(mContext.getSupportFragmentManager(),
				cacheParams);
		mImageFetcher.setImageFadeIn(false);
		LoadWork work = new LoadWork();
		work.execute(getString(R.string.loadurl));
	}

	private class LoadWork extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return NetUtil.downloadUrl(params[0], "get");
		}

		@Override
		protected void onPostExecute(String result) {
			// System.out.println(result);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			app.setImageFetcher(mImageFetcher);
			Intent it = new Intent();
			it.setClass(mContext, MainActivity.class);
			mContext.startActivity(it);
			mContext.finish();
		}
	}
}// end class