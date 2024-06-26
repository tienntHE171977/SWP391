package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private Item getItemById(int id) {
        for (Item i : items) {
            if (i.getProduct().getProduct_id()== id) {  // Sử dụng getProductId()
                return i;
            }
        }
        return null;
    }

    public int getQuantityById(int id) {
        Item item = getItemById(id);
        return item != null ? item.getQuantity() : 0;
    }
    
    // Thêm vào giỏ hàng
    public void addItem(Item t) {
        Item existingItem = getItemById(t.getProduct().getProduct_id());
        // Có trong giỏ rồi thì add thêm số lượng
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + t.getQuantity());
        } else {
            // Không có trong giỏ
            items.add(t);
        }
    }

    public void removeItem(int id) {
        Item item = getItemById(id);
        if (item != null) {
            items.remove(item);
        }
    }
    
    public double getTotalMoney() {
        double total = 0;
        for (Item i : items) {
            total += i.getQuantity() * i.getProduct().getOriginal_prices();  // Sử dụng getOriginalPrices()
        }
        return total;
    }
}
