package htw.entity;

import java.math.BigDecimal;

public class CurrencyServiceResponse {

    private String from;
    private String to;
    private BigDecimal totalCalculatedAmount;

    public CurrencyServiceResponse(String from, String to, BigDecimal totalCalculatedAmount) {
        this.from = from;
        this.to = to;
        this.totalCalculatedAmount = totalCalculatedAmount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getTotalCalculatedAmount() {
        return totalCalculatedAmount;
    }
}
