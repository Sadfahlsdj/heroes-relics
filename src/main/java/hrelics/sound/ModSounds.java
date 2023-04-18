package hrelics.sound;

import hrelics.HeroesRelics;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    //declare the soundevent here, intialize in HeroesRelics, then edit sounds.json
    //sound needs to be in KeyInputHandler, sounds don't work in packets sadly

    public static SoundEvent ATROCITY1;
    public static SoundEvent ATROCITY2;
    public static SoundEvent ATROCITY3;
    public static SoundEvent ATROCITY4;
    public static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(HeroesRelics.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
