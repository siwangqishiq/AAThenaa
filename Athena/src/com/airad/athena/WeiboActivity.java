package com.airad.athena;
import com.airad.athena.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WeiboActivity extends Activity {
	private Button mBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_layout);
		init();
	}
	
	private void init(){
		mBack=(Button)this.findViewById(R.id.weibo_backbutton);
		mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				WeiboActivity.this.finish();
			}
		});
	}
	
}//end class
