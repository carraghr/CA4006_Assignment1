/**
 * Created by richa on 07/03/2017.
 */
public class Exit implements Runnable{

    public CarPark carPark;
    public CarQueue line;

    public Exit(CarPark carPark){
        this.carPark = carPark;
        line = new CarQueue();
    }

    /**
     * Need to threads one to remove cars from the car park exit queue and place in a queue to be removed
     * The other removes the car completely from the car park and waits a time for each car to be removed
     */
    @Override
    public void run() {

        //Thread to place car been removed into exit queue
        Thread carPlacer = new Thread(new CarPlacement(this.line, this.carPark));
        //Thread to remove car completely from car park building
        Thread carExit = new Thread(new Exiter(this.line,this.carPark));

        carPlacer.start();
        carExit.start();

        try {
            carPlacer.join();
            carExit.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
