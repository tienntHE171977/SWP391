/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Order {
    private int order_id;
    private String orderDate;
    private int total_cost;
    private String fullName;
    private String phone;
    private String address;
    private int status_order;
    private int userId;
    private int saler_id;
    private String note;

    public Order() {
    }

    public Order(int order_id, String orderDate, int total_cost, String fullName, String phone, String address, int status_order, int userId, int saler_id, String note) {
        this.order_id = order_id;
        this.orderDate = orderDate;
        this.total_cost = total_cost;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.status_order = status_order;
        this.userId = userId;
        this.saler_id = saler_id;
        this.note = note;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus_order() {
        return status_order;
    }

    public void setStatus_order(int status_order) {
        this.status_order = status_order;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSaler_id() {
        return saler_id;
    }

    public void setSaler_id(int saler_id) {
        this.saler_id = saler_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    

}
