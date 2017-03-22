public class Exiter implements Runnable{

    CarQueue line;
    CarPark carPark;

    Exiter(CarQueue line, CarPark carPark){
        this.line = line;
        this.carPark = carPark;
    }

    @Override
    public void run() {
        while(true){
            Car temp = line.removeCar();
            if(temp != null){
                carPark.hasExited(temp);
            }
        }
    }
}
