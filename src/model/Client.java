package src.model;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import src.service.Server;

public class Client implements Runnable {
    private Server server;
    private List<Item> availableItems;
    private Random random = new Random();
    private String clientId;

    public Client(Server server, List<Item> items) {
        this.server = server;
        this.availableItems = items;
        this.clientId = "Cliente-" + UUID.randomUUID();
    }

    @Override
    public void run() {
        try {
            Order order = newOrder();
            server.addOrderToQueue(order);
            System.out.println(clientId + " adicionou um novo pedido.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Order newOrder() {
        Order order = new Order();
        int numItemsToOrder = random.nextInt(availableItems.size()) + 1;

        for (int i = 0; i < numItemsToOrder; i++) {
            Item item = availableItems.get(random.nextInt(availableItems.size()));
            int quantity = random.nextInt(10) + 1;
            order.addItem(item, quantity);
        }

        return order;
    }
}
