package com.airad.athena;

import java.util.Queue;
import java.util.Stack;

import com.airad.athena.config.Constants;
import com.airad.athena.fragment.AboutFrag;
import com.airad.athena.fragment.ShopFrag;
import com.airad.athena.fragment.WelcomeFrag;
import com.airad.athena.fragment.WorkerFrag;
import com.airad.athena.fragment.abstracts.ParentFragment;
import com.airad.athena.util.ImageFetcher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * 主Activity界面
 * 
 * @author panyi
 * 
 */
public class MainActivity extends FragmentActivity {
	MyApplication app;
	private FragmentActivity mContext;
	private ImageFetcher mImageFetcher;
	private ImageView mBanner;
	private BannerOnTouchListener mBannerTouchListenr;

	private ParentFragment mCurFragment;
	private WelcomeFrag welcomeFragment;
	private WorkerFrag workerFragment;
	private ShopFrag shopFragment;
	private AboutFrag aboutFragment;

	public static int UNIT_LENGTH;// 单位长度
	private int cur_state = Constants.STATE_WELCOME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		mContext = this;
		init();
	}

	protected void init() {
		app = (MyApplication) getApplication();
		UNIT_LENGTH = app.SCR_WIDTH / 4;
		mImageFetcher = app.getImageFetcher();
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		welcomeFragment = WelcomeFrag.newInstance();
		mCurFragment = welcomeFragment;
		fragmentTransaction.add(R.id.pagerContainer, welcomeFragment);
		fragmentTransaction.commit();
		mBanner = (ImageView) findViewById(R.id.banner);
		mBannerTouchListenr = new BannerOnTouchListener();
		mBanner.setOnTouchListener(mBannerTouchListenr);
	}

	public ImageFetcher getImageFetcher() {
		return mImageFetcher;
	}

	/**
	 * 按下返回键 退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog alert=app.getAlert(this);
			if(!alert.isShowing()){
				alert.show();
			}
		}
		return false;
	}

	private class BannerOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int offset = (int) event.getX();
				int type = offset / UNIT_LENGTH;
				if (type == cur_state) {
					return true;
				}
				switch (type) {
				case Constants.STATE_WELCOME:
					switchToWelcome();
					break;
				case Constants.STATE_WORKER:
					switchToWorker();
					break;
				case Constants.STATE_SHOP:
					switchToShop();
					break;
				case Constants.STATE_ABOUT:
					switchToAbout();
					break;
				default:
					break;
				}// end switch
			}
			return true;
		}
	}// end class BannerOnTouchListener

	/**
	 * 跳转到welcom页面
	 */
	private void switchToWelcome() {
		cur_state = Constants.STATE_WELCOME;
		mBanner.setBackgroundResource(R.drawable.debanner);
		FragmentTransaction ft = mContext.getSupportFragmentManager()
				.beginTransaction();
		mCurFragment.hide(ft);
		if (welcomeFragment == null) {
			welcomeFragment = WelcomeFrag.newInstance();
			ft.add(R.id.pagerContainer, welcomeFragment, WelcomeFrag.TAG);
		}

		mCurFragment = welcomeFragment;
		mCurFragment.show(ft);
		ft.commit();
	}

	/**
	 * 11-14 15:10:57.896:
	 * 
	 * 跳转到Work 页面
	 */
	private void switchToWorker() {
		cur_state = Constants.STATE_WORKER;
		mBanner.setBackgroundResource(R.drawable.debanner1);
		FragmentTransaction ft = mContext.getSupportFragmentManager()
				.beginTransaction();
		mCurFragment.hide(ft);
		if (workerFragment == null) {
			workerFragment = WorkerFrag.newInstance();
			ft.add(R.id.pagerContainer, workerFragment, WorkerFrag.TAG);
		}
		mCurFragment = workerFragment;
		mCurFragment.show(ft);
		ft.commit();
	}

	/**
	 * 跳转到shop页面
	 */
	private void switchToShop() {
		cur_state = Constants.STATE_SHOP;
		mBanner.setBackgroundResource(R.drawable.debanner2);
		FragmentTransaction ft = mContext.getSupportFragmentManager()
				.beginTransaction();
		mCurFragment.hide(ft);
		if (shopFragment == null) {
			shopFragment = ShopFrag.newInstance();
			ft.add(R.id.pagerContainer, shopFragment, ShopFrag.TAG);
		}
		mCurFragment = shopFragment;
		mCurFragment.show(ft);
		ft.commit();
	}

	/**
	 * 跳转到about 页面
	 */
	private void switchToAbout() {
		cur_state = Constants.STATE_ABOUT;
		mBanner.setBackgroundResource(R.drawable.debanner3);
		FragmentTransaction ft = mContext.getSupportFragmentManager()
				.beginTransaction();
		mCurFragment.hide(ft);
		if (aboutFragment == null) {
			aboutFragment = AboutFrag.newInstance();
			ft.add(R.id.pagerContainer, aboutFragment, AboutFrag.TAG);
		}
		mCurFragment = aboutFragment;
		mCurFragment.show(ft);
		ft.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
}// end class
