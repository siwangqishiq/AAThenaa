package com.airad.athena.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airad.athena.R;
import com.airad.athena.fragment.abstracts.ParentFragment;

public class WorkerFrag extends ParentFragment {
	public static final String TAG = "worker";
	private WorkerDetailFrag workerDetail;
	private ImageView mHairImg;
	private ImageView mMassageImg;

	public static WorkerFrag newInstance() {
		return new WorkerFrag(TAG);
	}

	public WorkerFrag(String tagName) {
		super(tagName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_worker_layout, container,
				false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHairImg = (ImageView) getActivity()
				.findViewById(R.id.woker_hair_image);
		mMassageImg = (ImageView) getActivity().findViewById(
				R.id.worker_massage_image);
		mHairImg.setOnClickListener(new OnHairImageClick());
	}

	private class OnHairImageClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if(getActivity().getSupportFragmentManager().findFragmentByTag(WorkerDetailFrag.TAG)==null){
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				workerDetail = WorkerDetailFrag.newInstance(getFragmentStack());
				ft.add(R.id.pagerContainer, workerDetail, WorkerDetailFrag.TAG);
				ft.addToBackStack(TAG);
				ft.commit();
			}
		}
	}

}// end class
