import java.util.Date;

public class Entry implements Runnable{

    public CarQueue line;
    public CarPark carPark;
    public Entry(CarQueue line, CarPark carPark){
        this.line = line;
        this.carPark = carPark;
    }

    @Override
    public void run(){
        try {
            while(true) {
                Car temp = this.line.removeCar();
                //place car into car park
                this.carPark.addCar(temp);
                System.out.println("Car " + temp + " has been added to car park");
            }
        }catch(Exception e){
            carPark.closing();
            System.out.println("THIS SHOULD APPEAR NOW!!");
        }
    }
}
