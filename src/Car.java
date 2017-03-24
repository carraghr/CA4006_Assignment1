public class Car implements Comparable<Car>{

    public class Spaces{

        int [] ID;

        public Spaces(int []ID){
            this.ID = ID;
        }

        public int getSize(){
            return ID.length;
        }
    }

    String driverType;

    long stayPeriod;
    long leavingTime;

    long timeStamp;

    boolean drivingAbility;

    Spaces parkedSpace;

    Car(String driverType,long stayPeriod, boolean drivingAbility){
        this.driverType = driverType;
        this.stayPeriod = stayPeriod;
        this.drivingAbility = drivingAbility;
    }

    public void setLeavingTime(long parkedTime){
        timeStamp = parkedTime;
        leavingTime = parkedTime  + stayPeriod;
    }

    public void parked(int [] spaceIndex, long parkedTime){
        parkedSpace = new Spaces(spaceIndex);
        leavingTime = parkedTime  + stayPeriod;
    }

    @Override
    public int compareTo(Car otherCar) {
        long difference = this.leavingTime - otherCar.leavingTime;

        if(difference > 0){
            return 1;
        }else{
            return -1;
        }
        //if its the same the queue wont be updated.
    }

    @Override
    public String toString(){
        return ""+leavingTime;//"" + this.driverType + " is staying for " + leavingTime + " is driving with ability " + this.drivingAbility;
    }

    public boolean equals(Car car){
        //todo create this method
        return car.driverType.equals(driverType) && (car.leavingTime == leavingTime) && car.drivingAbility == drivingAbility;
    }
}
