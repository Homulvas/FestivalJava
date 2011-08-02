import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class API {
	private String key;
	private String secret;
	private boolean debug = false;
	
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
