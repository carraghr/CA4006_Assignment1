import java.util.concurrent.*;

public class CarPark{

    Semaphore ticketsForParking = new Semaphore(1050);

    Semaphore open = new Semaphore(3);

    ExecutorService parkingThreadExecutor;//10 percent of spaces available

    ParkingSpaces spaces = new ParkingSpaces();

    RemovalQueue carsToBeRemoved = new RemovalQueue();

    CarQueue carsToExit = new CarQueue("carsToExit");


    Thread carRemoveal;

    CarPark(){
        parkingThreadExecutor = Executors.newFixedThreadPool(110);//10 percent of spaces available
        carRemoveal = new Thread(new CarRemovalThread(carsToBeRemoved, spaces, carsToExit));
        carRemoveal.start();
    }

    public void addCar(Car car){
        try {
            ticketsForParking.acquire();
            Thread parkingThread = new Thread(new ParkCarThread(car, this.spaces,carsToBeRemoved));
            parkingThreadExecutor.execute(parkingThread);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Car removeCar(){
        Car temp = carsToExit.removeCar();
        return temp;
    }

    public void hasExited(Car car){
        ticketsForParking.release();
        System.out.println(/*"There is " + ticketsForParking.availablePermits()+ " tickets left now. Car "+*/car+ " has left the building");
    }

    public void closing(){
        try {
            open.acquire();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
