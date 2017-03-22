/**
 * Created by richa on 21/03/2017.
 */
public class CarRemovalThread implements Runnable{

    public RemovalQueue queue;
    public ParkingSpaces spaces;

    CarRemovalThread(RemovalQueue queue,ParkingSpaces spaces){
        this.queue = queue;
        this.spaces = spaces;
    }

    @Override
    public void run() {
        while(true){
            /**
             * removeCar method is returning when it should be putting the current thread a sleep.
             */
            Car car = queue.removeCar();
            System.out.println("CAR IS BEEN REMOVED " + car);
            if(car != null) {
                spaces.removeCar(car);

            }
        }
    }
}
