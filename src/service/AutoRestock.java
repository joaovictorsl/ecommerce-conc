package src.service;

import src.model.Item;
import src.model.StockItem;

public class AutoRestock implements Runnable {
    private Server server;

    public AutoRestock(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        if (server.getPendingOrdersCount() > 0) {
            System.out.println("Reabastecimento iniciado. Pedidos pendentes: " + server.getPendingOrdersCount());

            System.out.println("Status do estoque antes do reabastecimento:");
            server.printStockStatus();

            String mostPopularItemId = getMostPopularItemId();

            for (String itemId : server.getStock().keySet()) {
                StockItem stockItem = server.getStock().get(itemId);
                int currentQuantity = stockItem.getQuantity();
                int restockAmount = 0;

                if (itemId.equals(mostPopularItemId)) {
                    if (currentQuantity < 50) {
                        restockAmount = 50 - currentQuantity;
                    }
                } else {
                    if (currentQuantity < 25) {
                        restockAmount = 25 - currentQuantity;
                    }
                }

                if (restockAmount > 0) {
                    server.updateStock(itemId, restockAmount);
                    Item item = stockItem.getItem();
                    System.out.println("Estoque reabastecido com " + restockAmount + " unidades de " + item.getName() + ".");
                }
            }

            System.out.println("Status do estoque após o reabastecimento:");
            server.printStockStatus();

            try {
                server.reprocessPendingOrders();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Nenhum pedido pendente. Reabastecimento não necessário.");
        }
    }

    private String getMostPopularItemId() {
        String mostPopularItemId = null;
        int maxPopularity = -1;

        for (String itemId : server.getStock().keySet()) {
            int popularity = server.getItemPopularity(itemId);
            if (popularity > maxPopularity) {
                maxPopularity = popularity;
                mostPopularItemId = itemId;
            }
        }

        return mostPopularItemId;
    }
}
