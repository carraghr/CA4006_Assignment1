public class Entrance implements Runnable{

    public CarPark carPark;
    public CarQueue line = new CarQueue();

    public Entrance(CarPark carPark){
        this.carPark = carPark;
    }

    @Override
    public void run(){

        //create two threads.
        //System.out.println("Starting instance of Entrance");
        Thread carGenerator = new Thread(new CarGenerator(this.line));
        Thread carEntry = new Thread(new Entry(this.line,this.carPark));

        carGenerator.start();
        carEntry.start();

        while(true){}
    }
}
