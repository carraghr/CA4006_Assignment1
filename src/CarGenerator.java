import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CarGenerator implements Runnable{

    public CarQueue line;

    private String entranceID;
    private long[] periodsOfTraffic;
    private long[] genSleepTimes;
    private MinMaxPair[] stayPeriods;

    public CarGenerator(CarQueue line, String entranceID,long[] periodsOfTraffic,long[] genSleepTimes,MinMaxPair[] stayPeriods){
        this.line = line;
        this.entranceID = entranceID;
        this.periodsOfTraffic = periodsOfTraffic;
        this.genSleepTimes = genSleepTimes;
        this.stayPeriods = stayPeriods;
    }

    @Override
    public void run() {
        try{
            Date date;
            for(int periodIndex = 0; periodIndex < stayPeriods.length; periodIndex++) {
                System.out.println("STARTING OF PERIOD " + periodIndex + " FOR " + entranceID);
                date = new Date();
                long currentTime = date.getTime();
                long periodEndTime = currentTime + periodsOfTraffic[periodIndex];

                while(currentTime < periodEndTime) {
                    Random choice = new Random();

                    String driverType;
                    double driverTypeProbability = choice.nextDouble() % 0.6;
                    if(driverTypeProbability <= 0.5) {
                        driverType = "Student";
                    } else {
                        driverType = "Lecture";
                    }

                    //True will be for good driving ability.
                    boolean drivingAbility;
                    double drivingAbilityProbability = choice.nextDouble() % 0.7;

                    if(drivingAbilityProbability <= 0.6) {
                        drivingAbility = true;
                    } else {
                        drivingAbility = false;
                    }


                    //first get probability if car is staying long or short periodEndTime
                    long stayPeriod = (long) 0;
                    while(stayPeriod == (long) 0) {
                        MinMax values;
                        if(periodIndex == 0) {
                            double stayPeriodProbability = choice.nextDouble() % 0.6;
                            if(stayPeriodProbability <= 0.5) {//stay long period
                                values = stayPeriods[periodIndex].getMax();
                            } else {
                                values = stayPeriods[periodIndex].getMin();
                            }
                        } else if(periodIndex == 1) {
                            double stayPeriodProbability = choice.nextDouble();
                            if(stayPeriodProbability <= 0.8) {//stay short period
                                values = stayPeriods[periodIndex].getMin();
                            } else {
                                values = stayPeriods[periodIndex].getMax();
                            }
                        } else {
                            double stayPeriodProbability = choice.nextDouble() % 0.6;
                            if(stayPeriodProbability <= 0.5) {//stay long period
                                values = stayPeriods[periodIndex].getMin();
                            } else {
                                values = stayPeriods[periodIndex].getMax();
                            }
                        }

                        long min = values.getMin();
                        long max = values.getMax();

                        stayPeriod = (ThreadLocalRandom.current().nextLong(0, max - min)) + min;
                    }

                    Car temp = new Car(driverType, stayPeriod, drivingAbility);

                    line.addCar(temp);

                    int size = line.size();

                    //System.out.println("" + size + " cars waiting at " + entranceID);

                    Date dateTime = new Date();
                    currentTime = dateTime.getTime();

                    long sleepTime = genSleepTimes[periodIndex];
                    long nextGeneration = currentTime + sleepTime;
                    while(currentTime < nextGeneration) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch(InterruptedException e) {}
                        dateTime = new Date();
                        currentTime = dateTime.getTime();
                    }
                    date = new Date();
                    currentTime = date.getTime();
                }
            }
        }catch(Exception e){}
        line.close();
        System.out.println("Im Colosing this sht sonsofnsld");
    }
}