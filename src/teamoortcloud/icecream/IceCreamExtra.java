package teamoortcloud.icecream;

public class IceCreamExtra {
	
	public static final int NONE = 0;
	public static final int STRAWBERRY = 1;
	public static final int CHOCOLATE = 2;
	public static final int MARSHMELLOW = 3;
	public static final int PINEAPPLE = 4;
	public static final int KETCHUP = 5;
	public static final int MUSTARD = 6;
	public static final int PICKLE_RELISH = 7;
	
	public static String getName(int index) {
		
		switch(index) {
		case STRAWBERRY:
			return "Strawberry";
		case CHOCOLATE:
			return "Chocolate Syrup";
		case MARSHMELLOW:
			return "Marshmallow Cream";
		case PINEAPPLE:
			return "Pineapple";
		case KETCHUP:
			return "Ketchup";
		case MUSTARD:
			return "Mustard";
		case PICKLE_RELISH:
			return "Pickle Relish";
		default:
			return "None";
		}
		
	}
	
	public static String[] getAll() {
		final int max = 8;
		String[] s = new String[max];
		for(int i = 0; i < max; i++) s[i] = IceCreamExtra.getName(i);
		return s;
	}
}
