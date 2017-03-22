public class CarPlacement implements Runnable {

    CarQueue line;
    CarPark carPark;

    CarPlacement(CarQueue line, CarPark carPark){
        this.line = line;
        this.carPark = carPark;
    }

    @Override
    public void run() {
        while(true){
            line.addCar(carPark.removeCar());
        }
    }
}
