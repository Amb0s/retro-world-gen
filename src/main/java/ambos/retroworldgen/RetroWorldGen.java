package ambos.retroworldgen;

import ambos.retroworldgen.world.type.WorldTypeOverworldAmplifiedIndev;
import ambos.retroworldgen.world.type.WorldTypeOverworldInfdev;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetroWorldGen implements ModInitializer {
    public static final String MOD_ID = "retroworldgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Retro World Gen initialized");
        WorldTypes.register(MOD_ID + ":"  + "overworld.infdev", 
            new WorldTypeOverworldInfdev(MOD_ID + "."  +"overworld.infdev"));
        WorldTypes.register(MOD_ID + ":"  + "overworld.amplified.indev", 
            new WorldTypeOverworldAmplifiedIndev(MOD_ID + "."  + "overworld.amplified.indev"));
    }
}
