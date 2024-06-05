package blue.nightmarish.create_confectionery.entity;

public interface Prankster {
    enum Prank {
        THROW_EGG,
        EAT_CAKE,
        CLIMB_HEAD;

        static final Prank[] values = values();
        public static Prank get(int i) {
            return values[i];
        }
        public static int count = values.length;
    }

    boolean shouldPrank();
    default boolean shouldPrank(Prank prankType) {
        return this.getPrankType() == prankType && this.shouldPrank();
    }
    default boolean shouldPrank(int prankType) {
        return this.getPrankType().ordinal() == prankType && this.shouldPrank();
    }

    void resetPrankDuration();


    void resetPrankType();
    Prank getPrankType();
}