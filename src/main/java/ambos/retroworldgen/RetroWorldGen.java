package ambos.retroworldgen;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetroWorldGen implements ModInitializer {
    public static final String MOD_ID = "retroworldgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String name(String name) {
        return RetroWorldGen.MOD_ID + "." + name;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("RetroWorldGen initialized");
    }
}
