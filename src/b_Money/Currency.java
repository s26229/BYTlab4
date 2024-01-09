package b_Money;

public class Currency {
    private String name;
    private Double rate;

    Currency(String name, Double rate) {
        this.name = name;
        this.rate = rate;
    }

    public Integer universalValue(Integer amount) {
        return (int) Math.round(amount * rate);
    }

    public String getName() {
        return name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer valueInThisCurrency(Integer amount, Currency otherCurrency) {
        return (int) Math.round(amount / this.rate * otherCurrency.getRate());
    }

}
