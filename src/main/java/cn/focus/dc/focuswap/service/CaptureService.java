package cn.focus.dc.focuswap.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.paoding.rose.web.Invocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.focus.dc.focuswap.config.AppConstants;

/**
 * 图片验证码
 * @author zhiweiwen
 *
 */
@Service
public class CaptureService {
	
	private static Log logger = LogFactory.getLog(CaptureService.class);
	
	@Autowired 
	private RestTemplate focusWapTemplate;

	private static final String format = "{\"width\" : 160,\"height\" : 60,\"border\" : true,\"noise\" : {\"name\":\"StraightLine\",\"color\":[220,0,0]},\"gimpy\" : \"Ripple\",\"background\" : {\"name\":\"Gradiated\",\"colors\":[[252,230,202],[252,230,202]]} ,\"text\" : {\"length\" : 4,\"producer\" : \"Default\",\"wordRender\" :{\"colors\" : [[5,0,0],[16,5,0],[0,0,0],[0,0,0],[20,10,0]],\"fonts\" : [{\"name\":\"宋体\",\"style\":0,\"size\":55}]}}}";

	private static final boolean VERIFY_TRUE = true;
	private static final boolean VERIFY_FALSE = false;
	
	public void getCapture(Invocation inv, String id) {

		OutputStream stream = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					AppConstants.CAPTURE_URL + id).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("content-type", "application/json");
			connection.setFollowRedirects(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			String json = format;
			out.writeBytes(json);
			out.flush();
			out.close();
			int status = connection.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();// 得到验证码的输出流后保存到本地图片
				inv.getResponse().setContentType("image/png");
				stream = inv.getResponse().getOutputStream();
				byte[] buffer = new byte[4048];
				int b = 0;
				while ((b = in.read(buffer)) != -1) {
					stream.write(buffer, 0, b);
				}
				in.close();
				stream.flush();
				stream.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkCapture(String id,String answer) {
		boolean flag = false;
		try {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("answer", answer);
		String result = focusWapTemplate.getForObject(AppConstants.CAPTURE_VERIFY_URL, String.class,params);
		if(null != result && result.contains("true")) {
			flag = VERIFY_TRUE;
		} else {
			flag = VERIFY_FALSE;
		}
		return flag;
		} catch (Exception e) {
			logger.error("", e);
			return flag;
		}
	}

}
