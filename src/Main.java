public class Main {
    public static void main(String[] args) {
        try {
            Philosopher.initTimeAndSem(args);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Wrong arguments\n");
            return ;
        }
        Waiter waiter = new Waiter(Integer.parseInt(args[0]));
        waiter.start();
    }
}
