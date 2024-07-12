/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author FPT
 */
public class Trademark {
    private int trademarkId;
    private String trademarkName;
    private int trademarkStatus;

    public Trademark() {
    }

    public Trademark(int trademarkId, String trademarkName, int trademarkStatus) {
        this.trademarkId = trademarkId;
        this.trademarkName = trademarkName;
        this.trademarkStatus = trademarkStatus;
    }

    public int getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(int trademarkId) {
        this.trademarkId = trademarkId;
    }

    public String getTrademarkName() {
        return trademarkName;
    }

    public void setTrademarkName(String trademarkName) {
        this.trademarkName = trademarkName;
    }

    public int getTrademarkStatus() {
        return trademarkStatus;
    }

    public void setTrademarkStatus(int trademarkStatus) {
        this.trademarkStatus = trademarkStatus;
    }
    
}