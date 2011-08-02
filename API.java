import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class API {
	public String getKey(String secretAccessKey, String data) {
		String mykey = secretAccessKey;
		String test = data;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret = new SecretKeySpec(mykey.getBytes(),
					"HmacSHA1");
			mac.init(secret);
			byte[] digest = mac.doFinal(test.getBytes());
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
