package hrelics.item.custom;

public interface PlayerEntityInterface {
    int atrocityHits = 0;

    public default void setAtrocityHits(int a){

    }

    public default int getAtrocityHits(){
        return atrocityHits;
    }

    public default void decrementAtrocityHits(){

    }
}
