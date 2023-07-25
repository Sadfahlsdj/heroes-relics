package hrelics.item.custom;

public interface ThrownItemEntityInterface {
    int valflameUseTime = 0;

    public default void setValflameUseTime(int i){

    }

    public default int getValflameUseTime(){
        return valflameUseTime;
    }
}
