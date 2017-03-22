public class ParkCarThread implements Runnable{

    Car car;
    ParkingSpaces spaces;
    RemovalQueue queue;

    ParkCarThread(Car car, ParkingSpaces spaces,RemovalQueue queue){
        this.car = car;
        this.spaces = spaces;
        this.queue = queue;
    }

    @Override
    public void run(){
        car = spaces.findSpace(car);
        System.out.println(car);
        queue.addCar(car);
    }
}
