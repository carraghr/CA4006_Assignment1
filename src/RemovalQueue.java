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

                while(queue.size() == 0){//if the queue is empty wait for elements to be pushed
                    isUpdated.await();
                }

                //queue had elements so get current time
                Date date = new Date();
                long currentTIme = date.getTime();

                //get the time difference between current time and the leave time of the car
                long timeDifference = queue.peek().leavingTime - currentTIme;
                //if there is still time to wait put thread to sleep for that duration
                while(timeDifference > 0) {
                    isUpdated.await(timeDifference, TimeUnit.MILLISECONDS);
                    date = new Date();
                    //update to current time
                    currentTIme = date.getTime();
                    //re-calculate the time difference as the the top element has changed so adjust sleep time.
                    timeDifference = queue.peek().leavingTime - currentTIme;
                }
                //remove top element
                Car temp = queue.poll();
                //release lock
                lock.unlock();
                //return car to be fully removed by exit
                return temp;

            }catch(Exception e){}
        return null;
    }
}
