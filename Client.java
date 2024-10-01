import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable {
    private BlockingQueue<Order> orderQueue;

    public Client(BlockingQueue<Order> q) {
        orderQueue = q;
    }

    @Override
    public void run() {
        try {
            orderQueue.put(newOrder());
        } catch (Exception e) {
        }
    }

    private Order newOrder() {
        Order o = new Order(UUID.randomUUID());
        o.addItem("Something", 10);
        return o;
    }
}
