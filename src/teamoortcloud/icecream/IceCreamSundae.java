package teamoortcloud.icecream;

public class IceCreamSundae extends Serving {
	
	int extras[];

	@Override
	public int getMaxScoops() {
		return 2;
	}

	@Override
	public int getMaxExtras() {
		return 1;
	}

	
}
