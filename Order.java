import java.util.HashMap;
import java.util.UUID;

public class Order {
    private UUID id;
    private HashMap<String, Integer> itens;
    private OrderStatus status;

    public Order(UUID id) {
        this.id = id;
        itens = new HashMap<String, Integer>();
        this.status = OrderStatus.PENDING;
    }

    public void addItem(String item, Integer quantity) {
        itens.put(item, quantity);
    }

    public UUID getId() {
        return id;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getItem(String item) {
        return itens.remove(item);
    }

}
