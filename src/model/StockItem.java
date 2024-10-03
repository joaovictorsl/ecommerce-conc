package src.model;

public class StockItem {
    private Item item;
    private int quantity;
    private int popularity;

    public StockItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.popularity = 0;
    }

    public void incrementPopularity() {
        popularity++;
    }

    public int getPopularity() {
        return popularity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}