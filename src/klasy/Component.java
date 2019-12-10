package klasy;

public class Component {
    private final String name;
    private final double weight;
    private final double price;

    public String getName() {return name;}
    public double getWeight() {return weight;}
    public double getPrice() {return price;}

    public Component(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }
}
