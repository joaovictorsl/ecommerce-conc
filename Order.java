import java.util.HashMap;
import java.util.UUID;

public class Order {
    private UUID id;
    private HashMap<String, Integer> itens;

    public Request(UUID id) {
        this.id = id;
        itens = new HashMap<String, Integer>();
    }

    public void addItem(String item, Integer quantity) {
        itens.put(item, quantity);
    }

    public UUID getId() {
        return id;
    }

}
