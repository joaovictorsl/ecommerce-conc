package src.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import src.model.Item;
import src.model.Order;
import src.model.StockItem;

public class Server {
    private ConcurrentHashMap<String, StockItem> stock = new ConcurrentHashMap<>();
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Order> pendingOrders = new LinkedBlockingQueue<>();
    private AutoRestock autoRestock;

    public Server() {
        this.autoRestock = new AutoRestock(this);
    }

    public void addItemToStock(Item item, int quantity) {
        stock.put(item.getId(), new StockItem(item, quantity));
    }

    public synchronized void printStockStatus() {
        StringBuilder stockOutput = new StringBuilder();

        stockOutput.append("\n==================== ESTOQUE E POPULARIDADE ====================\n");
        stockOutput.append(String.format("%-20s | %-10s | %-15s\n", "Nome do Item", "Quantidade", "Popularidade"));
        stockOutput.append("---------------------------------------------------------------\n");

        for (StockItem stockItem : stock.values()) {
            String itemName = stockItem.getItem().getName();
            int quantity = stockItem.getQuantity();
            int popularity = stockItem.getPopularity();
            stockOutput.append(String.format("%-20s | %-10d | %-15d\n", itemName, quantity, popularity));
        }

        stockOutput.append("================================================================\n");

        System.out.println(stockOutput.toString());
    }

    public void updateStock(String itemId, int quantityChange) {
        stock.computeIfPresent(itemId, (key, stockItem) -> {
            stockItem.setQuantity(stockItem.getQuantity() + quantityChange);
            return stockItem;
        });
    }

    public boolean hasEnoughStock(String itemId, int requiredQuantity) {
        StockItem stockItem = stock.get(itemId);
        return stockItem != null && stockItem.getQuantity() >= requiredQuantity;
    }

    public void incrementItemPopularity(String itemId) {
        StockItem stockItem = stock.get(itemId);
        if (stockItem != null) {
            stockItem.incrementPopularity();
        }
    }

    public int getItemPopularity(String itemId) {
        StockItem stockItem = stock.get(itemId);
        return stockItem != null ? stockItem.getPopularity() : 0;
    }

    public void addPendingOrder(Order order) throws InterruptedException {
        pendingOrders.put(order);
    }

    public void reprocessPendingOrders() throws InterruptedException {
        while (!pendingOrders.isEmpty()) {
            Order order = pendingOrders.take();
            addOrderToQueue(order);
        }
    }

    public void triggerAutoRestock() {
        autoRestock.run();
    }

    public int getPendingOrdersCount() {
        return pendingOrders.size();
    }

    public ConcurrentHashMap<String, StockItem> getStock() {
        return stock;
    }

    public void addOrderToQueue(Order order) throws InterruptedException {
        orderQueue.put(order);
    }

    public Order takeOrderFromQueue() throws InterruptedException {
        return orderQueue.take();
    }
}