import java.util.Random;

public class Test{

    public static void main(String [] args){

        CarPark park = new CarPark();
        Thread entrance1 = new Thread(new Entrance(park));
        Thread entrance2 = new Thread(new Entrance(park));
        Thread entrance3 = new Thread(new Entrance(park));
        Thread exit1 = new Thread(new Exit(park));
        entrance1.start();
        entrance2.start();
        entrance3.start();
        exit1.start();

        try {
            entrance1.join();
            entrance2.join();
            entrance3.join();
            exit1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
