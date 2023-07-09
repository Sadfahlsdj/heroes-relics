package hrelics.item.custom;

public interface LivingEntityInterface {

    int nagaWaitTicks = 0;
    boolean isHitByNaga = false;
    public default void setBoostedFireTicks(int i){

    }
    public default void setBoostedWitherTicks(int i){

    }

    public default void setNagaWaitTicks(int i){

    }
    public default int getNagaWaitTicks(){
        return nagaWaitTicks;
    }

    public default void setHitByNaga(boolean b){

    }

    public default boolean getHitByNaga(){ //filler comment to let me push
        return isHitByNaga;
    }
}
