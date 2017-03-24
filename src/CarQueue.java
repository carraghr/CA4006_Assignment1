import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue {

    private LinkedList<Car> line = new LinkedList<>();

    private Semaphore isClosing = new Semaphore(1);
    private Semaphore containsElements = new Semaphore(1);

    final Lock queueLock = new ReentrantLock();
    final Condition notEmprty = queueLock.newCondition();
    final Condition isDone = queueLock.newCondition();

    private String name;

    CarQueue(String name){
        this.name = name;
    }

    public void addCar(Car car){
        queueLock.lock();
        line.add(car);
        notEmprty.signal();
        queueLock.unlock();
    }

    public Car removeCar(){
        queueLock.lock();
        while(line.size() == 0){
            System.out.println(name + " Im removing");
            try {
                notEmprty.await();
            }catch(InterruptedException e){}
        }
        Car temp = line.removeFirst();
        queueLock.unlock();
        return temp;
    }

    public int size(){
        queueLock.lock();
        int size = line.size();
        queueLock.unlock();
        return size;
    }

    public void close(){

            queueLock.lock();
            if(line.size() == 0){
                System.out.println(name + " Im Shutting this down");
                line = null;
                notEmprty.signal();
            }
            queueLock.unlock();
    }
}
