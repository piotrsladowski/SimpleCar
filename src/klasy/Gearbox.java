package klasy;

import java.util.HashMap;
import java.util.Map;

public class Gearbox extends Component {
    public Gearbox(String name, double weight, double price, int currentGear, int numberOfGears, double currentGearing) {
        super(name, weight, price);
        this.currentGear = currentGear;
        this.numberOfGears = numberOfGears;
        this.currentGearing = currentGearing;

        double gearing = 0;
        for (int i = 0; i <= numberOfGears; i++) {
            gearing += 0.5;
            this.gearing.put(i, gearing);
        }
    }


    private int currentGear;
    private final int numberOfGears;
    private double currentGearing;

    private Map<Integer, Double> gearing = new HashMap<>();

    public int getCurrentGear() {
        return currentGear;
    }

    public int getNumberOfGears() {
        return numberOfGears;
    }

    public double getCurrentGearing() {
        return currentGearing;
    }

    public  double getMaxGearing(){
        return gearing.get(numberOfGears);
    }

    private void setCurrentGearing(int currentGear) {
        if (currentGear == 0) {currentGearing = 0d;}
        if (currentGear == 1) {currentGearing = 1.0d;}
        if (currentGear == 2) {currentGearing = 1.7d;}
        if (currentGear == 3) {currentGearing = 2.0d;}
        if (currentGear == 4) {currentGearing = 2.5d;}
        if (currentGear == 5) {currentGearing = 2.9d;}
        if (currentGear == 6) {currentGearing = 3.6d;}

    }


    public boolean increaseGear(){
        if(currentGear < numberOfGears){
            currentGear++;
            setCurrentGearing(currentGear);
        }
        else {
            return false;
        }
        return currentGear != numberOfGears;
    }

    public boolean decreaseGear(){
        if(currentGear > 0){
            currentGear--;
            setCurrentGearing(currentGear);
        }
        else {
            return false;
        }
        return currentGear != 0;
    }

}
