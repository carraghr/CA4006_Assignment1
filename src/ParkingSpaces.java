import java.util.Date;
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
                return false;
            }
        }else{
            return false;
        }
    }

    public void removeCar(){
        if(lock.isHeldByCurrentThread()) {
            carParked = null;
            lock.unlock();
        }
    }

    public boolean isOccupied(){
        if(lock.tryLock()){
            boolean temp = carParked != null;
            lock.unlock();
            return temp;
        }else{
            return true;
        }
    }

    public String toString(){
        if(carParked == null){
            return "Space is empty";
        }else{
            return "Space is filled";
        }
    }

    public Car carLeaving(){
        lock.lock();
        Car temp = carParked;
        carParked = null;
        lock.unlock();
        return temp;
    }
}

public class ParkingSpaces{

    public Space[] spaces = new Space[100];

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
                    System.out.println("Car is now parked in " + spaceIndex);
                    for(int i =0; i < spaces.length; i++ ){
                      //  System.out.println("Space at index " + i +" is "+ spaces[i]);
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
                        System.out.println("Car is now parked in " + spaceIndex + " "+(spaceIndex+1));
                        for(int i =0; i < spaces.length; i++ ){
                          //  System.out.println("Space at index " + i +" is "+ spaces[i]);
                        }
                        return car;
                    }else{
                        spaces[spaceIndex].removeCar();
                        spaces[spaceIndex+1].removeCar();
                    }
                }
            }
        }
        return null;
    }


    public void removeCar(Car car){
        int [] parkedSpaces = car.parkedSpace.ID;
        for(int i=0; i < parkedSpaces.length; i++){
            System.out.println("Space been freed: "+parkedSpaces[i]  );
            spaces[parkedSpaces[i]].carLeaving();
        }
    }
}