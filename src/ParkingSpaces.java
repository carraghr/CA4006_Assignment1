import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class Space{

    final ReentrantLock lock = new ReentrantLock();

    Car carParked;

    Space(){
        carParked = null;
    }

    Space(Car car){
        carParked = car;
    }

    public boolean parkCar(Car car){
        if(lock.tryLock()){
            if(carParked == null){
                carParked = car;
                lock.unlock();
                return true;
            }else {
                lock.unlock();
                return false;//car already present in space
            }
        }else{
            return false;
        }
    }

    public void backOutCar(){
        if(lock.isHeldByCurrentThread()) {
            carParked = null;
            lock.unlock();
        }
    }

    public String toString(){
        if(carParked == null){
            return "Space is empty";
        }else{
            return "Space is filled";
        }
    }

    public Car removeCar(){
        lock.lock();
        Car temp = carParked;
        carParked = null;
        lock.unlock();
        return temp;
    }
}

public class ParkingSpaces{

    public Space[] spaces = new Space[1000];
    public Semaphore counter = new Semaphore(1);
    public int spaceCounter = 1000;

    ParkingSpaces(){
        System.out.println("Parking spaces: " + spaces.length);
        for(int i =0; i < spaces.length; i++ ){
            spaces[i] = new Space();
        }
    }

    public Car findSpace(Car car){

        Date date;

        int numberOfSpaces = spaces.length;

        for(int spaceIndex = 0; spaceIndex < numberOfSpaces; spaceIndex = (spaceIndex + 1) % numberOfSpaces){
            if(car.drivingAbility){
                if(spaces[spaceIndex].parkCar(car)){
                    date = new Date();
                    long currentTime = date.getTime();
                    int [] spacesOccupied = {spaceIndex};
                    car.parked(spacesOccupied,currentTime);
                    System.out.println("Car "+ car +" is now parked in space ");// + spaceIndex);
                    try {
                        counter.acquire();
                            spaceCounter -= 1;
                            System.out.println("There is currently " + spaceCounter + " spaces free");
                        counter.release();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    return car;
                }
            }else{
                if(spaceIndex+1<spaces.length){
                    if(spaces[spaceIndex].parkCar(car) && spaces[spaceIndex+1].parkCar(car)){
                        date = new Date();
                        long currentTime = date.getTime();
                        int[] spacesOccupied = {spaceIndex, spaceIndex + 1};
                        car.parked(spacesOccupied, currentTime);
                        System.out.println("Car "+ car +" is now parked in space ");//+ spaceIndex + " "+(spaceIndex+1));
                        try {
                            counter.acquire();
                            spaceCounter -= 2;
                            System.out.println("There is currently " + spaceCounter + " spaces free");
                            counter.release();
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        return car;
                    }else{
                        spaces[spaceIndex].backOutCar();
                        spaces[spaceIndex+1].backOutCar();
                    }
                }
            }
        }
        return null;
    }

    public void removeCar(Car car){
        int [] parkedSpaces = car.parkedSpace.ID;
        for(int i=0; i < parkedSpaces.length; i++){
            spaces[parkedSpaces[i]].removeCar();
        }
        try {
            counter.acquire();
            spaceCounter += parkedSpaces.length;
            System.out.println("There is currently " + spaceCounter + " spaces free");
            counter.release();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}