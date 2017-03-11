import java.util.LinkedList;
import java.util.Queue;

public class Entrance implements Runnable{

    public CarPark carPark;
    private LinkedList<Car> line = new LinkedList<>();

    public Entrance(CarPark carPark){
        this.carPark = carPark;
    }

    @Override
    public void run() {
        while(true){

            //need to find a way to very between lectures and students.
            //Need a way to select times that they are going to stay. Some need to be the same
            //Need to find a way to very between good and bad parkers.



            //create a car
            //add the car to a first in first out queue
            //see if the car park is not full
                //if not full place the first car in queue to in car park
            //if the car park is full sleep for a set time
            //and then repeat
        }
    }
}
