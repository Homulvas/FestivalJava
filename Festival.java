import java.util.HashMap;

public class Festival {

	
	static final int size = 10;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		API api = new API("3FPE9X151AMKIqrv",
				"V6KLOjmYaz8r_cYWTKIfVPfkHIiIj7Ha");
		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("festival", "jazz");
//		map.put("size", Integer.toString(size));
//		int from = 0;
//		map.put("from", Integer.toString(from));
//		Event[] events = api.getEvents(map);
//		
//		while (events != null) {
//			for (Event event : events) {
//				System.out.println(event.getTitle());
//			}
//			from += size;
//			map.put("from", Integer.toString(from));
//			events = api.getEvents(map);
//		}

		System.out.println(api.getEvent(
				"2f4755476f2289ffd8dee94a48cce1df54698f46").getTitle());
		System.out.println(api.getEventFromUrl(
				"http://api.festivalslab.com/events/2f4755476f2289ffd8dee94a48cce1df54698f46").getTitle());
	}

}
