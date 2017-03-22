import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue {

    private LinkedList<Car> line = new LinkedList<>();

    final Lock lock = new ReentrantLock();
    final Condition notEmprty = lock.newCondition();

    public void addCar(Car car){
        lock.lock();
        line.add(car);
        notEmprty.signal();
        lock.unlock();
    }

    public Car removeCar(){
        lock.lock();
        while(line.size() == 0){
            try {
                notEmprty.await();
            }catch(InterruptedException e){}
        }
        Car temp = line.removeFirst();
        lock.unlock();
        return temp;
    }
}
