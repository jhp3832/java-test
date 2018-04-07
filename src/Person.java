import java.util.Map;

public class Person {
	private String id = "";
	protected Map<String, String> records;
	
	public void recordId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void putAttributes(String attribute, String value) {
		records.put(attribute, value);
	}
	
	public Map<String,String> getRecord(){
		return this.records;
	}
}
