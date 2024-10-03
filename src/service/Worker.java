package src.service;

import src.model.Item;
import src.model.Order;
import src.model.OrderStatus;

public class Worker implements Runnable {
    private Server server;
    private SalesReport report;

    public Worker(Server server, SalesReport report) {
        this.server = server;
        this.report = report;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order order = server.takeOrderFromQueue();

                if (order.getItens().isEmpty()) {
                    order.setStatus(OrderStatus.REJECTED);
                    report.recordRejection(order);
                    System.out.println("Pedido " + order.getId() + " rejeitado: lista de itens vazia.");
                    continue;
                }

                boolean canProcessOrder = true;
                boolean itemNotFound = false;

                for (Item item : order.getItens().keySet()) {
                    int requestedQuantity = order.getItens().get(item);

                    if (!server.getStock().containsKey(item.getId())) {
                        itemNotFound = true;
                        System.out.println("Pedido " + order.getId() + " rejeitado: item " + item.getName() + " não encontrado no estoque.");
                        break;
                    }

                    server.incrementItemPopularity(item.getId());

                    if (!server.hasEnoughStock(item.getId(), requestedQuantity)) {
                        canProcessOrder = false;  
                    }
                }

                if (itemNotFound) {
                    order.setStatus(OrderStatus.REJECTED);
                    report.recordRejection(order);
                } else if (canProcessOrder) {
                    for (Item item : order.getItens().keySet()) {
                        int requestedQuantity = order.getItens().get(item);
                        server.updateStock(item.getId(), -requestedQuantity);
                    }
                    order.setStatus(OrderStatus.DONE);
                    report.recordSuccess(order);
                    System.out.println("Pedido " + order.getId() + " processado com sucesso.");
                } else {
                    order.setStatus(OrderStatus.WAITING_FOR_STOCK);
                    server.addPendingOrder(order);
                    System.out.println("Pedido " + order.getId() + " movido para fila de espera.");
                    server.triggerAutoRestock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
