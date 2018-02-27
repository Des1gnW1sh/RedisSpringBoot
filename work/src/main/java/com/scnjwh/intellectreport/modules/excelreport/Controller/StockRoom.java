package com.scnjwh.intellectreport.modules.excelreport.Controller;

/**
 * Created by Administrator on 2017-10-31.
 */
public class StockRoom {

    private String stockName;

    public StockRoom(String stockName) {
        this.stockName = stockName;
    }
    public StockRoom() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
