package teamoortcloud.icecream;

public class IceCreamSoda extends Serving {

	public IceCreamSoda() {
		super();
	}
	
	@Override
	public String getName() {
		return "Ice Cream Soda";
	}
	
	@Override
	public int getMaxScoops() {
		return 2;
	}	
}
