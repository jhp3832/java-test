import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}
		// Create directory for the .txt files
		String dirName = "directory";
		new File(dirName).mkdirs();
		
		//Transform input files of all types into a csv file and store it in dir "directory"
		for (int i = 0; i < args.length; i++) {
			String inputType = args[i].substring(args[i].lastIndexOf('.') + 1);
			switch (inputType){
				case "csv":
					CSVparser csvParser = new CSVparser();
					csvParser.transform(args[i], dirName);
					break;
				case "html":
					HTMLparser htmlParser = new HTMLparser();
					htmlParser.transform(args[i], dirName);
					break;
					
				// Can easily add in different type(s) in the future
			}
		}
		Merger merger = new Merger();
		InfoEveryone infoEveryone = merger.merge("directory");
		List<String> headers = infoEveryone.getHeader();
		
		PrintWriter pw = new PrintWriter(new File(FILENAME_COMBINED));
		String headerString = String.join(",", headers);
		headerString = headerString.concat("\n");
		pw.write(headerString);
		
		for(Person person : infoEveryone.getPeople()) {
			StringBuilder sb = new StringBuilder();
			Map<String, String> personMap = person.getRecord();
			for (String key : headers) {
				if (personMap.keySet().contains(key)) {
					sb.append(personMap.get(key).concat(", "));
				}else {
					sb.append("null, ");
				}
			}
			sb.append("\n");
			pw.write(sb.toString());
		}
		pw.close();
		 
	}
}
