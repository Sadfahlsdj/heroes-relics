package hrelics.item.custom;

public interface LivingEntityInterface {

    int nagaWaitTicks = 0;

    int tyrfingTicks = 0;

    int tyrfingDamageTicks = 0;


    boolean isHitByNaga = false;

    public boolean nagaActivation = false;
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

    public default void setNagaActivation(boolean b){

    }
    public default boolean getNagaActivation(){
        return nagaActivation;
    }

    public default boolean getHitByNaga(){ //filler comment to let me push
        return isHitByNaga;
    }

    public default void setTyrfingTicks(int i){

    }

    public default void setTyrfingDamageTicks(int i){

    }
    public default int getTyrfingDamageTicks(){
        return tyrfingDamageTicks;
    }




}
