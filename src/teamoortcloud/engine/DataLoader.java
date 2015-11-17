package teamoortcloud.engine;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import teamoortcloud.people.Cashier;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

public class DataLoader {
	
	private static final int WORKER = 0;
	private static final int CUSTOMER = 1;
	private static final int ICECREAM = 2;
	
	//id, type, name, customers served, scoops served, amount earned, patience/stamina/none
	public static ArrayList<Worker> getWorkers() {
		ArrayList<Worker> workers = new ArrayList<>();
		
		try {
			Scanner fileReader = new Scanner(new FileReader(getFileName(WORKER)));
			String line;
			
			while(fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				String[] fields = line.split(",");
				
				long id = Long.parseLong(fields[0].trim());
				String name = fields[2].trim();
				long served = Long.parseLong(fields[3].trim());
				long scoops = Long.parseLong(fields[4].trim());
				double earned = Double.parseDouble(fields[5].trim());
				
				
				switch(fields[1].trim()) {
				case "Cashier":
					workers.add(new Cashier(
						id, name, served, scoops, earned,
						Integer.parseInt(fields[6].trim())
					));
					break;
					
				case "Stocker":
					workers.add(new Stocker(
							id, name, served, scoops, earned,
							Integer.parseInt(fields[6].trim())
					));
					break;
				case "Worker":
					workers.add(new Worker(id, name, served, scoops, earned));
					break;
				}
			}
			
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return workers;
	}
	
	private static String getFileName(int type) {
		switch(type) {
		case WORKER:
			return "data/TestWorkers.txt";
		case CUSTOMER:
			return "data/TestCustomer.txt";
		case ICECREAM:
			return "data/TestIceCream.txt";
		default:
			return null;
		}
	}
}
