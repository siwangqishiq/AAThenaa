package com.airad.athena.fragment.abstracts;

import java.util.Stack;

import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class LocalActivityManagerFragment extends ChildFragment {
	private static final String KEY_STATE_BUNDLE = "localActivityManagerState";
	private LocalActivityManager mLocalActivityManager;

	public LocalActivityManagerFragment(Stack<Fragment> stack) {
		super(stack);
	}

	protected LocalActivityManager getLocalActivityManager() {
		return mLocalActivityManager;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle state = null;
		if (savedInstanceState != null) {
			state = savedInstanceState.getBundle(KEY_STATE_BUNDLE);
		}

		mLocalActivityManager = new LocalActivityManager(getActivity(), true);
		mLocalActivityManager.dispatchCreate(state);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBundle(KEY_STATE_BUNDLE,
				mLocalActivityManager.saveInstanceState());
	}

	@Override
	public void onResume() {
		super.onResume();
		mLocalActivityManager.dispatchResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mLocalActivityManager.dispatchPause(getActivity().isFinishing());
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocalActivityManager.dispatchStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mLocalActivityManager.dispatchDestroy(getActivity().isFinishing());
	}
	
}// end class
