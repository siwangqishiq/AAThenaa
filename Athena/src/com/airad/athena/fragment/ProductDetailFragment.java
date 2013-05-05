package com.airad.athena.fragment;

import java.io.IOException;
import java.util.Stack;

import org.apache.http.client.ClientProtocolException;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airad.athena.MainActivity;
import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.data.model.Product;
import com.airad.athena.fragment.abstracts.ChildFragment;
import com.airad.athena.service.DataSwitchService;
import com.airad.athena.service.NetService;
import com.airad.athena.util.ImageFetcher;
import com.airad.athena.util.NetUtil;

/**
 * 商品详情页
 * 
 * @author Panyi
 * 
 */
public class ProductDetailFragment extends ChildFragment {
	public static final String TAG = "product_detail";
	private String product_id;
	private Button mBackBtn, mPhoneBtn;
	private ProgressBar mProgressBar;
	private GetProductTask mGetProductTask;
	private RelativeLayout mMain;
	private ImageFetcher mImageFetcher;

	public static ProductDetailFragment newInstance(Stack<Fragment> stacks,
			int id) {
		ProductDetailFragment f = new ProductDetailFragment(stacks);
		Bundle args = new Bundle();
		args.putString(Constants.PRODUCT_ID, id + "");
		f.setArguments(args);
		return f;
	}

	public ProductDetailFragment(Stack<Fragment> stack) {
		super(stack);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		product_id = getArguments() != null ? getArguments().getString(
				Constants.PRODUCT_ID) : null;
		System.out.println(product_id);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_product_detail_layout,
				container, false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		mImageFetcher = ((MainActivity) getActivity()).getImageFetcher();
		mProgressBar = (ProgressBar) getActivity().findViewById(
				R.id.product_detail_progress);
		mBackBtn = (Button) getActivity().findViewById(
				R.id.product_detail_backbutton);
		mPhoneBtn = (Button) getActivity().findViewById(
				R.id.product_detail_phonebutton);
		mMain = (RelativeLayout) getActivity().findViewById(
				R.id.product_detail_main);
		mBackBtn.setOnClickListener(new BackOnClick());
		mPhoneBtn.setOnClickListener(new PhoneOnClick());

		mGetProductTask = new GetProductTask();
		mGetProductTask.execute(product_id + "");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mGetProductTask != null) {
			mGetProductTask.cancel(true);
		}
	}

	private class GetProductTask extends AsyncTask<String, Void, Product> {
		@Override
		protected Product doInBackground(String... params) {
			try {
				String retString = (new NetService()).sendGetWithId(
						NetUtil.getURL(R.string.getProduct, getResources()),
						params[0]);
				return (new DataSwitchService()).getProduct(retString);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Product product) {
			super.onPostExecute(product);
			if (product == null) {
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
				return;
			}
			mMain.setVisibility(View.VISIBLE);
			ImageView img = (ImageView) getActivity().findViewById(
					R.id.product_detail_image);
			mImageFetcher.loadImage(product.getPicUrl(), img);// 载入图片
			TextView text = (TextView) getActivity().findViewById(
					R.id.product_detail_text);
			text.setText(product.getName() + "\n" + product.getPrice() + "\n"
					+ product.getBrand());
			mProgressBar.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 返回
	 * 
	 * @author Panyi
	 * 
	 */
	private class BackOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack(ProductListFragment.TAG, 1);
		}
	}

	/**
	 * 订购
	 * 
	 * @author Panyi
	 * 
	 */
	private class PhoneOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ Constants.phone));
			getActivity().startActivity(intent);
		}
	}
}// end class
