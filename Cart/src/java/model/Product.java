package model;

public class Product {
    private int product_id;  
    private String product_name;
    private int original_prices;
    private boolean sale;
    private int sale_prices;
    private String product_highlights;
    private String product_description;
    private int trademark_id;
    private boolean status;
    private int quantity;
    private int guarantee;
    private int category_id;
    private String update_date;
    private int sole;
    private int avr_rated_star;

    public Product() {
    }

    public Product(int product_id, String product_name, int original_prices, boolean sale, int sale_prices, String product_highlights, String product_description, int trademark_id, boolean status, int quantity, int guarantee, int category_id, String update_date, int sole, int avr_rated_star) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.original_prices = original_prices;
        this.sale = sale;
        this.sale_prices = sale_prices;
        this.product_highlights = product_highlights;
        this.product_description = product_description;
        this.trademark_id = trademark_id;
        this.status = status;
        this.quantity = quantity;
        this.guarantee = guarantee;
        this.category_id = category_id;
        this.update_date = update_date;
        this.sole = sole;
        this.avr_rated_star = avr_rated_star;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getOriginal_prices() {
        return original_prices;
    }

    public void setOriginal_prices(int original_prices) {
        this.original_prices = original_prices;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public int getSale_prices() {
        return sale_prices;
    }

    public void setSale_prices(int sale_prices) {
        this.sale_prices = sale_prices;
    }

    public String getProduct_highlights() {
        return product_highlights;
    }

    public void setProduct_highlights(String product_highlights) {
        this.product_highlights = product_highlights;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getTrademark_id() {
        return trademark_id;
    }

    public void setTrademark_id(int trademark_id) {
        this.trademark_id = trademark_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public int getSole() {
        return sole;
    }

    public void setSole(int sole) {
        this.sole = sole;
    }

    public int getAvr_rated_star() {
        return avr_rated_star;
    }

    public void setAvr_rated_star(int avr_rated_star) {
        this.avr_rated_star = avr_rated_star;
    }


    
}
    