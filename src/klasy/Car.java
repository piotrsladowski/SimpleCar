package klasy;

public class Car extends Thread{
    public Car(Boolean isStarted, String registrationNumber, String model, Clutch clutch, Gearbox gearbox, Engine engine) {
        this.isStarted = isStarted;
        this.registrationNumber = registrationNumber;
        this.model = model;
        //this.maxSpeed = maxSpeed;
        this.gearbox = gearbox;
        this.clutch = clutch;
        this.engine = engine;
        this.maxSpeed = gearbox.getCurrentGearing() * gearbox.getNumberOfGears();
    }

    //region variables
    private final Clutch clutch;
    private final Gearbox gearbox;
    private final Engine engine;
    private final Position position = new Position(0,0);
    private final Position destination = new Position(0,0);
    private Boolean isStarted;
    private final String registrationNumber;
    private final String model;
    private double maxSpeed;
    private double currentSpeed;


    private final int tyreCircus = 2;

    //endregion


    public Position getPosition() {
        return position;
    }

    public void turnOn(){
        engine.increaseEngineSpeed(200);
        isStarted = true;
    }

    public void turnOff(){
        engine.decreaseEngineSpeed(engine.getEngineSpeed());
        isStarted = false;
    }

    public double getWeight(){
        double weight = gearbox.getWeight() + clutch.getWeight() + engine.getWeight();
        return weight;
    }


    public void faster(int delta){
        engine.increaseEngineSpeed(delta);
    }

    public void run(){
        while(true){
                try{
                    double dt = 0.1;
                    double destX = destination.getX();
                    double destY = destination.getY();
                    double x = position.getX();
                    double y = position.getY();
                    currentSpeed = getCarSpeed();
                    if(destX != x || destY != y){
                        position.update(destination, currentSpeed, dt);
                    }
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }

    public void driveTo(int newX, int newY){
        destination.setX(newX);
        destination.setY(newY);
    }


    public double getCarSpeed() {
        if(!isStarted){
            return 0;
        }
        else {
            return engine.getEngineSpeed() * gearbox.getCurrentGearing() * 60 * tyreCircus * Math.PI * 0.00001;
        }
    }

    public double getEngineSpeed() {
        return engine.getEngineSpeed();
    }

    public boolean increaseGear(){
        return gearbox.increaseGear();
    }

    public boolean decreaseGear(){
        return gearbox.decreaseGear();
    }

    public double getMaxSpeed() {
        return gearbox.getMaxGearing() * engine.getMaxEngineSpeed() * 60 * tyreCircus * Math.PI * 0.00001;
    }

    public String getModel() {
        return model;
    }

    public int getCurrentGear(){
        return gearbox.getCurrentGear();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
