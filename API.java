import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.Data;

import com.google.gson.Gson;

public class API {
	static final String FORMAT_JSON = "application/json";
	static final String FORMAT_ATOM = "application/atom+xml";
	static final String FORMAT_IVES = "application/vnd.ives+xml";
	static final String BASE_URL = "http://api.festivalslab.com";
	private String key;
	private String secret;

	public API(String key, String secret) {
		this.key = key;
		this.secret = secret;
	}
	
	public Event getEvent(String id) {
		return getEvent(id, FORMAT_JSON);
	}

	private Event getEvent(String id, String format) {
		try {
			return parseSingle(request("/events/" + id, format));
		} catch (Exception e) {
			return null;
		}
	}

	public Event[] getEvents(HashMap<String, String> query) {
		return getEvents(query, FORMAT_JSON);
	}

	public Event[] getEvents(HashMap<String, String> query, String format) {
		String params = "";
		for (String key : query.keySet()) {
			params += key + "=" + query.get(key) + "&";
		}
		params = params.substring(0, params.length() - 1);
		try {
			return parse(request("/events?" + params, format));
		} catch (Exception e) {
			return null;
		}
	}

	private Event[] parse(String request) {
		Event[] events = new Gson().fromJson(request, Event[].class);
		return events;
	}
	
	private Event parseSingle(String request) {
		System.out.println(request);
		Event event = new Gson().fromJson(request, Event.class);
		return event;
	}

	private String request(String url, String format) throws Exception {
		String fullUrl = BASE_URL + getSignedUrl(url);
		URL connectionUrl = new URL(fullUrl);
		URLConnection connection = connectionUrl.openConnection();
		connection.setRequestProperty("accept", format);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		String outputLine = "";
		while ((inputLine = in.readLine()) != null) {
			outputLine += inputLine;
		}
		in.close();
		return outputLine;
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
