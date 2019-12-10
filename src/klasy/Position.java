package klasy;

public class Position{
    private double x;
    private double y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void update(Position destination, double currentSpeed, double dt){
        double distance = Math.sqrt(Math.pow(destination.x-x,2) + Math.pow(destination.y -y, 2));
/*        if (distance < 1.5){
            x = destination.x;
            y = destination.y;
        }*/
        if (x != destination.x || y != destination.y) {
            double dx, dy;
            double cosine = ((destination.x - x) / distance);
            double sine = ((destination.y - y) / distance);
            dx = currentSpeed * cosine * dt;
            dy = currentSpeed * sine * dt;
            if (dx >= Math.abs(distance * cosine) || dy >= Math.abs(distance * sine)) {
                x = destination.x;
                y = destination.y;
            } else {
                x = (x + dx);
                y = (y + dy);
            }
        }
    }
}
