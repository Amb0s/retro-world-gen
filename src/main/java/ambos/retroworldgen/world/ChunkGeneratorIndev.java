package ambos.retroworldgen.world;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.MapGenCaves;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.ChunkGeneratorResult;
import net.minecraft.core.world.generate.chunk.perlin.SurfaceGenerator;
import net.minecraft.core.world.generate.chunk.perlin.overworld.retro.ChunkDecoratorOverworldRetro;
import net.minecraft.core.world.generate.chunk.perlin.overworld.retro.SurfaceGeneratorOverworldRetro;
import net.minecraft.core.world.noise.CombinedPerlinNoise;
import net.minecraft.core.world.noise.PerlinNoise;

public class ChunkGeneratorIndev extends ChunkGenerator {
    private final CombinedPerlinNoise combinedA;
    private final CombinedPerlinNoise combinedB;
    private final CombinedPerlinNoise combinedC;
    private final CombinedPerlinNoise combinedD;
    private final PerlinNoise octavesA;
    private final PerlinNoise octavesB;
    private final SurfaceGenerator sg;
    private final MapGenCaves cg;

    public ChunkGeneratorIndev(World world) {
        super(world, new ChunkDecoratorOverworldRetro(world));
        long seed = world.getRandomSeed();
        this.combinedA = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 0), new PerlinNoise(seed, 8, 8));
        this.combinedB = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 16), new PerlinNoise(seed, 8, 24));
        this.combinedC = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 32), new PerlinNoise(seed, 8, 40));
        this.combinedD = new CombinedPerlinNoise(new PerlinNoise(seed, 8, 48), new PerlinNoise(seed, 8, 56));
        this.octavesA = new PerlinNoise(seed, 6, 64);
        this.octavesB = new PerlinNoise(seed, 8, 70);
        this.sg = new SurfaceGeneratorOverworldRetro(world);
        this.cg = new MapGenCaves(true);
    }

    protected ChunkGeneratorResult doBlockGeneration(Chunk chunk) {
        ChunkGeneratorResult result = new ChunkGeneratorResult();
        short[] blocks = new short[256 * this.world.getHeightBlocks()];
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        float mod = 1.3F;
        int[] heightMap = new int[256];

        int x;
        for(x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
                double noiseA = this.combinedA.get((double)((float)(chunkX * 16 + x) * 1.3F), (double)((float)(chunkZ * 16 + z) * 1.3F)) / 6.0 + -4.0;
                double noiseB = this.combinedB.get((double)((float)(chunkX * 16 + x) * 1.3F), (double)((float)(chunkZ * 16 + z) * 1.3F)) / 5.0 + 10.0 + -4.0;
                if (this.octavesA.get((double)(chunkX * 16 + x), (double)(chunkZ * 16 + z)) / 8.0 > 0.0) {
                    noiseB = noiseA;
                }

                double height;
                if ((height = Math.max(noiseA, noiseB) /* / 2.0*/) < 0.0) {
                    //height *= 0.8;
                }

                heightMap[x + z * 16] = (int)height;
            }
        }

        int[] newHeightMap = heightMap;

        int val2;
        int z;
        int y;
        for(x = 0; x < 16; ++x) {
            for(z = 0; z < 16; ++z) {
                double val = this.combinedC.get((double)(chunkX * 16 + x << 1), (double)(chunkZ * 16 + z << 1)) / 8.0;
                val2 = this.combinedD.get((double)(chunkX * 16 + x << 1), (double)(chunkZ * 16 + z << 1)) > 0.0 ? 1 : 0;
                if (val > 2.0) {
                    y = ((newHeightMap[x + z * 16] - val2) / 2 << 1) + val2;
                    newHeightMap[x + z * 16] = y;
                }
            }
        }

        for(x = 0; x < 16; ++x) {
            for(z = 0; z < 16; ++z) {
                int val = (int)(this.octavesB.get((double)(chunkX * 16 + x), (double)(chunkZ * 16 + z)) / 24.0) - 4;
                int newHeight;
                val2 = (newHeight = newHeightMap[x + z * 16] + this.world.getWorldType().getOceanY()) + val;
                newHeightMap[x + z * 16] = Math.max(newHeight, val2);
                if (newHeightMap[x + z * 16] > this.world.getWorldType().getMaxY() - 2) {
                    newHeightMap[x + z * 16] = this.world.getWorldType().getMaxY() - 2;
                }

                if (newHeightMap[x + z * 16] < 1) {
                    newHeightMap[x + z * 16] = 1;
                }

                for(y = this.world.getWorldType().getMinY(); y < this.world.getWorldType().getMaxY(); ++y) {
                    int index = x << this.world.getHeightBlocks() + 4 | z << this.world.getHeightBlocks() | y;
                    int blockID = 0;
                    if (y < newHeight) {
                        blockID = this.world.getWorldType().getFillerBlock();
                    } else if (y < this.world.getWorldType().getOceanY()) {
                        blockID = this.world.getWorldType().getOceanBlock();
                    }

                    if (y == 0) {
                        blockID = Block.bedrock.id;
                    }

                    result.setBlock(x, y, z, blockID);
                }
            }
        }

        this.sg.generateSurface(chunk, result);
        this.cg.generate(this.world, chunkX, chunkZ, result);
        return result;
    }
}