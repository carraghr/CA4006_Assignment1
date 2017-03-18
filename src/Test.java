/**
 * Created by Richard on 18/03/2017.
 */
public class Test{

    public static void main(String [] args){
        CarPark park = new CarPark();
        Thread entrance = new Thread(new Entrance(park));
        entrance.start();

        try {
            entrance.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
