package com.airad.athena.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.apache.http.client.ClientProtocolException;

import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airad.athena.MainActivity;
import com.airad.athena.R;
import com.airad.athena.config.Constants;
import com.airad.athena.data.model.Product;
import com.airad.athena.fragment.ProductListFragment.ItemAdapter;
import com.airad.athena.fragment.abstracts.ChildListFragment;
import com.airad.athena.service.DataSwitchService;
import com.airad.athena.service.NetService;
import com.airad.athena.util.ImageFetcher;
import com.airad.athena.util.NetUtil;

/**
 * 品牌产品列表
 * 
 * @author Panyi
 * 
 */
public class ProductListFragment extends ChildListFragment {
	public static final String TAG = "product_list";
	private String brand;
	private Button mBackButton;
	private ArrayList<Product> mProductList;// 产品列表 从网络获得数据
	private GetDataTask mGetDataTask;
	private LoadListData mLoadListData;
	private ImageFetcher mImageFetcher;// 下载网络图片控件
	private ProgressBar mProgressBar;// 进度
	private ProductDetailFragment mProductDetail;

	public static ProductListFragment newInstance(Stack<Fragment> stacks,
			String brand) {
		Bundle bundle = new Bundle();
		bundle.putString(Constants.PRODUCT_BRAND, brand);
		ProductListFragment fragment = new ProductListFragment(stacks);
		fragment.setArguments(bundle);
		return fragment;
	}

	public ProductListFragment(Stack<Fragment> stack) {
		super(stack);
		// this.brand = brand;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_product_list_layout,
				container, false);// 载入视图布局
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	/**
	 * 点击产品 打开产品详情页 并传入产品ID参数
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {//
		super.onListItemClick(l, v, position, id);
		FragmentManager fm = getActivity().getSupportFragmentManager();
		if (fm.findFragmentByTag(ProductDetailFragment.TAG) == null) {
			int productId = mProductList.get(position).getId();
			FragmentTransaction ft = fm.beginTransaction();
			if (mProductDetail == null) {
				mProductDetail = ProductDetailFragment.newInstance(
						getFragmentStack(), position);
			} else {
				mProductDetail.getArguments().putString(Constants.PRODUCT_ID,
						position + "");
			}
			ft.add(R.id.pagerContainer, mProductDetail,
					ProductDetailFragment.TAG);
			ft.addToBackStack(TAG);
			ft.commit();
		}
	}

	private void init() {
		mImageFetcher = ((MainActivity) getActivity()).getImageFetcher();
		mBackButton = (Button) getActivity().findViewById(
				R.id.product_list_backbutton);
		mProgressBar = (ProgressBar) getActivity().findViewById(
				R.id.product_list_progress);
		mBackButton.setOnClickListener(new BackOnClick());
		mGetDataTask = new GetDataTask();
		mGetDataTask.execute(brand);// 启动线程 根据品牌名 获取商品列表
	}

	/**
	 * 退出则终止网络线程
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (mGetDataTask != null) {
			mGetDataTask.cancel(true);
		}
		if (mLoadListData != null) {
			mLoadListData.cancel(true);
		}
	}

	/**
	 * 返回按钮
	 * 
	 * @author Panyi
	 * 
	 */
	private class BackOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack(ShopFrag.TAG, 1);// 返回worker主页面
		}
	}

	private class GetDataTask extends
			AsyncTask<String, Void, ArrayList<Product>> {
		@Override
		protected ArrayList<Product> doInBackground(String... params) {
			String brandName = params[0];
			ArrayList<Product> result = null;
			try {
				String retString = (new NetService())
						.sendGet(NetUtil.getURL(R.string.getProductList,
								getResources()), brandName);
				result = (new DataSwitchService()).getProductList(retString);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<Product> result) {
			mProductList = result;
			mProgressBar.setVisibility(View.INVISIBLE);
			if (result == null || result.size() <= 0) {
				Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
				return;
			}
			// TODO 加载数据
			mLoadListData = new LoadListData();
			mLoadListData.execute(result);
		}
	}

	private class LoadListData extends
			AsyncTask<ArrayList<Product>, Void, ItemAdapter> {
		@Override
		protected ItemAdapter doInBackground(ArrayList<Product>... data) {
			return new ItemAdapter(data[0]);
		}

		@Override
		protected void onPostExecute(ItemAdapter result) {
			super.onPostExecute(result);
			ProductListFragment.this.setListAdapter(result);
		}
	}

	class ItemAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<Product> dataList;

		public ItemAdapter(ArrayList<Product> dataList) {
			mInflater = LayoutInflater.from(getActivity());
			this.dataList = dataList;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {// 返回View
			Product product = dataList.get(position);
			convertView = mInflater.inflate(R.layout.item_product_list_item,
					null);
			ImageView img = (ImageView) convertView
					.findViewById(R.id.product_list_item_image);
			// mImageFetcher.loadImage(product.getPicUrl(), img);// 载入图片
			ProgressBar bar = (ProgressBar) convertView
					.findViewById(R.id.product_list_progress);
			mImageFetcher.loadImage(product.getPicUrl(), img, bar);
			TextView text = (TextView) convertView
					.findViewById(R.id.product_list_item_text);// 载入文字
			text.setText(product.getName());
			return convertView;
		}
	}
}// end class
