package model;

public class SoldCars {
    private static SoldCars soldCars;
    private Long countSoldCars;
    private Long summaryPrice;

    private SoldCars(Long countSoldCars, Long summaryPrice) {
        this.countSoldCars = countSoldCars;
        this.summaryPrice = summaryPrice;
    }

    public static SoldCars getInstance() {
        if (soldCars == null) {
            soldCars = new SoldCars(0L, 0L);
        }
        return soldCars;
    }

    public Long getCountSoldCars() {
        return countSoldCars;
    }

    public void setCountSoldCars(Long countSoldCars) {
        this.countSoldCars = countSoldCars;
    }

    public Long getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(Long summaryPrice) {
        this.summaryPrice = summaryPrice;
    }
}
