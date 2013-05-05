package com.airad.athena.fragment.abstracts;

import java.util.Stack;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class ParentFragment extends Fragment {
	private Stack<Fragment> fragmentStack;
	public String TAG_NAME;
	
	public ParentFragment(String tagName) {
		super();
		fragmentStack = new Stack<Fragment>();
		fragmentStack.push(this);
		this.TAG_NAME=tagName;
	}
	
	public Stack<Fragment> getFragmentStack(){
		return fragmentStack;
	}

	public void hide(FragmentTransaction ft) {
		for(int i=0;i<fragmentStack.size();i++){
			Fragment f=fragmentStack.get(i);
			ft.hide(f);
		}
	}
	
	public void show(FragmentTransaction ft){
		for(int i=0;i<fragmentStack.size();i++){
			Fragment f=fragmentStack.get(i);
			ft.show(f);
		}
	}

}// end class
