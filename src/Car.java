public class Car implements Comparable<Car>{
    String driverType;

    long stayPeriod;
    long leavingTime;

    boolean drivingAbility;

    Space parkedSpace;

    Car(String driverType,long stayPeriod, boolean drivingAbility){
        this.driverType = driverType;
        this.leavingTime = stayPeriod;
        this.drivingAbility = drivingAbility;
    }

    public void setLeavingTime(long parkedTime){
        leavingTime = parkedTime  + stayPeriod;
    }

    public void parked(int [] spaceIndex, long parkedTime){
        parkedSpace = new Space(spaceIndex);
        leavingTime = parkedTime  + stayPeriod;
    }

    @Override
    public int compareTo(Car otherCar) {
        long difference = this.leavingTime - otherCar.leavingTime;
        if(difference > 0){
            return 1;
        }else if(difference < 0){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString(){
        return "" + this.driverType + " is staying for " + leavingTime + " is driving with ability " + this.drivingAbility;
    }
}
