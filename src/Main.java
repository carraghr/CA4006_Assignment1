import java.util.Date;
import java.util.Random;
class MinMax{
    long min;
    long max;

    MinMax(long min, long max){
        this.min = min;
        this.max = max;
    }

    public long getMax(){
        return max;
    }
    public long getMin(){
        return min;
    }
}

class MinMaxPair{
    MinMax min;
    MinMax max;

    MinMaxPair(MinMax min, MinMax max){
        this.min = min;
        this.max = max;
    }

    public MinMax getMax(){
        return max;
    }

    public MinMax getMin() {
        return min;
    }
}
public class Main {

    public static void main(String [] args){

        Date date = new Date();

        long[] periodsOfTraffic = new long[]{5000l, 20000l,5000l};
        long[] genSleepTimes = new long[]{84l, 25l,84l};

        MinMaxPair[] stayPeriods = new MinMaxPair[]{
                                        new MinMaxPair(new MinMax(1000l,14000l),new MinMax(15000l,30000l)),
                                        new MinMaxPair(new MinMax(100l,1000l),new MinMax(1000l,2000l)),
                                        new MinMaxPair(new MinMax(100l,500l),new MinMax(600l,1000l))};


        CarPark park = new CarPark();
        Thread entrance1 = new Thread(new Entrance(park,"Entrance 1",periodsOfTraffic,genSleepTimes,stayPeriods));
        Thread entrance2 = new Thread(new Entrance(park,"Entrance 2",periodsOfTraffic,genSleepTimes,stayPeriods));
        Thread entrance3 = new Thread(new Entrance(park,"Entrance 3",periodsOfTraffic,genSleepTimes,stayPeriods));
        Thread exit1 = new Thread(new Exit(park));
        Thread exit2 = new Thread(new Exit(park));
        Thread exit3 = new Thread(new Exit(park));


        entrance1.start();
        entrance2.start();
        entrance3.start();
        exit1.start();
        exit2.start();
        exit3.start();

        try {
            entrance1.join();
            entrance2.join();
            entrance3.join();
            System.out.println("We are done");
            return;
            //exit1.join();
            //exit2.join();
            //exit3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
