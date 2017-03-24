public class Exit implements Runnable{

    public CarPark carPark;
    private CarQueue line;

    public Exit(CarPark carPark){
        this.carPark = carPark;
        line = new CarQueue("Exit");
    }

    @Override
    public void run() {

        //Thread to place car been removed into exit queue
        Thread carPlacer = new Thread(new CarPlacement(this.line, this.carPark));
        //Thread to remove car completely from car park building
        Thread carExit = new Thread(new Exiter(this.line,this.carPark));

        carPlacer.start();
        carExit.start();

        try {
            carPlacer.join();
            carExit.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
