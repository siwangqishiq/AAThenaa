package com.airad.athena.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.fragment.abstracts.ParentFragment;

public class ShopFrag extends ParentFragment {
	public static final String TAG = "shop";
	private ImageView[] mImageViews;
	private ProductListFragment productListFragment;
	private int[] imageIds = new int[] { R.id.shop_brand1, R.id.shop_brand2,
			R.id.shop_brand3, R.id.shop_brand4, R.id.shop_brand5,
			R.id.shop_brand6 };

	public static ShopFrag newInstance() {
		return new ShopFrag(TAG);
	}

	public ShopFrag(String tagName) {
		super(tagName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.fragment_shop_layout, container, false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		mImageViews = new ImageView[imageIds.length];// 开辟数组控件
		for (int i = 0; i < imageIds.length; i++) {
			mImageViews[i] = (ImageView) this.getActivity().findViewById(
					imageIds[i]);
			mImageViews[i].setOnClickListener(new ImageClick(i));
		}// end for
	}

	private class ImageClick implements OnClickListener {
		public int id;

		public ImageClick(int id) {
			this.id = id;
		}

		/**
		 * 点击进入产品列表页面
		 */
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			if (fm.findFragmentByTag(ProductListFragment.TAG) == null) {
				FragmentTransaction ft = fm.beginTransaction();
				if(productListFragment==null){
					productListFragment = ProductListFragment.newInstance(
							getFragmentStack(), id + "");
				}else{
					productListFragment.getArguments().putString(Constants.PRODUCT_BRAND, ""+id);
				}
				ft.add(R.id.pagerContainer, productListFragment,
						ProductListFragment.TAG);
				ft.addToBackStack(TAG);
				ft.commit();
			}
		}
	}

}// end class
