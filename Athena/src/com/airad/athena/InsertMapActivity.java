package com.airad.athena;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;

import com.airad.athena.config.Constants;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.PoiOverlay;

public class InsertMapActivity extends MapActivity {
	MyApplication app;
	private BMapManager mBMapMan;
	private MapView mMapView;
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_layout);
		text=(TextView)findViewById(R.id.about_map_text);
		text.setText(Html.fromHtml(Constants.CONTRACT));
		initMap();
	}

	/**
	 * 初始化地图
	 */
	private void initMap() {
		app = (MyApplication) getApplication();
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("41159E4B7E5E2738CDD56DE64B5F413F6292C2C9", null);
		super.initMapActivity(mBMapMan);
		mMapView = (MapView) findViewById(R.id.about_map_bmapView);
		MapController mMapController = mMapView.getController();  // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		//118.759569, 32.059393
		GeoPoint point = new GeoPoint((int) (32.059393 * 1E6),
		        (int) (118.759569 * 1E6));  //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		MyLocationOverlay mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		mMapView.setDrawOverlayWhenZooming(true);
		mMapController.setCenter(point);  //设置地图中心点
		mMapController.setZoom(16);    //设置地图zoom级别
	}

	@Override
	protected void onDestroy() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * 按下返回键 退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog alert = app.getAlert(this);
			if (!alert.isShowing()) {
				alert.show();
			}
			return false;
		}
		return false;
	}
}
