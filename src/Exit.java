/**
 * Created by richa on 07/03/2017.
 */
public class Exit implements Runnable{

    public CarPark carPark;

    public Exit(CarPark carPark){
        this.carPark = carPark;
    }

    @Override
    public void run() {
        while(true){
            //check if there is a car waiting to be removed.
            //if its not wait till some wake up call.
            //if there is car waiting remove from the list
                // wait a while after and repeat so it acts like a car been move
                //out and barriers going back down.
        }
    }
}
