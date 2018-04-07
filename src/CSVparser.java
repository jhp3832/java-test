import java.io.File;

import org.apache.commons.io.FileUtils;

public class CSVparser implements parser{
	
	/** 
	 * Copy csv file from csvPath to dirName
	 * 
	 *  @param csvPath  location where csv file is located at
	 *  @param dirName  location of the directory where transformed csv file will be located in
	 */
	public void transform(String csvPath, String dirName) throws Exception{
		try {
			File source = new File(csvPath);			
			String fileName = csvPath.substring(csvPath.lastIndexOf('/') + 1, csvPath.indexOf(".csv")) + ".csv";
			FileUtils.copyFile(source, new File(dirName, fileName));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
