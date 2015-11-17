package teamoortcloud.engine;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import teamoortcloud.icecream.IceCream;

import teamoortcloud.people.Cashier;
import teamoortcloud.people.Customer;
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
	
        public static ArrayList<Customer> getCustomers() {
            ArrayList<Customer> customer = new ArrayList<>();
            try {
			Scanner fileReader = new Scanner(new FileReader(getFileName(CUSTOMER)));
			String line;
			
			while(fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				String[] fields = line.split(",");
                                long id = Long.parseLong(fields[0].trim());
				String name = fields[1].trim();
				double wallet= Double.parseDouble(fields[2].trim());
				int happiness = Integer.parseInt(fields[3].trim());
				int pennies= Integer.parseInt(fields[4].trim());
                                int nickles= Integer.parseInt(fields[5].trim());
                                int dimes= Integer.parseInt(fields[6].trim());
                                int quarters= Integer.parseInt(fields[7].trim());
                                int dollar1= Integer.parseInt(fields[8].trim());
                                int dollar5= Integer.parseInt(fields[9].trim());
                                int dollar10= Integer.parseInt(fields[10].trim());
                                int dollar20= Integer.parseInt(fields[11].trim());
                                
                                customer.add(new Customer(id, name, wallet, happiness, pennies, nickles, 
                                        dimes, quarters, dollar1, dollar5, dollar10, dollar20));
                        }        
            }                    
            catch (Exception e) {
			e.printStackTrace();
		}
		
		return customer;
	}                    
                                
        public static ArrayList<IceCream> getIceCream() {
            ArrayList<IceCream> icecream = new ArrayList<>();
            try {
			Scanner fileReader = new Scanner(new FileReader(getFileName(ICECREAM)));
			String line;
			
			while(fileReader.hasNextLine()) {
				line = fileReader.nextLine();
				String[] fields = line.split(",");
                                long id = Long.parseLong(fields[0].trim());
                                double price= Double.parseDouble(fields[1].trim());
				String flavor = fields[2].trim();
				String name = fields[3].trim();
				String description= fields[4].trim();
                                int scoops= Integer.parseInt(fields[5].trim());
                                icecream.add(new IceCream(id, price, flavor, name, description, scoops));
                        }        
            }                    
            catch (Exception e) {
			e.printStackTrace();
		}
		
		return icecream;
	}
        
	private static String getFileName(int type) {
		switch(type) {
		case WORKER:
			return "data/TestWorker.txt";
		case CUSTOMER:
			return "data/TestCustomer.txt";
		case ICECREAM:
			return "data/TestIceCream.txt";
		default:
			return null;
		}
	}
}
