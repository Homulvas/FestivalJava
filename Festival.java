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
		map.put("date_from", "2000-01-01 00:00:01");
		map.put("size", Integer.toString(size));
		int from = 0;
		map.put("from", Integer.toString(from));
		Event[] events = api.getEvents(map);
		
		while (events != null) {
			for (Event event : events) {
				if (!event.getLatitude().equals("0")  && !event.getFestival().equals("Edinburgh International Book Festival")) {
					System.out.println(event.getTitle());
					System.out.println(event.getFestival());
					System.out.println(event.getLatitude());
					System.out.println(event.getLongitude());
				}
			}
			from += size;
			map.put("from", Integer.toString(from));
			events = api.getEvents(map);
		}

//		System.out.println(api.getEvent(
//				"2f4755476f2289ffd8dee94a48cce1df54698f46").getTitle());
//		System.out.println(api.getEventFromUrl(
//				"http://api.festivalslab.com/events/2f4755476f2289ffd8dee94a48cce1df54698f46").getTitle());
	}

}
