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
    
    public IceCream(long id, String name, double price, String flavor)
    {
    	super();
    	this.id=id;
    	this.name=name;
    	this.price=price;
    	this.flavor=flavor;
    	this.description="You don't get a description";
    	this.scoops=80;
    }
    
    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFlavor() {
		return flavor;
	}



	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getScoops() {
		return scoops;
	}



	public void setScoops(int scoops) {
		this.scoops = scoops;
	}



	@Override
    public String toString() {
		return "IceCream [id=" + id + ", flavor=" + flavor + ", price=" + price +
                    ", name=" + name + ", description=" + description + ", scoops=" + scoops +"]\n";
	}
}
