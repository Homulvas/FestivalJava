import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class API {
    static final String FORMAT_JSON = "application/json";
    static final String FORMAT_ATOM = "application/atom+xml";    
    static final String FORMAT_IVES = "application/vnd.ives+xml";
    static final String BASE_URL = "http://api.festivalslab.com";
	private String key;
	private String secret;
	private boolean debug = false;
	
	public String getEvents(HashMap<String, String> query) {
		return getEvents(query, FORMAT_JSON);
	}
	
	public String getEvents(HashMap<String, String> query, String format) {
		String params = "";
		for (String key : query.keySet()) {
			params += key + "=" + query.get(key) + "&";
		}
		params = params.substring(0, params.length() - 1);
		return "/events?" + params;
	}
	
	private String getSignedUrl(String url) {
		if (url.contains("?")) {
			url += "&key=" + key;
		} else {
			url += "?key=" + key;
		}
		url += "&signature=" + getSignature(url);
		return url;
	}
	
	private String getSignature(String data) {
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(),
					"HmacSHA1");
			mac.init(secretKey);
			byte[] digest = mac.doFinal(data.getBytes());
			String key = new String();
			for (byte b : digest) {
				key += String.format("%02x", b);
			}
			return key;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
