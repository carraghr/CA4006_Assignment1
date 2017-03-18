import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

class Space{

    int [] ID;

    public Space(int []ID){
        this.ID = ID;
    }

    public int getSize(){
        return ID.length;
    }
}

public class CarPark{

    Car [] carSpaces = new Car[1000];

    PriorityBlockingQueue leavingCars = new PriorityBlockingQueue();

    LinkedList<Car> exitQueue = new LinkedList<>();
    Semaphore spacesAvailable = new Semaphore(1050);

    LinkedList<Car>  parkingQueue = new LinkedList<>();

    public void addCar(Car car){
        try {
            spacesAvailable.acquire();
            //need to fine a space after this
            parkingQueue.add(car);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean findSpace(Car car){
        Date date = new Date();
        long currentTime = date.getTime();
        for(int spaceIndex = 0; spaceIndex < carSpaces.length; spaceIndex++){
            if(car.drivingAbility){
                if(carSpaces[spaceIndex] != null){
                    int [] spacesOccupied = {spaceIndex};
                    car.parked(spacesOccupied,currentTime);
                    carSpaces[spaceIndex] = car;
                    leavingCars.add(car);
                    return true;
                }
            }else{
                if(spaceIndex + 1 < carSpaces.length ) {
                    if(carSpaces[spaceIndex] != null && carSpaces[spaceIndex + 1] != null){
                        int[] spacesOccupied = {spaceIndex, spaceIndex + 1};
                        car.parked(spacesOccupied, currentTime);
                        carSpaces[spaceIndex] = car;
                        carSpaces[spaceIndex + 1] = car;
                        leavingCars.add(car);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void removeCarFromSpace(Car car){
        int [] spacesOccupied = car.parkedSpace.ID;

        for(int i=0; i < spacesOccupied.length; i++){
            carSpaces[spacesOccupied[i]] = null;
        }
    }

}
