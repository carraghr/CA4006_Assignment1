import java.util.concurrent.*;

public class CarPark{

    Semaphore spacesAvailable = new Semaphore(1050);

    ExecutorService parkingThreadExecutor = Executors.newFixedThreadPool(100);

    ParkingSpaces spaces = new ParkingSpaces();

    RemovalQueue carsToBeRemoved = new RemovalQueue();


    Thread carRemoveal;

    CarPark(){
        carRemoveal = new Thread(new CarRemovalThread(carsToBeRemoved, spaces));
        carRemoveal.start();
    }

    public void addCar(Car car){
        try {
            spacesAvailable.acquire();
            Thread parkingThread = new Thread(new ParkCarThread(car, this.spaces,carsToBeRemoved));
            parkingThreadExecutor.execute(parkingThread);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }


}
