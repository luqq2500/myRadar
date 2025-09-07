package adversity;

public enum SubAdversity {
    ROAD_HAZARD(Adversity.ROAD),
    ELECTRICITY_SHORT(Adversity.UTILITY),
    FINANCIAL_AID(Adversity.SOCIAL),
    FLOOD(Adversity.WEATHER),
    TRAIN_DELAY(Adversity.TRANSPORTATION);

    private final Adversity adversity;
    SubAdversity(Adversity adversity) {
        this.adversity = adversity;
    }
    public Adversity getMainAdversity() {
        return adversity;
    }
}
