import java.util.ArrayList;

public class Waiter extends Thread {
    private ArrayList<Philosopher>  list = new ArrayList<>();

    public  Waiter(int count) {
        for (int i = 0; i < count; ++i) {
            list.add(new Philosopher(Integer.toString(i + 1)));
        }
    }

    public long getDiffTime(Philosopher philosopher) {
        return System.currentTimeMillis() - philosopher.getLastTimeMeal();
    }

    public long getTimeOfPhiloDeath(Philosopher philosopher) {
        return System.currentTimeMillis() - philosopher.getLastTimeMeal();
    }

    @Override
    public void run() {
        list.forEach(x -> x.setStartTime(System.currentTimeMillis()));
        list.forEach(Philosopher::start);
        while (true) {
            int i = 0;
            for (Philosopher philosopher : list) {
                if (philosopher.isInterrupted())
                    ++i;
                if (!philosopher.isInterrupted() && getDiffTime(philosopher) >= Philosopher.getDeathTime()) {
                    list.forEach(Thread::interrupt);
                    System.out.printf("%dms %s died\n", getTimeOfPhiloDeath(philosopher),
                                                        philosopher.getName());
                    return ;
                }
            }
            if (i == list.size())
                return ;
        }
    }
}
