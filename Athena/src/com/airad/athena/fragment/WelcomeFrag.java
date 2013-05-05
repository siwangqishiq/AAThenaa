package com.airad.athena.fragment;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.fragment.abstracts.ParentFragment;
import com.airad.athena.service.DataSwitchService;
import com.airad.athena.service.NetService;
import com.airad.athena.util.NetUtil;
import com.airad.athena.view.IndexView;

public class WelcomeFrag extends ParentFragment {
	public static final String TAG = "welcome";
	private ViewPager mViewPager;// 滑动游览控件
	private SetdapterAsynTask setTask;// 载入适配器线程
	private Button mTelButton;
	private IndexView mIndexView;
	private WelPicListTask mWelPicListTask;
	private ArrayList<String> picData;
	
//	public static String[] picData = new String[] {
//			"http://img.kumi.cn/photo/c3/46/3b/c3463bea94b81c44.jpg",
//			"http://im.glogster.com/media/10/45/58/26/45582675.jpg",
//			"http://download.minitokyo.net/Clannad.400466.jpg",
//			"http://imgsrc.baidu.com/forum/pic/item/79297659cace7c622934f054.jpg" };

	public WelcomeFrag(String tagName) {
		super(tagName);
	}

	public static WelcomeFrag newInstance() {
		return new WelcomeFrag(TAG);
	}

	/**
	 * 返回栈头元素
	 * 
	 * @return
	 */
	public Fragment getTopFragment() {
		return getFragmentStack().peek();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_welcome_layout, container,
				false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewPager = (ViewPager) getActivity().findViewById(R.id.welcomepager);
		mTelButton = (Button) getActivity().findViewById(R.id.telBtn);
		mWelPicListTask = new WelPicListTask();
		mTelButton.setOnClickListener(new TelButtonOnClickListener());
		mIndexView = (IndexView) getActivity().findViewById(
				R.id.welcome_indexview);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				mIndexView.setPoint(index);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		mWelPicListTask.execute(1);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (setTask != null) {
			setTask.cancel(true);
		}
		if (mWelPicListTask != null) {
			mWelPicListTask.cancel(true);
		}
	}

	private class WelPicListTask extends
			AsyncTask<Integer, Integer, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(Integer... params) {
			ArrayList<String> list=null;
			try {
				String ret=(new NetService()).sendGet(NetUtil.getURL(R.string.getWelcomePicList, getResources()));
				list = (new DataSwitchService().getWelList(ret));
				return list;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<String> list) {
			if(list==null){
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
				return;
			}
			picData=list;
			setTask = new SetdapterAsynTask();
			setTask.execute(mViewPager);
		}
	}// end inner class

	private class SetdapterAsynTask extends
			AsyncTask<ViewPager, Integer, ImagePagerAdapter> {
		private ViewPager mContainer;

		@Override
		protected ImagePagerAdapter doInBackground(ViewPager... params) {
			mContainer = params[0];
			return new ImagePagerAdapter(getActivity()
					.getSupportFragmentManager(), picData.size());
		}

		@Override
		protected void onPostExecute(ImagePagerAdapter result) {
			mContainer.setAdapter(result);
			mIndexView.setNum(picData.size());
		}
	}//end inner class

	class ImagePagerAdapter extends FragmentStatePagerAdapter {
		private final int mSize;

		public ImagePagerAdapter(FragmentManager fm, int size) {
			super(fm);
			mSize = size;
		}

		@Override
		public int getCount() {
			return mSize;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageDetailFragment.newInstance(picData.get(position));
		}
	}

	private class TelButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Constants.phone));
			getActivity().startActivity(intent);
		}
	}
}
