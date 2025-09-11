package event;

public enum SubAdversity {
    ROAD_HAZARD(MainAdversity.ROAD),
    ELECTRICITY_SHORT(MainAdversity.UTILITY),
    FINANCIAL_AID(MainAdversity.SOCIAL),
    FLOOD(MainAdversity.WEATHER),
    TRAIN_DELAY(MainAdversity.TRANSPORTATION);

    private final MainAdversity mainAdversity;
    SubAdversity(MainAdversity mainAdversity) {
        this.mainAdversity = mainAdversity;
    }
    public MainAdversity getMainAdversity() {
        return mainAdversity;
    }
}
