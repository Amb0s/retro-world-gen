package ambos.retroworldgen.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.overworld.TerrainGeneratorOverworld;

public class TerrainGeneratorOverworldInfdev extends TerrainGeneratorOverworld {
    public TerrainGeneratorOverworldInfdev(World world) {
            super(world, new DensityGeneratorOverworldInfdev(world));
    }
}
