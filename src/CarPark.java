import java.util.concurrent.*;

public class CarPark{

    Semaphore spacesAvailable = new Semaphore(1050);

    ExecutorService parkingThreadExecutor = Executors.newFixedThreadPool(105);//10 percent of spaces available

    ParkingSpaces spaces = new ParkingSpaces();

    RemovalQueue carsToBeRemoved = new RemovalQueue();

    CarQueue carsToExit = new CarQueue();


    Thread carRemoveal;

    CarPark(){
        carRemoveal = new Thread(new CarRemovalThread(carsToBeRemoved, spaces, carsToExit));
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

    public Car removeCar(){
        System.out.println("Going to remove");
        Car temp = carsToExit.removeCar();
        return temp;
    }

    public void hasExited(Car car){
        spacesAvailable.release();
        System.out.println("There is " +spacesAvailable.availablePermits()+ " spaces left now. Car "+car+ " has left the building");
    }
}
