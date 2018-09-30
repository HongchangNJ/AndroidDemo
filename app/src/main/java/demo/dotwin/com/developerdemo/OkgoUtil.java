package demo.dotwin.com.developerdemo;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.https.HttpsUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by david.ding on 2017/6/8.
 */

public class OkgoUtil {

	private static final String TAG = "OkgoUtil";
	private static long default_timeout = 30000;
	private static long read_timeout = 30000;

	/**
	 * init timeout
	 * 
	 * @param timeout
	 *            超时设置
	 */
	public static void okgo_init_timeout(long timeout) {
		if (timeout == 0)
			timeout = default_timeout;
		HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
		OkHttpClient okhttpclient = new OkHttpClient.Builder()
				.readTimeout(read_timeout, TimeUnit.MILLISECONDS).writeTimeout(timeout, TimeUnit.MILLISECONDS)
				.connectTimeout(timeout, TimeUnit.MILLISECONDS)
				.cookieJar(new CookieJarImpl(new MemoryCookieStore()))
				.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager).build();
		OkGo.getInstance().setOkHttpClient(okhttpclient);
	}

	public static String OkGo_Get(String url) {
		return OkGo_Get(url, default_timeout);
	}

	/**
	 * http(s) get method
	 * 
	 * @param url
	 *            网络地址
	 * @return
	 * @throws Exception
	 */
	public static String OkGo_Get(String url, long timeout) {
		//LogUtil.log(0, TAG, "http get,url:" + url);
		String result = new String();
		Response response;
		try {
			response = OkGo.get(url).execute();
			if (response.isSuccessful())
				result = response.body().string();
			else
				result = null;
		} catch (Exception e) {
			Log.d(TAG, "OkGo_Get IOException，url:"+url + ",result:" + e.toString());
		}
		return result;
	}

	public static String OkGo_Post(String path, String param) {
		return OkGo_Post(path, param, default_timeout);
	}

	public static String OkGo_Post_Json(String path, JSONObject param) {
		return OkGo_Post_Json(path, param, default_timeout);
	}

	/**
	 * http(s) post method
	 * 
	 * @param path
	 * @param param
	 * @throws Exception
	 */
	public static String OkGo_Post(String path, String param, long timeout) {
		//LogUtil.log(0, TAG, "http post,url:" + path + ", para:"+param);
		String result = new String();
		Response response;
		try {
			//if (timeout != default_timeout)
			//	okgo_init_timeout(timeout);
			response = OkGo.post(path).upString(param, MediaType.parse("application/x-www-form-urlencoded")).execute();
			if (response.isSuccessful())
				result = response.body().string();
			else
				result = null;
		} catch (Exception e) {
			//LogUtil.log(0, TAG, "OkGo_Post IOException，url:"+path + ",para:"+param+ ",result:" + e.toString());
		}
		return result;
	}

	/**
	 * http(s) post method
	 *
	 * @param path
	 * @param param
	 * @throws Exception
	 */
	public static String OkGo_Post_Json(String path, JSONObject param, long timeout) {
		String result = new String();
		Response response;
		try {
			//if (timeout != default_timeout)
			//	okgo_init_timeout(timeout);
			response = OkGo.post(path).upJson(param).execute();
			if (response.isSuccessful())
				result = response.body().string();
			else
				result = null;
		} catch (Exception e) {
			//LogUtil.log(0, TAG, "OkGo_Post_Json IOException，result:" + e.toString());
		}
		return result;
	}
	
	/**
	 * http(s) post method
	 *
	 * @throws Exception
	 */
	public static String OkGo_Post_File(String url, String file_path) {
		String result = new String();
		Response response;
		try {
			response = OkGo.post(url).params("file",new File(file_path)).execute();
			if (response.isSuccessful())
				result = response.body().string();
			else
				result = null;
		} catch (Exception e) {
			//LogUtil.log(0, TAG, "OkGo_Post_File IOException，result:" + e.toString());
		}
		return result;
	}

	public static boolean OkGo_Down(String url, String path) {
		return OkGo_Down(url, path, default_timeout);
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            网络地址
	 * @param path
	 *            文件的保存地址
	 * @return
	 */
	public static boolean OkGo_Down(String url, String path, long timeout) {
		InputStream is = null;
		RandomAccessFile savedFile = null;
		File file = null;
		boolean result = false;
		try {
			//path = SysUtil.absolutePath(path);
            path = "";
            //if (timeout != default_timeout)
			//	okgo_init_timeout(timeout);
			Response response = OkGo.get(url).execute();
			if ((response != null) && response.isSuccessful()) {
				is = response.body().byteStream();
				file = new File(path);
				savedFile = new RandomAccessFile(file, "rw");

				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) != -1) {
					savedFile.write(b, 0, len);
				}
				response.body().close();
				result = true;
			}

		} catch (Exception e) {
			//LogUtil.log(0, TAG, "dwonload file Exception,result:" + e.toString());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (savedFile != null) {
					savedFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
