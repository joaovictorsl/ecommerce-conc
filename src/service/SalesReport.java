package src.service;

import src.model.Order;

public class SalesReport implements Runnable {
    private int totalOrdersProcessed = 0;
    private int totalOrdersRejected = 0;
    private double totalSales = 0.0;
    private Server server;

    public SalesReport(Server server) {
        this.server = server;
    }

    public synchronized void recordSuccess(Order order) {
        totalOrdersProcessed++;
        totalSales += order.getTotalValue();
    }

    public synchronized void recordRejection(Order order) {
        totalOrdersRejected++;
    }

    @Override
    public void run() {
        System.out.println("Relatório de Vendas:");
        System.out.println("Total de pedidos processados: " + totalOrdersProcessed);
        System.out.println("Total de vendas: $" + totalSales);
        System.out.println("Total de pedidos rejeitados: " + totalOrdersRejected);
        int pendingOrdersCount = server.getPendingOrdersCount();
        System.out.println("Total de pedidos pendentes: " + pendingOrdersCount);
        System.out.println("------------------------------");
    }
}