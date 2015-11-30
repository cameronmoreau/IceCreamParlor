package teamoortcloud.engine;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import teamoortcloud.icecream.IceCream;

import teamoortcloud.people.Cashier;
import teamoortcloud.people.Customer;
import teamoortcloud.people.Stocker;
import teamoortcloud.people.Worker;

public class DataLoader {
	
	public static final int WORKER = 0;
    public static final int CUSTOMER = 1;
    public static final int ICECREAM = 2;

	public static final String DEFAULT_WORKER_PATH = "data/TestWorker.txt";
	public static final String DEFAULT_CUSTOMER_PATH = "data/TestCustomer.txt";
	public static final String DEFAULT_ICECREAM_PATH = "data/TestIceCream.txt";
    public static final String DEFAULT_PATH = "data/filepaths.txt";

	String workerPath, customerPath, icecreamPath;

	public DataLoader() {
        loadPaths();
	}

    public void setNewPath(int type, String path) {
        switch (type) {
            case WORKER:
                workerPath = path;
                break;
            case ICECREAM:
                icecreamPath = path;
                break;
            case CUSTOMER:
                customerPath = path;
                break;
        }

        saveData();
    }

    private void loadPaths() {

        try {
            Scanner fileReader = new Scanner(new FileReader(DEFAULT_PATH));
            String line;

            while(fileReader.hasNextLine()) {
                line = fileReader.nextLine();
                String[] fields = line.split(",");

                switch (fields[0]) {
                    case "Worker":
                        workerPath = fields[1].trim();
                        break;
                    case "Customer":
                        customerPath = fields[1].trim();
                        break;
                    case "IceCream":
                        icecreamPath = fields[1].trim();
                        break;

                    default:
                        break;
                }
            }

        } catch(Exception e) {
            resetPaths();
            loadPaths();
        }
    }

    public void resetPaths() {
        workerPath = DEFAULT_WORKER_PATH;
        customerPath = DEFAULT_CUSTOMER_PATH;
        icecreamPath = DEFAULT_ICECREAM_PATH;

        saveData();
    }

    private void saveData() {
        try {
            PrintWriter writer = new PrintWriter(DEFAULT_PATH, "UTF-8");
            writer.println("Worker," + workerPath);
            writer.println("Customer," + customerPath);
            writer.println("IceCream," + icecreamPath);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath(int type) {
        switch (type) {
            case WORKER:
                return workerPath;
            case ICECREAM:
                return icecreamPath;
            case CUSTOMER:
                return customerPath;

            default:
                return null;
        }
    }

	
	//id, type, name, customers served, scoops served, amount earned, patience/stamina/none
	public ArrayList<Worker> getWorkers() {
		ArrayList<Worker> workers = new ArrayList<>();
		
		try {
			Scanner fileReader = new Scanner(new FileReader(getFilePass(WORKER)));
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
            resetPaths();
            loadPaths();
		}
		
		return workers;
	}
	
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customer = new ArrayList<>();
        try {
        Scanner fileReader = new Scanner(new FileReader(getFilePass(CUSTOMER)));
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
            fileReader.close();
            }
            catch (Exception e) {
            e.printStackTrace();
            resetPaths();
            loadPaths();
        }

        return customer;
	}                    
                                
        public ArrayList<IceCream> getIceCream() {
            ArrayList<IceCream> icecream = new ArrayList<>();
            try {
			Scanner fileReader = new Scanner(new FileReader(getFilePass(ICECREAM)));
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
			fileReader.close();
            }                    
            catch (Exception e) {
			e.printStackTrace();
                resetPaths();
                loadPaths();
		}
		
		return icecream;
	}
        
	/*private static String getFileName(int type) {
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
	}*/
	private String getFilePass(int type)
	{
		switch(type) {
		case WORKER:
			return workerPath;
		case CUSTOMER:
			return workerPath;
		case ICECREAM:
			return workerPath;
		default:
			return null;
		}
	}
}
