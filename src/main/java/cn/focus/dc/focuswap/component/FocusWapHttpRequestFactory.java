package cn.focus.dc.focuswap.component;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AuthKeyPair;
import cn.focus.dc.focuswap.config.SecretkeyPair;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SuppressWarnings("deprecation")
public class FocusWapHttpRequestFactory extends
		HttpComponentsClientHttpRequestFactory {

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 300;

	private static final int DEFAULT_TIMEOUT_MILLISECONDS = 5 * 1000;
	private static final String DEFAULT_SIGN_NAME = "sign";
	private static final Log logger = LogFactory
			.getLog(FocusWapHttpRequestFactory.class);

	public FocusWapHttpRequestFactory(int maxConnectionsPerRoute) {
		super();
		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
		connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
		HttpClient httpClient = new DefaultHttpClient(connectionManager);
		super.setHttpClient(httpClient);
		setReadTimeout(DEFAULT_TIMEOUT_MILLISECONDS);
		setConnectTimeout(DEFAULT_TIMEOUT_MILLISECONDS);
	}

	// TODO 现在默认查询字符串已经排序过,将来可能会取消该默认。
	@Override
	protected void postProcessHttpRequest(HttpUriRequest request) {
		if (StringUtils.equals(HttpGet.METHOD_NAME, request.getMethod())) {
			URI url = request.getURI();
			String path = url.getPath();
			String host = url.getHost();
			String query = url.getQuery();
			if (null != query) {
				String sign = genSignParam(query, host + path);
				if (StringUtils.isNotBlank(sign)) {
					request.addHeader(DEFAULT_SIGN_NAME, sign);
				}
			}
		}
		super.postProcessHttpRequest(request);
	}

	/**
	 * 在url中添加权限校验的参数
	 */
	// 将这步操作本来计划合并到postProcessHttpRequest中去做，但是发现postProcessHttpRequest中无法给request对象重新setURI，所以只能在这里做了
	@Override
	protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
		String path = uri.getPath();
		String host = uri.getHost();
		AuthKeyPair pair = AppConstants.authKeyMap.get(host + path);
		if (null != pair) {
			switch (pair.getType()) {
			case XINFANG_API_V4:
			case PC_SIGN_AT_URL:
				uri = genXinfangApiV4AuthKey(httpMethod, uri, pair);
				break;
			default:
				break;
			}
		}
		return super.createHttpUriRequest(httpMethod, uri);
	}

    private String genSignParam(String queryStr,String path) {
        StringBuilder sb = new StringBuilder();
        SecretkeyPair s = AppConstants.secretKeyMap.get(path);
        if(s != null){
            if (StringUtils.isNotBlank(s.getSecretKey())) {
                if (StringUtils.isBlank(queryStr)) {
                    sb.append(s.getSecretKey());
                } else {
                    sb.append(StringUtils.chomp(queryStr, "&")).append(s.getSecretKey());
                }
            } else {
                return null;
            }
            if(s.isMd5()){
                return DigestUtils.md5Hex(sb.toString());
            }else{
                return s.getSecretKey();
            }
        }
        return null;
    }

	/**
	 * 新房API4.0接口校验
	 * 
	 * @param httpMethod
	 * @param uri
	 * @param pair
	 */
	private URI genXinfangApiV4AuthKey(HttpMethod httpMethod, URI uri,
			AuthKeyPair pair) {
		String query = uri.getQuery();
		StringBuilder auth = null;
		if (StringUtils.isNotBlank(query)) {
			auth = new StringBuilder(query);
		} else {
			auth = new StringBuilder("");
		}
		// StringBuilder auth = new StringBuilder(query);
		if (StringUtils.isNotBlank(query)) {
			auth.append('&');
		}
		auth.append(pair.getType().getAddi()).append("=").append(pair.getSecretKey());
		// 该MD5方式默认使用的是utf8编码，正好符合新房APIV4的定义
		String addedParamValue = DigestUtils.md5Hex(auth.toString());
		StringBuilder newUri = new StringBuilder(uri.toString());
		if (StringUtils.isNotBlank(query)) {
			newUri.append("&").append(pair.getType().getParamKey()).append("=").append(addedParamValue);
		} else {
			newUri.append("?").append(pair.getType().getParamKey()).append("=").append(addedParamValue);
		}
		try {
			uri = new URI(newUri.toString());
		} catch (URISyntaxException e) {
			logger.error("param auth error: ", e);
		}
		return uri;
	}
	
}