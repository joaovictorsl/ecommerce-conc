package src;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import src.model.Client;
import src.model.Item;
import src.model.Order;
import src.service.AutoRestock;
import src.service.SalesReport;
import src.service.Server;
import src.service.Worker;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Simulação de E-commerce Iniciada!");

        Server server1 = new Server();

        ExecutorService workerPool = Executors.newFixedThreadPool(6);

        Item item1 = new Item("Caderno", 15.0);
        Item item2 = new Item("Celular", 800.0);
        Item item3 = new Item("Tablet", 500.0);
        Item item4 = new Item("Headphones", 200.0);

        server1.addItemToStock(item1,25);
        server1.addItemToStock(item2, 25);
        server1.addItemToStock(item3, 25);
        server1.addItemToStock(item4, 25);

        List<Item> availableItems = Arrays.asList(item1, item2, item3, item4);

        SalesReport salesReport = new SalesReport(server1);

        ScheduledExecutorService restockScheduler = Executors.newScheduledThreadPool(1);
        AutoRestock autoRestock = new AutoRestock(server1);
        restockScheduler.scheduleAtFixedRate(autoRestock, 0, 10, TimeUnit.SECONDS);

        ScheduledExecutorService reportScheduler = Executors.newScheduledThreadPool(1);
        reportScheduler.scheduleAtFixedRate(salesReport, 0, 30, TimeUnit.SECONDS);

        ScheduledExecutorService clientScheduler = Executors.newScheduledThreadPool(8);
        for (int i = 0; i < 4; i++) {
            clientScheduler.scheduleAtFixedRate(new Client(server1, availableItems), 0, 5, TimeUnit.SECONDS);
        }

        Order emptyOrder = new Order(); 
        server1.addOrderToQueue(emptyOrder);
        
        Item nonExistentItem = new Item("Garrafa", 5.0);  
        Order orderWithNonExistentItem = new Order();
        orderWithNonExistentItem.addItem(nonExistentItem, 5);
        server1.addOrderToQueue(orderWithNonExistentItem);

        for (int i = 0; i < 6; i++) {
            workerPool.execute(new Worker(server1, salesReport));
        }
    }
}
