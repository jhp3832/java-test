
public interface parser {
	// Every parser should transform file into csv file.
	void transform(String htmlFile, String dirName) throws Exception;
	
}
