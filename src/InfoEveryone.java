import java.util.ArrayList;
import java.util.List;

public class InfoEveryone {
	protected List<Person> everyone;
	private List<String> header;
	
	public void setHeader(List<String> header) {
		this.header = new ArrayList<String>();
		this.header = header;
	}
	
	public List<String> getHeader(){
		return this.header;
	}
	
	public void setPeople(List<Person> people) {
		this.everyone = new ArrayList<Person>();
		this.everyone = people;
	}
	
	public List<Person> getPeople() {
		return this.everyone;
	}
}
