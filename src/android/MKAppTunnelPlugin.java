package com.hand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.text.TextUtils;

import android.util.Log;

public class MKAppTunnelPlugin extends CordovaPlugin{
	
	private CallbackContext mCallbackContext;
//	private String urlString = "http://wzdcbdbpm63/sites/OA2/SitePages/MaryKayK2Pages/HLYTest.aspx?mobile=0";//测试环境
	private String urlString = "http://mkoa2/sites/oa2/SitePages/MaryKayK2Pages/HLYUserInfo.aspx";//正式环境
	private String responseStr = null;
	private final static String DEFAULT_ENCODING = "UTF-8";
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.mCallbackContext = callbackContext;
		if("getIdByHttpUrlConnection".equals(action)){
			Log.e("399", "getIdByHttpUrlConnection");
			getResonseFromeServer("HttpUrlConnection");
			return true;
		}
		
		mCallbackContext.error("error");
		return false;
	}
	
	private void getResonseFromeServer(final String requestMode) {
		
		new Thread(){
			@Override
			public void run() {
				try{
					if("HttpUrlConnection".equals(requestMode)){
						URL url = new URL(urlString);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setDoInput(true);
						responseStr = readInputStreamToString(connection.getInputStream());
					}
					Log.e("399", "responseStr :" + responseStr);
					if(!TextUtils.isEmpty(responseStr)){
						responseStr = responseStr.trim().replace(" ","");
					}
					mCallbackContext.success(responseStr);
					} catch (MalformedURLException e) {
						mCallbackContext.error("MalformedURLException");
						e.printStackTrace();
					} catch (ProtocolException e) {
						mCallbackContext.error("ProtocolException");
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						mCallbackContext.error("UnsupportedEncodingException");
						e.printStackTrace();
					} catch (IOException e) {
						mCallbackContext.error("IOException");
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						mCallbackContext.error("IllegalArgumentException");
						e.printStackTrace();
					}
			};
		}.start();
	}

	private String readInputStreamToString(InputStream is) throws IOException {
		if (is == null) {
			return "";
		}

		Log.d("399", "InputStream Available? " + is.available());

		BufferedReader rd = new BufferedReader(new InputStreamReader(is, DEFAULT_ENCODING));
		String line;
		StringBuffer responseBuf = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			responseBuf.append(line);
		}
		rd.close();

		return responseBuf.toString();
	}
}