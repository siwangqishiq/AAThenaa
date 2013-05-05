package com.airad.athena.fragment.abstracts;

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

public class ChildListFragment extends ListFragment {
	protected Stack<Fragment> fragmentStack;

	public ChildListFragment(Stack<Fragment> stack) {
		this.fragmentStack = stack;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		fragmentStack.push(this);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		fragmentStack.pop();
	}
	
	public Stack<Fragment> getFragmentStack(){
		return fragmentStack;
	}
}//end class
