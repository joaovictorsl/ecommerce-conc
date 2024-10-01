public interface ServerInterface {

    public void addItemToStorage(String item, Integer quantity);

    public void removeItemFromStorage(String item, Integer quantity);

    public boolean verifyItemStorage(String item);

}
