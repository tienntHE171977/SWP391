/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author FPT
 */
public class Product {
    private int productId;
    private String productName;
    private int originalPrice;
    private boolean sale;
    private int salePrice;
    private String productHighlights;
    private String productDescription;
    private int trademarkId;
    private boolean status;
    private int quantity;
    private int guarantee;
    private int categoryId;
    private Date updateDate;
    private int sole; 
    private int avrRatedStar;

    public Product() {
    }

    public Product(int productId, String productName, int originalPrice, boolean sale, int salePrice, String productHighlights, String productDescription, int trademarkId, boolean status, int quantity, int guarantee, int categoryId, Date updateDate, int sole, int avrRatedStar) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.sale = sale;
        this.salePrice = salePrice;
        this.productHighlights = productHighlights;
        this.productDescription = productDescription;
        this.trademarkId = trademarkId;
        this.status = status;
        this.quantity = quantity;
        this.guarantee = guarantee;
        this.categoryId = categoryId;
        this.updateDate = updateDate;
        this.sole = sole;
        this.avrRatedStar = avrRatedStar;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductHighlights() {
        return productHighlights;
    }

    public void setProductHighlights(String productHighlights) {
        this.productHighlights = productHighlights;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(int trademarkId) {
        this.trademarkId = trademarkId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getSole() {
        return sole;
    }

    public void setSole(int sole) {
        this.sole = sole;
    }

    public int getAvrRatedStar() {
        return avrRatedStar;
    }

    public void setAvrRatedStar(int avrRatedStar) {
        this.avrRatedStar = avrRatedStar;
    }
    
    
}
