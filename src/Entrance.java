import java.util.LinkedList;
import java.util.Queue;

public class Entrance implements Runnable{

    public CarPark carPark;
    public EntranceQueue line = new EntranceQueue();

    public Entrance(CarPark carPark){
        this.carPark = carPark;
    }

    @Override
    public void run(){

        //create two threads.
        System.out.println("Starting instance of Entrance");
        Thread carGenerator = new Thread(new CarGenerator(this.line));
        Thread carEntry = new Thread(new Entry(this.line,this.carPark));

        carGenerator.start();
        carEntry.start();


        while(true){
            //create a car
            //add the car to a first in first out queue
            //see if the car park is not full
                //if not full place the first car in queue to in car park
            //if the car park is full sleep for a set time
            //and then repeat
        }
    }
}
