package blue.nightmarish.create_confectionery.entity;

import net.minecraft.world.entity.LivingEntity;

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
    boolean shouldPrank(LivingEntity entity);
    default boolean shouldPrank(Prank prankType) {
        return this.getPrankType() == prankType;
    }
    default boolean shouldPrank(LivingEntity entity, Prank type) {
        return this.shouldPrank(type) && this.shouldPrank(entity);
    }

    void resetPrankDuration();


    void resetPrankType();
    Prank getPrankType();
}