package klasy;

public class Engine extends Component {
    public Engine(String name, double weight, double price, int maxEngineSpeed) {
        super(name, weight, price);

        this.maxEngineSpeed = maxEngineSpeed;
    }

    private int engineSpeed;
    private final int maxEngineSpeed;

    public void increaseEngineSpeed(int delta){
        if(engineSpeed + delta <= maxEngineSpeed){
            engineSpeed += delta;
        }
        else {
            System.out.println("Can't increase engine speed");
        }
    }

    public void decreaseEngineSpeed(int delta){
        if(engineSpeed - delta >= 0){
            engineSpeed -= delta;
        }
        else {
            System.out.println("Can't decrease engine speed");
        }
    }

    public int getMaxEngineSpeed(){
        return maxEngineSpeed;
    }

    public int getEngineSpeed() {
        return engineSpeed;
    }
}
