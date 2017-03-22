/**
 * Created by richa on 21/03/2017.
 */
public class CarRemovalThread implements Runnable{

    public RemovalQueue queue;
    public ParkingSpaces spaces;
    public CarQueue exitQueue;

    CarRemovalThread(RemovalQueue queue,ParkingSpaces spaces,CarQueue exitQueue){
        this.queue = queue;
        this.spaces = spaces;
        this.exitQueue = exitQueue;
    }

    @Override
    public void run(){
        while(true){
            /**
             * removeCar method is returning when it should be putting the current thread a sleep.
             */
            Car car = queue.removeCar();
            System.out.println("CAR IS BEEN REMOVED FROM SPACE " + car);
            if(car != null) {
                spaces.removeCar(car);
                exitQueue.addCar(car);
            }
        }
    }
}