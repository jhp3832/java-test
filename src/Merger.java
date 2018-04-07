import java.io.BufferedReader;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Merger {
	//Decided not to use JDBC as we might not know attributes and 
	//number of attributes might be different
	
	/**
	 * Returns merged info of every row of each file inside the 
	 * directory using class InfoEveryone 
	 *
	 * @param dirName  name of the directory where all csv file exists
	 */
	public InfoEveryone merge(String dirName) {
		//List to hold every row's info
		List<Person> everyone = new ArrayList<Person>();
		//To be returned
		InfoEveryone infoEveryone = new InfoEveryone();
		Set<String> everyHeaders = new HashSet<String>();
		try {
			File dir = new File(dirName);

			List<String>previousHeaders = new ArrayList<String>();
			Set<String> idCollection = new HashSet<String>();
			
			for (File fileEntry : dir.listFiles()) {
				Path logFile = Paths.get(fileEntry.getAbsolutePath());
				BufferedReader reader = Files.newBufferedReader(logFile, StandardCharsets.UTF_8);
				
				String currRow;
				boolean firstRow = true;
				int idIndex = -1;
				List<String> headers = new ArrayList<String>();
				
				while ((currRow = reader.readLine()) != null) {
					// If the the fileEntry is html get rid of comma at the end for each loop
					if (currRow.charAt(currRow.length() - 2) == ',') {
						currRow = currRow.substring(0, currRow.length() - 2);
					} else {
						// For csv get rid of quotation marks
						currRow = currRow.replaceAll("\"", "");
					}
					
					// Header row
					if (firstRow) {
						currRow = currRow.replaceAll("\\s+","");
						List<String> splited = Arrays.asList(currRow.split(","));
						
						//Remember the col num of ID for later use
						idIndex = splited.indexOf("ID");
						headers.addAll(splited);
						everyHeaders.addAll(splited);
						
						firstRow = false;
					} 
					// Non-header rows
					else {
						// Every row represents a person
						Person currPerson = new Person();
						currPerson.records = new HashMap<String, String>();
						boolean hasDuplicate = false;
						
						List<String> cells = Arrays.asList(currRow.split(","));
						//ID of the currPerson
						String idVal = cells.get(idIndex);
						
						//If attribute ID exists
						if (idIndex != -1) {
							// There already exist someone with the same id
							if (idCollection.contains(idVal)) {
								// Find that person from everyone and delete but store that info
								for (Person person : everyone) {
									String personId = person.getId();
									if (personId.equals(idVal)) {
										currPerson = person;
										everyone.remove(person);
										hasDuplicate = true;
									}
								}
							}
							int cellNum = 0;
							for (String currCell : cells) {
								// Duplicate exists and already contains the same info
								if (hasDuplicate && previousHeaders.contains(headers.get(cellNum))) {
									cellNum++;
									continue;
								}
								currPerson.putAttributes(headers.get(cellNum), currCell);
								currPerson.recordId(idVal);
								cellNum++;
							}
							everyone.add(currPerson);
							idCollection.add(idVal);
						}else {
							System.out.println("ID attribute does not exist");
						}
					}	
				} previousHeaders = headers;
			}
		}catch (Exception e){
			e.getStackTrace();
		}
		List<String> listHeaders = new ArrayList<String>();
		listHeaders.addAll(everyHeaders);
		infoEveryone.setHeader(listHeaders);
		infoEveryone.setPeople(everyone);

		return infoEveryone;
	}
}
