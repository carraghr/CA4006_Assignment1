import java.util.Date;
import java.util.Random;

public class CarGenerator implements Runnable{

    public EntranceQueue line = new EntranceQueue();

    public CarGenerator(EntranceQueue line){
        this.line = line;
    }

    @Override
    public void run() {
        try{
            while(true){

                //set driver type based on probability
                //If a college has a student/lecture ration of one lecture for every 5 students
                //then it can be said students have a 50 percent chance of entering the car park,
                //while lectures are 10 percent likely to enter the car park.
                Random choice = new Random();

                String driverType;
                double driverTypeProbability = choice.nextDouble() % 0.6;
                if(driverTypeProbability <= 0.5){
                    driverType = "Student";
                }else{
                    driverType = "Lecture";
                }

                long stayPeriod = Math.abs(choice.nextLong()) % (long) 1000;

                Car temp = new Car(driverType,stayPeriod, choice.nextBoolean());

                line.addCar(temp);

                Date dateTime = new Date();
                long currentTime = dateTime.getTime();
                //TODO change sleep time to a dynamic value for periods of traffic changes
                long sleepTime =  (long)1000;
                long nextGeneration = currentTime + sleepTime;
                while(dateTime.getTime() < nextGeneration){
                    try{
                        Thread.sleep(sleepTime);
                    }catch(InterruptedException e){}
                    dateTime = new Date();
                }
            }
        }catch(Exception e){}
    }
}