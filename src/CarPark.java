import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CarPark{

    Semaphore ticketsForParking = new Semaphore(1050);

    ReentrantLock lock = new ReentrantLock();

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
        lock.lock();
        if (parkingThreadExecutor instanceof ThreadPoolExecutor) {
            if(((ThreadPoolExecutor) parkingThreadExecutor).getActiveCount() == 0 && open.availablePermits() == 0){
                //carsToExit.close();
                System.out.println("WE SHOULD BE GONE BY NOW");
            }
        }
        Car temp = carsToExit.removeCar();
        lock.unlock();
        return temp;
    }

    public void hasExited(Car car){
        ticketsForParking.release();
        System.out.println(/*"There is " + ticketsForParking.availablePermits()+ " tickets left now. Car "+*/car+ " has left the building");
    }

    public void closing(){
        System.out.println("WE SHOULD BE CALLED AROUND 3");
        lock.lock();
        try {
            open.acquire();
            System.out.println("THIS SHIT AGAIN "+open.availablePermits());
        } catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println(" Failed THIS SHIT AGAIN "+open.availablePermits());
        }
        lock.unlock();
    }
}
