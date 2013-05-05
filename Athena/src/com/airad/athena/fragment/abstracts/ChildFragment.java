package com.airad.athena.fragment.abstracts;

import java.util.Stack;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ChildFragment extends Fragment {
	protected Stack<Fragment> fragmentStack;

	public ChildFragment(Stack<Fragment> stack) {
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
