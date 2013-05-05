package com.airad.athena.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airad.athena.MainActivity;
import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.data.model.Worker;
import com.airad.athena.fragment.abstracts.ChildFragment;
import com.airad.athena.service.DataSwitchService;
import com.airad.athena.service.NetService;
import com.airad.athena.util.ImageFetcher;
import com.airad.athena.util.NetUtil;
import com.airad.athena.view.IndexView;

/**
 * 发型师介绍页面
 * 
 * @author Panyi
 * 
 */
public class WorkerDetailFrag extends ChildFragment {
	public static final String TAG = "worker_detail";
	private Button mBack;
	private Button mPhone;
	private ImageView showWorkerDes;
	private ScrollView descContainer;
	private TextView workerText;
	private ViewPager mViewPager;
	private LoadDataTask task;
	private ArrayList<Worker> workerList;
	private IndexView mIndexView;

	public static WorkerDetailFrag newInstance(Stack<Fragment> stacks) {
		return new WorkerDetailFrag(stacks);
	}

	public WorkerDetailFrag(Stack<Fragment> stack) {
		super(stack);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_worker_detail_layout,
				container, false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (task != null) {
			task.cancel(true);
		}
	}

	private void init() {
		// mImageFetcher = ((MainActivity)getActivity()).getImageFetcher();
		mViewPager = (ViewPager) getActivity().findViewById(R.id.workergallery);
		task = new LoadDataTask();
		task.execute();
		mBack = (Button) getActivity().findViewById(
				R.id.worker_detail_backbutton);
		mPhone = (Button) getActivity().findViewById(
				R.id.worker_detail_phoneButton);
		showWorkerDes = (ImageView) getActivity().findViewById(
				R.id.worker_detail_desc_button);
		descContainer = (ScrollView) getActivity().findViewById(
				R.id.worker_detail_desc_container);
		workerText = (TextView) getActivity().findViewById(
				R.id.worker_detail_desc_text);
		mIndexView = (IndexView)getActivity().findViewById(R.id.worker_detail_indexview);
		mBack.setOnClickListener(new BackButton());
		mPhone.setOnClickListener(new PhoneButton());
		showWorkerDes.setOnClickListener(new ShowDescButton());
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideDescContainer();
				return false;
			}
		});
		workerText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideDescContainer();
			}
		});
	}
	
	private void hideDescContainer(){
		if (descContainer != null) {
			descContainer.setVisibility(View.INVISIBLE);
		}
	}

	private class LoadDataTask extends AsyncTask<Void, Void, ArrayList<Worker>> {
		@Override
		protected ArrayList<Worker> doInBackground(Void... params) {
			String ret = null;
			try {
				ret = new NetService().sendGet(NetUtil.getURL(R.string.getWorkerList, getResources()));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (NotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			workerList = new DataSwitchService().getWorkList(ret);
			return workerList;
		}

		@Override
		protected void onPostExecute(ArrayList<Worker> workerList) {
			if (workerList != null) {
				((ProgressBar) getActivity().findViewById(
						R.id.worker_detail_progress))
						.setVisibility(View.INVISIBLE);
				((RelativeLayout) getActivity().findViewById(
						R.id.worker_detail_main)).setVisibility(View.VISIBLE);
				mViewPager.setAdapter(new ImagePagerAdapter(
						getFragmentManager(), workerList.size()));
				mIndexView.setNum(workerList.size());
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
			} else {
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
			}
		}
	}

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
			return ImageDetailFragment.newInstance(workerList.get(position)
					.getPic());
		}
	}

	private class ShowDescButton implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (workerList == null || workerList.size() == 0) {
				return;
			}
			String content = workerList.get(mViewPager.getCurrentItem())
					.getDes();
			AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
			alpha.setDuration(500);
			// alpha.setFillAfter(true);
			alpha.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					descContainer.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}
			});
			workerText.setText(content);
			descContainer.setAnimation(alpha);
		}
	}

	private class BackButton implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack(WorkerFrag.TAG, 1);// 返回worker主页面
		}
	}// end inner class

	private class PhoneButton implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Constants.phone));
			getActivity().startActivity(intent);
		}
	}

}// end class
