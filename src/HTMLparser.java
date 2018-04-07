import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class HTMLparser implements parser{
	
	/**
	 * Transforms HTML file at htmlPath into a csv file and stores 
	 * it insider directory dirName
	 * 
	 *  @param htmlPath  location where html file is located at
	 *  @param dirName   location of the directory where transformed csv file will be located in
	 */
	public void transform(String htmlPath, String dirName) throws Exception{
		try {
			// Extract just the file name
			String fileName = htmlPath.substring(htmlPath.lastIndexOf('/') + 1, htmlPath.indexOf(".html")) + ".csv";
			
			FileWriter fileWriter = new FileWriter(new File(dirName, fileName));
			
			//Find html file and input it as a string
			File fileHtml = new File(htmlPath);
			String htmlString = Files.toString(fileHtml, Charsets.UTF_8);
			Document htmlDoc = Jsoup.parse(htmlString);
			Elements directoryTR = htmlDoc.select("#directory tr");
			
			//for the header
			Elements header = directoryTR.get(0).getElementsByTag("th");
			for (Element hCell : header) {
				fileWriter.write(hCell.text().concat(", "));
			}
			
			//for the rest
			for (Element row : directoryTR) {
				Elements cells = row.getElementsByTag("td");
				for (Element cell : cells) {
					fileWriter.write(cell.text().concat(", "));
				}
				fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
