package teste.transaction.com.testtransaction;

/**
 * Created by mainente on 29/06/16.
 */
public class Transaction {


    private static Transaction instance = null;
    private String card_holder;
    private String card_number;
    private String card_brand;
    private int expiration_month;
    private int expiration_year;
    private Double value;
    private String CVV;
    private String status;

    public static Transaction getInstance() {
        if (null == instance) {
            instance = new Transaction();
        }
        return instance;
    }

    public static void setInstance(Transaction instance) {
        Transaction.instance = instance;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_brand() {
        return card_brand;
    }

    public void setCard_brand(String card_brand) {
        this.card_brand = card_brand;
    }

    public int getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(int expiration_month) {
        this.expiration_month = expiration_month;
    }

    public int getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(int expiration_year) {
        this.expiration_year = expiration_year;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
