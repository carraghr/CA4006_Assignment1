import java.util.Date;
import java.util.Random;

public class CarGenerator implements Runnable{

    public CarQueue line = new CarQueue();

    public CarGenerator(CarQueue line){
        this.line = line;
    }

    @Override
    public void run() {
        try{
            while(true){

                Random choice = new Random();

                String driverType;
                double driverTypeProbability = choice.nextDouble() % 0.6;
                if(driverTypeProbability <= 0.5){
                    driverType = "Student";
                }else{
                    driverType = "Lecture";
                }

                //True will be for good driving ability.
                boolean drivingAbility;
                double drivingAbilityProbability = choice.nextDouble() % 0.7;

                if(drivingAbilityProbability <= 0.6){
                    drivingAbility = true;
                }else{
                    drivingAbility = false;
                }

                long stayPeriod = Math.abs(choice.nextLong()) % (long) 10000;

                Car temp = new Car(driverType,stayPeriod,drivingAbility);//choice.nextBoolean());

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