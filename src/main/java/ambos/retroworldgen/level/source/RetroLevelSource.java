package ambos.retroworldgen.level.source;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.chunk.Chunk;
import net.minecraft.level.gen.Cave;
import net.minecraft.level.gen.OverworldCave;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.structure.*;
import net.minecraft.structure.Ore;
import net.minecraft.tile.Sand;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.ProgressListener;

import java.util.Random;

public abstract class RetroLevelSource implements LevelSource {
    protected Random rand;
    protected Level level;
    protected double[] noises;
    protected double[] sandNoises = new double[256];
    protected double[] gravelNoises = new double[256];
    protected double[] surfaceDepthNoises = new double[256];
    protected Cave cave = new OverworldCave();
    protected Biome[] biomes;
    protected double[] interpolationNoises;
    protected double[] upperInterpolationNoises;
    protected double[] lowerInterpolationNoises;
    protected double[] biomeNoises;
    protected double[] depthNoises;
    protected double[] temperatureNoises;

    protected abstract void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures);

    protected abstract void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes);

    protected abstract double calculateTreeNoise(double d1, double d2);

    @Override
    public Chunk loadChunk(int x, int z) {
        return this.getChunk(x, z);
    }

    @Override
    public Chunk getChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        byte[] var3 = new byte[32768];
        Chunk var4 = new Chunk(this.level, var3, x, z);
        this.biomes = this.level.getBiomeSource().getBiomes(this.biomes, x * 16, z * 16, 16, 16);
        double[] var5 = this.level.getBiomeSource().temperatureNoises;
        this.shapeChunk(x, z, var3, this.biomes, var5);
        this.buildSurface(x, z, var3, this.biomes);
        this.cave.generate(this, this.level, x, z, var3);
        var4.generateHeightmap();
        return var4;
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
        Sand.fallInstantly = true;
        int var4 = chunkX * 16;
        int var5 = chunkZ * 16;
        Biome var6 = this.level.getBiomeSource().getBiome(var4 + 16, var5 + 16);
        this.rand.setSeed(this.level.getSeed());
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)chunkX * var7 + (long)chunkZ * var9 ^ this.level.getSeed());
        double var11 = 0.25D;
        if (this.rand.nextInt(4) == 0) {
            int var13 = var4 + this.rand.nextInt(16) + 8;
            int var14 = this.rand.nextInt(128);
            int var15 = var5 + this.rand.nextInt(16) + 8;
            (new Lake(Tile.STILL_WATER.id)).generate(this.level, this.rand, var13, var14, var15);
        }

        if (this.rand.nextInt(8) == 0) {
            int var26 = var4 + this.rand.nextInt(16) + 8;
            int var38 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int var50 = var5 + this.rand.nextInt(16) + 8;
            if (var38 < 64 || this.rand.nextInt(10) == 0) {
                (new Lake(Tile.STILL_LAVA.id)).generate(this.level, this.rand, var26, var38, var50);
            }
        }

        for(int var27 = 0; var27 < 8; ++var27) {
            int var39 = var4 + this.rand.nextInt(16) + 8;
            int var51 = this.rand.nextInt(128);
            int var16 = var5 + this.rand.nextInt(16) + 8;
            (new MobSpawnerRoom()).generate(this.level, this.rand, var39, var51, var16);
        }

        for(int var28 = 0; var28 < 10; ++var28) {
            int var40 = var4 + this.rand.nextInt(16);
            int var52 = this.rand.nextInt(128);
            int var63 = var5 + this.rand.nextInt(16);
            (new ClayDiskFeature(32)).generate(this.level, this.rand, var40, var52, var63);
        }

        for(int var29 = 0; var29 < 20; ++var29) {
            int var41 = var4 + this.rand.nextInt(16);
            int var53 = this.rand.nextInt(128);
            int var64 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.DIRT.id, 32)).generate(this.level, this.rand, var41, var53, var64);
        }

        for(int var30 = 0; var30 < 10; ++var30) {
            int var42 = var4 + this.rand.nextInt(16);
            int var54 = this.rand.nextInt(128);
            int var65 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.GRAVEL.id, 32)).generate(this.level, this.rand, var42, var54, var65);
        }

        for(int var31 = 0; var31 < 20; ++var31) {
            int var43 = var4 + this.rand.nextInt(16);
            int var55 = this.rand.nextInt(128);
            int var66 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.COAL_ORE.id, 16)).generate(this.level, this.rand, var43, var55, var66);
        }

        for(int var32 = 0; var32 < 20; ++var32) {
            int var44 = var4 + this.rand.nextInt(16);
            int var56 = this.rand.nextInt(64);
            int var67 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.IRON_ORE.id, 8)).generate(this.level, this.rand, var44, var56, var67);
        }

        for(int var33 = 0; var33 < 2; ++var33) {
            int var45 = var4 + this.rand.nextInt(16);
            int var57 = this.rand.nextInt(32);
            int var68 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.GOLD_ORE.id, 8)).generate(this.level, this.rand, var45, var57, var68);
        }

        for(int var34 = 0; var34 < 8; ++var34) {
            int var46 = var4 + this.rand.nextInt(16);
            int var58 = this.rand.nextInt(16);
            int var69 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.REDSTONE_ORE.id, 7)).generate(this.level, this.rand, var46, var58, var69);
        }

        for(int var35 = 0; var35 < 1; ++var35) {
            int var47 = var4 + this.rand.nextInt(16);
            int var59 = this.rand.nextInt(16);
            int var70 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.ORE_DIAMOND.id, 7)).generate(this.level, this.rand, var47, var59, var70);
        }

        for(int var36 = 0; var36 < 1; ++var36) {
            int var48 = var4 + this.rand.nextInt(16);
            int var60 = this.rand.nextInt(16) + this.rand.nextInt(16);
            int var71 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.LAPIS_LAZULI_ORE.id, 6)).generate(this.level, this.rand, var48, var60, var71);
        }

        var11 = 0.5D;
        int var37 = (int) (calculateTreeNoise((double)var4 * var11, (double)var5 * var11) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D / 3.0D);
        int var49 = 0;
        if (this.rand.nextInt(10) == 0) {
            ++var49;
        }

        if (var6 == Biome.FOREST) {
            var49 += var37 + 5;
        }

        if (var6 == Biome.RAINFOREST) {
            var49 += var37 + 5;
        }

        if (var6 == Biome.SEASONAL_FOREST) {
            var49 += var37 + 2;
        }

        if (var6 == Biome.TAIGA) {
            var49 += var37 + 5;
        }

        if (var6 == Biome.DESERT) {
            var49 -= 20;
        }

        if (var6 == Biome.TUNDRA) {
            var49 -= 20;
        }

        if (var6 == Biome.PLAINS) {
            var49 -= 20;
        }

        for(int var61 = 0; var61 < var49; ++var61) {
            int var72 = var4 + this.rand.nextInt(16) + 8;
            int var17 = var5 + this.rand.nextInt(16) + 8;
            Feature var18 = var6.getTree(this.rand);
            var18.method_1143(1.0D, 1.0D, 1.0D);
            var18.generate(this.level, this.rand, var72, this.level.getHeight(var72, var17), var17);
        }

        byte var62 = 0;
        if (var6 == Biome.FOREST) {
            var62 = 2;
        }

        if (var6 == Biome.SEASONAL_FOREST) {
            var62 = 4;
        }

        if (var6 == Biome.TAIGA) {
            var62 = 2;
        }

        if (var6 == Biome.PLAINS) {
            var62 = 3;
        }

        for(int var73 = 0; var73 < var62; ++var73) {
            int var76 = var4 + this.rand.nextInt(16) + 8;
            int var85 = this.rand.nextInt(128);
            int var19 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.DANDELION.id)).generate(this.level, this.rand, var76, var85, var19);
        }

        byte var74 = 0;
        if (var6 == Biome.FOREST) {
            var74 = 2;
        }

        if (var6 == Biome.RAINFOREST) {
            var74 = 10;
        }

        if (var6 == Biome.SEASONAL_FOREST) {
            var74 = 2;
        }

        if (var6 == Biome.TAIGA) {
            var74 = 1;
        }

        if (var6 == Biome.PLAINS) {
            var74 = 10;
        }

        for(int var77 = 0; var77 < var74; ++var77) {
            byte var86 = 1;
            if (var6 == Biome.RAINFOREST && this.rand.nextInt(3) != 0) {
                var86 = 2;
            }

            int var97 = var4 + this.rand.nextInt(16) + 8;
            int var20 = this.rand.nextInt(128);
            int var21 = var5 + this.rand.nextInt(16) + 8;
            (new TallgrassFeature(Tile.TALLGRASS.id, var86)).generate(this.level, this.rand, var97, var20, var21);
        }

        var74 = 0;
        if (var6 == Biome.DESERT) {
            var74 = 2;
        }

        for(int var78 = 0; var78 < var74; ++var78) {
            int var87 = var4 + this.rand.nextInt(16) + 8;
            int var98 = this.rand.nextInt(128);
            int var108 = var5 + this.rand.nextInt(16) + 8;
            (new DeadbushFeature(Tile.DEADBUSH.id)).generate(this.level, this.rand, var87, var98, var108);
        }

        if (this.rand.nextInt(2) == 0) {
            int var79 = var4 + this.rand.nextInt(16) + 8;
            int var88 = this.rand.nextInt(128);
            int var99 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.ROSE.id)).generate(this.level, this.rand, var79, var88, var99);
        }

        if (this.rand.nextInt(4) == 0) {
            int var80 = var4 + this.rand.nextInt(16) + 8;
            int var89 = this.rand.nextInt(128);
            int var100 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.MUSHROOM_1.id)).generate(this.level, this.rand, var80, var89, var100);
        }

        if (this.rand.nextInt(8) == 0) {
            int var81 = var4 + this.rand.nextInt(16) + 8;
            int var90 = this.rand.nextInt(128);
            int var101 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.MUSHROOM_2.id)).generate(this.level, this.rand, var81, var90, var101);
        }

        for(int var82 = 0; var82 < 10; ++var82) {
            int var91 = var4 + this.rand.nextInt(16) + 8;
            int var102 = this.rand.nextInt(128);
            int var109 = var5 + this.rand.nextInt(16) + 8;
            (new ReedsFeature()).generate(this.level, this.rand, var91, var102, var109);
        }

        if (this.rand.nextInt(32) == 0) {
            int var83 = var4 + this.rand.nextInt(16) + 8;
            int var92 = this.rand.nextInt(128);
            int var103 = var5 + this.rand.nextInt(16) + 8;
            (new PumpkinPatch()).generate(this.level, this.rand, var83, var92, var103);
        }

        int var84 = 0;
        if (var6 == Biome.DESERT) {
            var84 += 10;
        }

        for(int var93 = 0; var93 < var84; ++var93) {
            int var104 = var4 + this.rand.nextInt(16) + 8;
            int var110 = this.rand.nextInt(128);
            int var114 = var5 + this.rand.nextInt(16) + 8;
            (new CactusFeature()).generate(this.level, this.rand, var104, var110, var114);
        }

        for(int var94 = 0; var94 < 50; ++var94) {
            int var105 = var4 + this.rand.nextInt(16) + 8;
            int var111 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int var115 = var5 + this.rand.nextInt(16) + 8;
            (new Spring(Tile.FLOWING_WATER.id)).generate(this.level, this.rand, var105, var111, var115);
        }

        for(int var95 = 0; var95 < 20; ++var95) {
            int var106 = var4 + this.rand.nextInt(16) + 8;
            int var112 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int var116 = var5 + this.rand.nextInt(16) + 8;
            (new Spring(Tile.FLOWING_LAVA.id)).generate(this.level, this.rand, var106, var112, var116);
        }

        this.temperatureNoises = this.level.getBiomeSource().getTemperatures(this.temperatureNoises, var4 + 8, var5 + 8, 16, 16);

        for(int var96 = var4 + 8; var96 < var4 + 8 + 16; ++var96) {
            for(int var107 = var5 + 8; var107 < var5 + 8 + 16; ++var107) {
                int var113 = var96 - (var4 + 8);
                int var117 = var107 - (var5 + 8);
                int var22 = this.level.method_228(var96, var107);
                double var23 = this.temperatureNoises[var113 * 16 + var117] - (double)(var22 - 64) / 64.0D * 0.3D;
                if (var23 < 0.5D && var22 > 0 && var22 < 128 && this.level.isAir(var96, var22, var107) && this.level.getMaterial(var96, var22 - 1, var107).blocksMovement() && this.level.getMaterial(var96, var22 - 1, var107) != Material.ICE) {
                    this.level.setTile(var96, var22, var107, Tile.SNOW.id);
                }
            }
        }

        Sand.fallInstantly = false;
    }

    @Override
    public boolean method_1804(boolean flag, ProgressListener arg) {
        return true;
    }

    @Override
    public boolean method_1801() {
        return false;
    }

    @Override
    public boolean method_1805() {
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public String toString() {
        return "RetroLevelSource";
    }
}
