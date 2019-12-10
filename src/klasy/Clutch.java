package klasy;

public class Clutch extends Component {
    public Clutch(String name, double weight, double price) {
        super(name, weight, price);
    }

    private Boolean isClutchPushed;

    public Boolean getIsClutchPushed() {
        return isClutchPushed;
    }

    public void push(){
        isClutchPushed = true;
    }
    public void zwolnij(){
        isClutchPushed = false;
    }
}
