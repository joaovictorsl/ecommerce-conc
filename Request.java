import java.util.ArrayList;
import java.util.UUID;

public class Request {
    private UUID id;
    private ArrayList<String> itens;

    public Request(UUID id) {
        this.id = id;
        itens = new ArrayList<String>();
    }

    public void addItem(String item) {
        itens.add(item);
    }

    public UUID getId() {
        return id;
    }

}
