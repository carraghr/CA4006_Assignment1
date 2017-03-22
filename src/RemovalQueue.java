import java.sql.Time;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RemovalQueue{

    final Lock lock = new ReentrantLock();
    final Condition isUpdated = lock.newCondition();

    PriorityQueue<Car> queue = new PriorityQueue<>();

    public void addCar(Car car){

        if(lock.tryLock()){
            queue.add(car);
            if(queue.peek().equals(car)) {
                isUpdated.signal();
            }
            lock.unlock();
        }else{}
    }

    public Car removeCar(){
        lock.lock();
            try {
                //System.out.println("Hello!!!!!!!!!!!!!!!!!!!!");
                //need to loop through wait time
                /**
                 * Need to see first value of the queue get leave time and compare to DateTime
                 * if its the same remove car from queue
                 * if its not and the time for the car to leave is greater subtract the two and do a wait time
                 */
                while(queue.size() == 0) {
                    //System.out.println("Should be on screen!!!!!!!!!!!!!!!!!!!!");
                    isUpdated.await();
                    //System.out.println("Shoulf be on screen!!!!!!!!!!!!!!!!!!!!");
                }
                Date date = new Date();
                long currentTIme = date.getTime();
                long timeDifference = queue.peek().leavingTime - currentTIme;
                while(timeDifference > 0) {
                    isUpdated.await(timeDifference, TimeUnit.MILLISECONDS);//TODO timedifference timeout
                    date = new Date();
                    currentTIme = date.getTime();
                    timeDifference = queue.peek().leavingTime - currentTIme;
                }
                Car temp = queue.poll();
                lock.unlock();
                return temp;

            }catch(Exception e){}
        //}
        return null;
    }
}
