package teamoortcloud.icecream;

public class IceCreamCone extends Serving {

	public IceCreamCone() {
		super();
	}
	
	@Override
	public String getName() {
		return "Ice Cream Cone";
	}
	
	@Override
	public int getMaxScoops() {
		return 3;
	}
	
}
