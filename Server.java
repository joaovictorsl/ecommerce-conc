import java.util.concurrent.ArrayBlockingQueue;

public class Server implements ServerInterface {
    private final int MAX_ORDERS;
    private ArrayBlockingQueue<Order> orders;

    public Server() {
        this.MAX_ORDERS = 10;
        this.orders = new ArrayBlockingQueue<Order>(MAX_ORDERS);
    }

    @Override
    public void addItemToStorage(String item, Integer quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addItemToStorage'");
    }

    @Override
    public void removeItemFromStorage(String item, Integer quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeItemFromStorage'");
    }

    @Override
    public boolean verifyItemStorage(String item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyItemStorage'");
    }

}