import java.util.Random;

/**
 * Created by Richard on 18/03/2017.
 */
public class Test{

    public static void main(String [] args){
        Random choice = new Random();
        long temp = Math.abs(choice.nextLong());
        System.out.println("Long value: " + temp + " In time for sleep" + (temp % (long) 1000));



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
