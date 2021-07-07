import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private static volatile Semaphore   semForks;
    private static int  deathTime;
    private static int  sleepTime;
    private static int  eatTime;
    private static int  mealCount;
    private int         eatMoments;
    private long        startTime;
    private long        lastTimeMeal;

    public Philosopher(String name) {
        super(name);
    }

    public static void  initTimeAndSem(String[] args) {
        if (args == null)
            throw new IllegalArgumentException();
        if (args.length != 4 && args.length != 5)
            throw new IllegalArgumentException();
        int philoCount = Integer.parseInt(args[0]);
        deathTime = Integer.parseInt(args[1]);
        eatTime = Integer.parseInt(args[2]);
        sleepTime = Integer.parseInt(args[3]);
        if (args.length == 5)
            mealCount = Integer.parseInt(args[4]);
        else
            mealCount = -1;
        if (philoCount < 0 || deathTime < 0 || eatTime < 0 || sleepTime < 0 ||
                (args.length == 5 && mealCount < 0))
            throw new IllegalArgumentException();
        semForks = new Semaphore(philoCount);
    }

    public long getLastTimeMeal() {
        return lastTimeMeal;
    }

    public static int getDeathTime() {
        return deathTime;
    }

    public  void setStartTime(long startTime) {
        this.startTime = startTime;
        lastTimeMeal = startTime;
    }

    public long getTime() {
        return (System.currentTimeMillis() - startTime) / 10 * 10;
    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private void takeForks() throws InterruptedException {
        printf("%dms %s is thinking\n", getTime(), getName());
        semForks.acquire(2);
        printf("%dms %s has taken a fork\n", getTime(), getName());
        printf("%dms %s has taken a fork\n", getTime(), getName());
    }

    private void eat() throws InterruptedException {
        eatMoments++;
        lastTimeMeal = System.currentTimeMillis();
        printf("%dms %s is eating\n", getTime(), getName());
        Thread.sleep(eatTime);
    }

    private void putForksAndSleep() throws InterruptedException{
        semForks.release(2);
        if (eatMoments == mealCount) {
            this.interrupt();
            return ;
        }
        printf("%dms %s is sleeping\n", getTime(), getName());
        Thread.sleep(sleepTime);

    }

    public void run() {
        try {
            while (!isInterrupted()) {
                takeForks();
                eat();
                putForksAndSleep();
            }
        }
        catch (InterruptedException e) {

        }
    }
}
