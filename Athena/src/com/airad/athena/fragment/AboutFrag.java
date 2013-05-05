package com.airad.athena.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.airad.athena.R;
import com.airad.athena.fragment.abstracts.ParentFragment;

public class AboutFrag extends ParentFragment {
	public static final String TAG = "about";
	private AboutDescFragment aboutDetailFrag;
	private AboutMapFragment mAboutMapFragment;
	private Button mDescBtn, mContractBtn;

	public static AboutFrag newInstance() {
		return new AboutFrag(TAG);
	}

	public AboutFrag(String tagName) {
		super(tagName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_about_layout, container,
				false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDescBtn = (Button) getActivity().findViewById(R.id.about_desc_btn);
		mContractBtn = (Button) getActivity().findViewById(
				R.id.about_contract_btn);
		mDescBtn.setOnClickListener(new DesOnClick());
		mContractBtn.setOnClickListener(new ContractOnClick());
	}

	private class ContractOnClick implements OnClickListener {// 联系我们
		@Override
		public void onClick(View v) {
			if (getActivity().getSupportFragmentManager().findFragmentByTag(
					AboutDescFragment.TAG) == null
					&& getActivity().getSupportFragmentManager()
							.findFragmentByTag(AboutMapFragment.TAG) == null) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				mAboutMapFragment = AboutMapFragment
						.newInstance(getFragmentStack());
				ft.add(R.id.pagerContainer, mAboutMapFragment,
						AboutMapFragment.TAG);
				ft.addToBackStack(TAG);
				ft.commit();
			}
		}
	}// end inner class

	private class DesOnClick implements OnClickListener {// 场馆介绍
		@Override
		public void onClick(View v) {
			if (getActivity().getSupportFragmentManager().findFragmentByTag(
					AboutDescFragment.TAG) == null
					&& getActivity().getSupportFragmentManager()
							.findFragmentByTag(AboutMapFragment.TAG) == null) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				aboutDetailFrag = AboutDescFragment
						.newInstance(getFragmentStack());
				ft.add(R.id.pagerContainer, aboutDetailFrag,
						AboutDescFragment.TAG);
				ft.addToBackStack(TAG);
				ft.commit();
			}
		}
	}// end inner class

}// end class
