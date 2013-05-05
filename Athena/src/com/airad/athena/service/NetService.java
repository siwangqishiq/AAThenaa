package com.airad.athena.service;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class NetService {
	/**
	 * 发出对指定URl的get请求
	 */
	public String sendGet(String url) throws ClientProtocolException,
			IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity());
		}
		return result;
	}

	public String sendGet(String url, String params)
			throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url + "?type=" + params);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity());
		}
		return result;
	}

	public String sendGetWithId(String url, String id)
			throws ClientProtocolException, IOException {
		String result = "";
		HttpGet httpGet = new HttpGet(url + "?id=" + id);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity());
		}
		return result;
	}

	public String sendPost(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity());
		}
		return result;
	}
	
}// end class

