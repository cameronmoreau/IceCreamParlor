package teamoortcloud.icecream;

public class IceCreamBananaSplit extends IceCreamSundae {

	public IceCreamBananaSplit() {
		super();
	}
	
	@Override
	public double getPrice() {
		//Fo' da banana
		return super.getPrice() + 2;
	}

	@Override
	public String getName() {
		return "Banana Split";
	}

	@Override
	public int getMaxScoops() {
		return 3;
	}

	@Override
	public int getMaxExtras() {
		return 3;
	}
}
