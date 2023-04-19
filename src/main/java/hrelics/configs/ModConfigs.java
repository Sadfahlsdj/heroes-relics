package hrelics.configs;
import com.mojang.datafixers.util.Pair;
import hrelics.HeroesRelics;


public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static Boolean VOICE_LINES_ON;
    public static int SOME_INT;
    public static double SOME_DOUBLE;
    public static int MAX_DAMAGE_DOWSING_ROD;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(HeroesRelics.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("key.test.value1", false), "Boolean");
        configs.addKeyValuePair(new Pair<>("key.test.value2", 50), "int");
        configs.addKeyValuePair(new Pair<>("key.test.value3", 4142.5), "double");
        configs.addKeyValuePair(new Pair<>("dowsing.rod.max.damage", 32), "int");
    }

    private static void assignConfigs() {
        VOICE_LINES_ON = CONFIG.getOrDefault("key.test.value1", true);
        SOME_INT = CONFIG.getOrDefault("key.test.value2", 42);
        SOME_DOUBLE = CONFIG.getOrDefault("key.test.value3", 42.0d);
        MAX_DAMAGE_DOWSING_ROD = CONFIG.getOrDefault("dowsing.rod.max.damage", 32);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}