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

    int lightningHits = 0;

    public default void setLightningHits(int a){

    }

    public default int getLightningHits(){
        return lightningHits;
    }

    public default void decrementLightningHits(){

    }
    //burning quake
    int fireHits = 0;
    public default void setFireHits(int a){

    }
    public default int getFireHits(){
        return fireHits;
    }

    public default void decrementFireHits(){

    }

    //fallen star
    int fallenStarHits = 0;
    public default void setFallenStarHits(int a){

    }
    public default int getFallenStarHits(){
        return fallenStarHits;
    }

    public default void decrementFallenStarHits(){

    }

    //ruined sky
    int ruinedSkyHits = 0;
    public default void setRuinedSkyHits(int a){

    }
    public default int getRuinedSkyHits(){
        return ruinedSkyHits;
    }

    public default void decrementRuinedSkyHits(){

    }
    //beast fang

    int witherHits = 0;
    public default void setWitherHits(int a) {

    }

    public default int getWitherHits(){
        return witherHits;
    }

    public default void decrementWitherHits(){

    }

    //pavise & aegis
    int PaviseAegisTicks = 0;

    public default int getPaviseAegisTicks(){
        return PaviseAegisTicks;
    }

    public default void setPaviseAegisTicks(int a){

    }

}
