package teamoortcloud.icecream;

public class Serving {
	
	IceCream iceCreams[];
	int extras[];
	double price;
	int scoops;
	
	public Serving() {
		iceCreams = new IceCream[this.getMaxScoops()];
		extras = new int[this.getMaxExtras()];
	}
	
	public void printIceCreams() {
		for(int i = 0; i < this.getMaxScoops(); i++) {
			if(iceCreams[i] == null) System.out.println(i + ": null");
			else System.out.println(i + ": " + iceCreams[i].getFlavor());
		}
	}
	
	public double getPrice() {
		double price = 0;
		
		//Base price for icecream
		for(int i = 0; i < this.getMaxScoops(); i++) {
			price += iceCreams[i].getPrice();
		}
		
		//Extras
		for(int i = 0; i < this.getMaxExtras(); i++) {
			//Make sure it's not none
			if(extras[i] != 0) price++;
		}
		
		return price;
	}
	
	public void addIceCreamAtPos(int pos, IceCream icecream) {
		iceCreams[pos] = icecream;
	}
	
	public void addExtraAtPos(int pos, int extra) {
		extras[pos] = extra;
	}
	
	public int getMaxScoops() { 
		return 1; 
	}
	
	public int getMaxExtras() {
		return 0;
	}
}
