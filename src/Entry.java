public class Entry implements Runnable{

    public EntranceQueue line = new EntranceQueue();
    public CarPark carPark;
    public Entry(EntranceQueue line, CarPark carPark){
        this.line = line;
        this.carPark = carPark;
    }

    @Override
    public void run(){
        while(true){
            //get car from line
            Car temp = this.line.removeCar();
            //place car into car park
            this.carPark.addCar(temp);

            System.out.println("Car " +temp+ " has been added to car park");
        }
    }
}
