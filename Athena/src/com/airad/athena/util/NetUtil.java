package com.airad.athena.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.airad.athena.R;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetUtil {

	/**
	 * 判断是否可访问网络
	 * 
	 * @return
	 */
	public static boolean canAccessNet(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static String downloadUrl(String myurl,String requestMethod) {
		InputStream is = null;
		int len = 5000;//读取最大长度
		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 );
			conn.setConnectTimeout(15000);
			conn.setRequestMethod(requestMethod.toUpperCase());
			conn.setDoInput(true);
			conn.connect();
			int response = conn.getResponseCode();
			Log.d("out", "The response is: " + response);
			//System.out.println("状态码:"+response);
			is = conn.getInputStream();
			String contentAsString = readIt(is, len);
			return contentAsString;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	public static String readIt(InputStream stream, int len) {
		Reader reader = null;
		char[] buffer = null;
		try {
			reader = new InputStreamReader(stream, "UTF-8");
			buffer = new char[len];
			reader.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(buffer);
	}
	
	/**
	 * 得到URL
	 * @param res
	 * @param id
	 * @return
	 */
	public static String getURL(int id,Resources res){
		return res.getString(R.string.socket)+res.getString(id);
	}
	
}//end class
