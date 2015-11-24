package teamoortcloud.icecream;

public class IceCream {
    long id;
    String flavor;
    double price;
    String name;
    String description;
    int scoops;
    
    public IceCream(long id, double price, String flavor, String name, String description, int scoops) {
		super();
		this.id = id;
		this.price = price;
		this.flavor = flavor;
		this.name = name;
		this.description = description;
        this.scoops = scoops;
                
	}
    
    @Override
    public String toString() {
		return "IceCream [id=" + id + ", flavor=" + flavor + ", price=" + price +
                    ", name=" + name + ", description=" + description + ", scoops=" + scoops +"]\n";
	}
}
