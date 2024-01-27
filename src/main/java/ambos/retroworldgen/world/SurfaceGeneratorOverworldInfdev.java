package ambos.retroworldgen.world;

import ambos.retroworldgen.noise.unused.InfdevPerlinNoise;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;
import net.minecraft.core.world.noise.RetroPerlinNoise;

public class SurfaceGeneratorOverworldInfdev extends SurfaceGeneratorOverworld {
    public SurfaceGeneratorOverworldInfdev(World world) {
        super(world, new InfdevPerlinNoise(world.getRandomSeed(), 4, 40), 
            new InfdevPerlinNoise(world.getRandomSeed(), 4, 44),
            new InfdevPerlinNoise(world.getRandomSeed(), 8, 32), 
            false
        );
    }
}
