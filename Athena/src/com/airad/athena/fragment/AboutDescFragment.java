package com.airad.athena.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.data.model.Description;
import com.airad.athena.fragment.abstracts.ChildFragment;
import com.airad.athena.service.DataSwitchService;
import com.airad.athena.service.NetService;
import com.airad.athena.util.NetUtil;
import com.airad.athena.view.IndexView;

public class AboutDescFragment extends ChildFragment {
	public static final String TAG = "about_desc";
	private Button mBack;
	private ViewPager mViewPager;// 滑动游览控件
	private IndexView mIndexView;// 下标控件
	private GetDescTask mTask;

	public AboutDescFragment(Stack<Fragment> stack) {
		super(stack);
	}

	public static AboutDescFragment newInstance(Stack<Fragment> stacks) {
		return new AboutDescFragment(stacks);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_about_des_layout, container,
				false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		mBack = (Button) getActivity().findViewById(R.id.about_des_backbutton);
		mViewPager = (ViewPager) getActivity().findViewById(
				R.id.about_desc_gallery);
		mIndexView = (IndexView) getActivity().findViewById(
				R.id.about_desc_indexview);
		mBack.setOnClickListener(new BackOnClick());
		mTask = new GetDescTask();
		mTask.execute(0);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mTask != null) {
			mTask.cancel(true);
		}
	}

	private class BackOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack(AboutFrag.TAG, 1);
		}
	}

	private class GetDescTask extends AsyncTask<Integer, Void, Description> {
		@Override
		protected Description doInBackground(Integer... params) {
			try {
				String ret = (new NetService()).sendGet(NetUtil.getURL(
						R.string.getDesc, getResources()));
				Description record = (new DataSwitchService())
						.getDescription(ret);
				return record;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Description record) {
			super.onPostExecute(record);
			if (record == null) {
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
				return;
			}
			mViewPager.setAdapter(new ImagePagerAdapter(getFragmentManager(),
					record.getPicList()));
			TextView text = (TextView) getActivity().findViewById(
					R.id.about_desc_text);
			text.setText(record.getContent());
			mIndexView.setNum(record.getPicList().size());
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrollStateChanged(int index) {
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageSelected(int index) {
					mIndexView.setPoint(index);
				}
			});
		}
	}// end inner class

	class ImagePagerAdapter extends FragmentStatePagerAdapter {
		private final ArrayList<String> mPicList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> picList) {
			super(fm);
			mPicList = picList;
		}

		@Override
		public int getCount() {
			return mPicList.size();
		}

		@Override
		public Fragment getItem(int position) {
			return ImageDetailFragment.newInstance(mPicList.get(position));
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
