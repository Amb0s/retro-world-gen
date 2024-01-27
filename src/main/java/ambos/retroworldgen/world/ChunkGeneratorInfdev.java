package ambos.retroworldgen.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.MapGenCaves;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import net.minecraft.core.world.generate.chunk.perlin.overworld.retro.ChunkDecoratorOverworldRetro;

public class ChunkGeneratorInfdev extends ChunkGeneratorPerlin {
    public ChunkGeneratorInfdev(World world) {
        super(world, new ChunkDecoratorOverworldRetro(world),
                new TerrainGeneratorOverworldInfdev(world),
                new SurfaceGeneratorOverworldInfdev(world),
                new MapGenCaves(true)
        );
    }
}
