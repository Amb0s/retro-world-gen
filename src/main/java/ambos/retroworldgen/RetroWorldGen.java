package ambos.retroworldgen;

import ambos.retroworldgen.world.type.WorldTypeOverworldIndev;
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
        LOGGER.info("RetroWorldGen initialized");
        //WorldTypes.register(MOD_ID + ":"  + "overworld.infdev", new WorldTypeOverworldInfdev("worldType.overworld.infdev"));
        WorldTypes.register(MOD_ID + ":"  + "overworld.indev", new WorldTypeOverworldIndev("worldType.overworld.indev"));
    }
}
