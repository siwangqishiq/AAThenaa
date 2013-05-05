package com.airad.athena.fragment;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import com.airad.athena.InsertMapActivity;
import com.airad.athena.R;
import com.airad.athena.fragment.abstracts.LocalActivityManagerFragment;

public class AboutMapFragment extends LocalActivityManagerFragment {
	public static final String TAG="about_map";
	private TabHost mTabHost;
	private Button mBackBtn;

	public static AboutMapFragment newInstance(Stack<Fragment> stacks){
		return new AboutMapFragment(stacks);
	}
	
	public AboutMapFragment(Stack<Fragment> stack) {
		super(stack);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about_map_layout,
				container, false);
		mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup(getLocalActivityManager());
		TabSpec tab = mTabHost.newTabSpec("map").setIndicator("map")
				.setContent(new Intent(getActivity(), InsertMapActivity.class));
		mTabHost.addTab(tab);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mBackBtn=(Button)getActivity().findViewById(R.id.about_map_backbutton);
		mBackBtn.setOnClickListener(new BackOnClick());
	}
	
	private class BackOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack(AboutFrag.TAG, 1);
		}
	}
}// end class
