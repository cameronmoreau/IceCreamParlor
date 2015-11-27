package teamoortcloud.icecream;

public class IceCreamSundae extends Serving {
	
	boolean hasNuts;
	
	public IceCreamSundae() {
		super();
	}

	@Override
	public double getPrice() {
		double price = super.getPrice();
		if(hasNuts) price++;
		return price;
	}
	
	@Override
	public String getName() {
		return "Sundae";
	}

	@Override
	public int getMaxScoops() {
		return 2;
	}

	@Override
	public int getMaxExtras() {
		return 1;
	}
}
