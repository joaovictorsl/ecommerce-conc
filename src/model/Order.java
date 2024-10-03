package src.model;

import java.util.HashMap;
import java.util.UUID;

public class Order {
    private UUID id;
    private HashMap<Item, Integer> itens;
    private OrderStatus status;

    public Order() {
        this.id = UUID.randomUUID();
        this.itens = new HashMap<>();
        this.status = OrderStatus.PENDING;
    }

    public void addItem(Item item, Integer quantity) {
        itens.put(item, quantity);
    }

    public double getTotalValue() {
        double total = 0.0;
        for (Item item : itens.keySet()) {
            total += item.getPrice() * itens.get(item);
        }
        return total;
    }

    public UUID getId() {
        return id;
    }

    public HashMap<Item, Integer> getItens() {
        return this.itens;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
