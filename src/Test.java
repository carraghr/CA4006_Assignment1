import java.util.Date;
import java.util.PriorityQueue;

/**
 * Created by richa on 23/03/2017.
 */
public class Test {

    public static void main(String [] args){
        Date date = new Date();
        long cT = date.getTime();

        Car one = new Car("one",1l,true);
        Car two = new Car("two",1l,true);

        PriorityQueue cars = new PriorityQueue();

        int [] a = {1,1};

        one.parked(a, cT - 2l);
        two.parked(a, cT);

        cars.add(one);
        System.out.println(cars.peek());
        cars.add(two);
        System.out.println(cars.remove());
        System.out.println(cars.remove());

        System.out.println(cars.size());
    }
}
