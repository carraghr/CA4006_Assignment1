public class Entrance implements Runnable{

    public CarPark carPark;

    private CarQueue line;
    private String name;
    private long[] periodsOfTraffic;
    private long[] genSleepTimes;
    private MinMaxPair[] stayPeriods;


    public Entrance(CarPark carPark, String name,long[] periodsOfTraffic,long[] genSleepTimes,MinMaxPair[] stayPeriods){
        this.carPark = carPark;
        this.name = name;
        this.periodsOfTraffic = periodsOfTraffic;
        this.genSleepTimes = genSleepTimes;
        this.stayPeriods = stayPeriods;

        this.line = new CarQueue(name + " Car queue ");
    }

    @Override
    public void run(){

        Thread carGenerator = new Thread(new CarGenerator(this.line, this.name, periodsOfTraffic, genSleepTimes, stayPeriods));
        Thread carEntry = new Thread(new Entry(this.line, this.carPark));

        carGenerator.start();
        carEntry.start();
        try {
            carGenerator.join();
            carEntry.join();
        }catch(Exception e){}

        System.out.println(name + " IS CLOSED!!!");
    }
}
