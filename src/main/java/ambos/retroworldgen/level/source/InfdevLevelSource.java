package ambos.retroworldgen.level.source;

import ambos.retroworldgen.util.noise.InfdevPerlinOctaveNoise;
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

public class InfdevLevelSource implements LevelSource {
    private Random rand;
    private InfdevPerlinOctaveNoise field_2255;
    private InfdevPerlinOctaveNoise field_2256;
    private InfdevPerlinOctaveNoise field_2257;
    private InfdevPerlinOctaveNoise field_2258;
    private InfdevPerlinOctaveNoise field_2259;
    public InfdevPerlinOctaveNoise field_2245;
    public InfdevPerlinOctaveNoise field_2246;
    public InfdevPerlinOctaveNoise field_2247;
    private Level field_2260;
    private double[] field_2261;
    private double[] field_2262 = new double[256];
    private double[] field_2263 = new double[256];
    private double[] field_2264 = new double[256];
    private Cave cave = new OverworldCave();
    private Biome[] field_2266;
    double[] field_2248;
    double[] field_2249;
    double[] field_2250;
    double[] field_2251;
    double[] field_2252;
    int[][] field_2253 = new int[32][32];
    private double[] field_2267;

    public InfdevLevelSource(Level arg, long l) {
        this.field_2260 = arg;
        this.rand = new Random(l);
        this.field_2255 = new InfdevPerlinOctaveNoise(this.rand, 16);
        this.field_2256 = new InfdevPerlinOctaveNoise(this.rand, 16);
        this.field_2257 = new InfdevPerlinOctaveNoise(this.rand, 8);
        this.field_2258 = new InfdevPerlinOctaveNoise(this.rand, 4);
        this.field_2259 = new InfdevPerlinOctaveNoise(this.rand, 4);
        this.field_2245 = new InfdevPerlinOctaveNoise(this.rand, 5);
        this.field_2246 = new InfdevPerlinOctaveNoise(this.rand, 5);
        this.field_2247 = new InfdevPerlinOctaveNoise(this.rand, 8);
    }

    public void method_1798(int integer1, int integer2, byte[] bs, double[] ds) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                double[][] array = new double[33][4];
                int k = (integer1 << 2) + i;
                int n = (integer2 << 2) + j;
                for (int l = 0; l < array.length; ++l) {
                    array[l][0] = this.method_1799(k, l, n);
                    array[l][1] = this.method_1799(k, l, n + 1);
                    array[l][2] = this.method_1799(k + 1, l, n);
                    array[l][3] = this.method_1799(k + 1, l, n + 1);
                }
                for (k = 0; k < 32; ++k) {
                    double n2 = array[k][0];
                    double n3 = array[k][1];
                    double n4 = array[k][2];
                    double n5 = array[k][3];
                    double n6 = array[k + 1][0];
                    double n7 = array[k + 1][1];
                    double n8 = array[k + 1][2];
                    double n9 = array[k + 1][3];
                    for (int n10 = 0; n10 < 4; ++n10) {
                        double n11 = n10 / 4.0;
                        double n12 = n2 + (n6 - n2) * n11;
                        double n13 = n3 + (n7 - n3) * n11;
                        double n14 = n4 + (n8 - n4) * n11;
                        double n15 = n5 + (n9 - n5) * n11;
                        for (int n16 = 0; n16 < 4; ++n16) {
                            double n17 = n16 / 4.0;
                            double n18 = n12 + (n14 - n12) * n17;
                            double n19 = n13 + (n15 - n13) * n17;
                            int n20 = n16 + (i << 2) << 11 | 0 + (j << 2) << 7 | (k << 2) + n10;
                            for (int n21 = 0; n21 < 4; ++n21) {
                                double var53 = ds[(i * 4 + n16) * 16 + j * 4 + n21];
                                double n22 = n18 + (n19 - n18) * (n21 / 4.0);
                                int n23 = 0;
                                if ((k << 2) + n10 < 64) {
                                    if (var53 < 0.5D) {
                                        n23 = Tile.ICE.id;
                                    } else {
                                        n23 = Tile.STILL_WATER.id;
                                    }

                                }
                                if (n22 > 0.0) {
                                    n23 = Tile.STONE.id;
                                }
                                bs[n20] = (byte)n23;
                                n20 += 128;
                            }
                        }
                    }
                }
            }
        }
    }

    public void method_1797(int i, int j, byte[] bs, Biome[] args) {
        byte byte0 = 64;
        double d = 0.03125;
        this.field_2262 = this.field_2258.generateNoiseOctaves(this.field_2262, i * 16, j * 16, 0, 16, 16, 1, d, d, 1.0);
        this.field_2263 = this.field_2258.generateNoiseOctaves(this.field_2263, i * 16, 109, j * 16, 16, 1, 16, d, 1.0, d);
        this.field_2264 = this.field_2259.generateNoiseOctaves(this.field_2264, i * 16, j * 16, 0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome var10 = args[k + l * 16];
                boolean flag = this.field_2262[k + l * 16] + this.rand.nextDouble() * 0.2 > 0.0;
                boolean flag2 = this.field_2263[k + l * 16] + this.rand.nextDouble() * 0.2 > 3.0;
                int i2 = (int)(this.field_2264[k + l * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int j2 = -1;
                byte byte2 = var10.topTileId;
                byte byte3 = var10.underTileId;
                for (int k2 = 127; k2 >= 0; --k2) {
                    int l2 = (l * 16 + k) * 128 + k2;
                    if (k2 <= 0 + this.rand.nextInt(5)) {
                        bs[l2] = (byte)Tile.BEDROCK.id;
                    }
                    else {
                        byte byte4 = bs[l2];
                        if (byte4 == 0) {
                            j2 = -1;
                        }
                        else if (byte4 == Tile.STONE.id) {
                            if (j2 == -1) {
                                if (i2 <= 0) {
                                    byte2 = 0;
                                    byte3 = (byte)Tile.STONE.id;
                                }
                                else if (k2 >= byte0 - 4 && k2 <= byte0 + 1) {
                                    byte2 = var10.topTileId;
                                    byte3 = var10.underTileId;
                                    if (flag2) {
                                        byte2 = 0;
                                    }
                                    if (flag2) {
                                        byte3 = (byte)Tile.GRAVEL.id;
                                    }
                                    if (flag) {
                                        byte2 = (byte)Tile.SAND.id;
                                    }
                                    if (flag) {
                                        byte3 = (byte)Tile.SAND.id;
                                    }
                                }
                                if (k2 < byte0 && byte2 == 0) {
                                    byte2 = (byte)Tile.STILL_WATER.id;
                                }
                                j2 = i2;
                                if (k2 >= byte0 - 1) {
                                    bs[l2] = byte2;
                                }
                                else {
                                    bs[l2] = byte3;
                                }
                            }
                            else if (j2 > 0) {
                                --j2;
                                bs[l2] = byte3;
                                if (j2 == 0 && byte3 == Tile.SAND.id) {
                                    j2 = this.rand.nextInt(4);
                                    byte3 = (byte)Tile.SANDSTONE.id;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Chunk method_1807(int i, int j) {
        return this.getChunk(i, j);
    }

    @Override
    public Chunk getChunk(int i, int j) {
        this.rand.setSeed(i * 341873128712L + j * 132897987541L);
        byte[] var3 = new byte[32768];
        Chunk chunk = new Chunk(this.field_2260, var3, i, j);
        this.field_2266 = this.field_2260.getBiomeSource().getBiomes(this.field_2266, i * 16, j * 16, 16, 16);
        double[] var5 = this.field_2260.getBiomeSource().field_2235;
        this.method_1798(i, j, var3, var5);
        this.method_1797(i, j, var3, this.field_2266);
        this.cave.generate(this, this.field_2260, i, j, var3);
        chunk.method_873();
        return chunk;
    }

    private double method_1799(double double1, double double2, double double3) {
        double n;
        if ((n = double2 * 4.0 - 64.0) < 0.0) {
            n *= 3.0;
        }
        double n2;
        double n3;
        if ((n2 = field_2257.generateNoise(double1 * 684.412 / 80.0, double2 * 684.412 / 400.0, double3 * 684.412 / 80.0) / 2.0) < -1.0) {
            if ((n3 = field_2255.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else if (n2 > 1.0) {
            if ((n3 = field_2256.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else {
            double n4 = field_2255.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
            double n5 = field_2256.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
            if (n4 < -10.0) {
                n4 = -10.0;
            }
            if (n4 > 10.0) {
                n4 = 10.0;
            }
            if (n5 < -10.0) {
                n5 = -10.0;
            }
            if (n5 > 10.0) {
                n5 = 10.0;
            }
            n3 = n4 + (n5 - n4) * ((n2 + 1.0) / 2.0);
        }
        return n3;
    }

    @Override
    public boolean isChunkLoaded(int i, int j) {
        return true;
    }

    @Override
    public void decorate(LevelSource arg, int i, int j) {
        Sand.fallInstantly = true;
        int var4 = i * 16;
        int var5 = j * 16;
        Biome var6 = this.field_2260.getBiomeSource().getBiome(var4 + 16, var5 + 16);
        this.rand.setSeed(this.field_2260.getSeed());
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)i * var7 + (long)j * var9 ^ this.field_2260.getSeed());
        double var11 = 0.25D;
        if (this.rand.nextInt(4) == 0) {
            int var13 = var4 + this.rand.nextInt(16) + 8;
            int var14 = this.rand.nextInt(128);
            int var15 = var5 + this.rand.nextInt(16) + 8;
            (new Lake(Tile.STILL_WATER.id)).generate(this.field_2260, this.rand, var13, var14, var15);
        }

        if (this.rand.nextInt(8) == 0) {
            int var26 = var4 + this.rand.nextInt(16) + 8;
            int var38 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int var50 = var5 + this.rand.nextInt(16) + 8;
            if (var38 < 64 || this.rand.nextInt(10) == 0) {
                (new Lake(Tile.STILL_LAVA.id)).generate(this.field_2260, this.rand, var26, var38, var50);
            }
        }

        for(int var27 = 0; var27 < 8; ++var27) {
            int var39 = var4 + this.rand.nextInt(16) + 8;
            int var51 = this.rand.nextInt(128);
            int var16 = var5 + this.rand.nextInt(16) + 8;
            (new MobSpawnerRoom()).generate(this.field_2260, this.rand, var39, var51, var16);
        }

        for(int var28 = 0; var28 < 10; ++var28) {
            int var40 = var4 + this.rand.nextInt(16);
            int var52 = this.rand.nextInt(128);
            int var63 = var5 + this.rand.nextInt(16);
            (new ClayDiskFeature(32)).generate(this.field_2260, this.rand, var40, var52, var63);
        }

        for(int var29 = 0; var29 < 20; ++var29) {
            int var41 = var4 + this.rand.nextInt(16);
            int var53 = this.rand.nextInt(128);
            int var64 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.DIRT.id, 32)).generate(this.field_2260, this.rand, var41, var53, var64);
        }

        for(int var30 = 0; var30 < 10; ++var30) {
            int var42 = var4 + this.rand.nextInt(16);
            int var54 = this.rand.nextInt(128);
            int var65 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.GRAVEL.id, 32)).generate(this.field_2260, this.rand, var42, var54, var65);
        }

        for(int var31 = 0; var31 < 20; ++var31) {
            int var43 = var4 + this.rand.nextInt(16);
            int var55 = this.rand.nextInt(128);
            int var66 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.COAL_ORE.id, 16)).generate(this.field_2260, this.rand, var43, var55, var66);
        }

        for(int var32 = 0; var32 < 20; ++var32) {
            int var44 = var4 + this.rand.nextInt(16);
            int var56 = this.rand.nextInt(64);
            int var67 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.IRON_ORE.id, 8)).generate(this.field_2260, this.rand, var44, var56, var67);
        }

        for(int var33 = 0; var33 < 2; ++var33) {
            int var45 = var4 + this.rand.nextInt(16);
            int var57 = this.rand.nextInt(32);
            int var68 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.GOLD_ORE.id, 8)).generate(this.field_2260, this.rand, var45, var57, var68);
        }

        for(int var34 = 0; var34 < 8; ++var34) {
            int var46 = var4 + this.rand.nextInt(16);
            int var58 = this.rand.nextInt(16);
            int var69 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.REDSTONE_ORE.id, 7)).generate(this.field_2260, this.rand, var46, var58, var69);
        }

        for(int var35 = 0; var35 < 1; ++var35) {
            int var47 = var4 + this.rand.nextInt(16);
            int var59 = this.rand.nextInt(16);
            int var70 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.ORE_DIAMOND.id, 7)).generate(this.field_2260, this.rand, var47, var59, var70);
        }

        for(int var36 = 0; var36 < 1; ++var36) {
            int var48 = var4 + this.rand.nextInt(16);
            int var60 = this.rand.nextInt(16) + this.rand.nextInt(16);
            int var71 = var5 + this.rand.nextInt(16);
            (new Ore(Tile.LAPIS_LAZULI_ORE.id, 6)).generate(this.field_2260, this.rand, var48, var60, var71);
        }

        var11 = 0.5D;
        int var37 = (int)((this.field_2247.func_806_a((double)var4 * var11, (double)var5 * var11) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D) / 3.0D);
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
            var18.generate(this.field_2260, this.rand, var72, this.field_2260.getHeight(var72, var17), var17);
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
            (new MushroomFeature(Tile.DANDELION.id)).generate(this.field_2260, this.rand, var76, var85, var19);
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
            (new TallgrassFeature(Tile.TALLGRASS.id, var86)).generate(this.field_2260, this.rand, var97, var20, var21);
        }

        var74 = 0;
        if (var6 == Biome.DESERT) {
            var74 = 2;
        }

        for(int var78 = 0; var78 < var74; ++var78) {
            int var87 = var4 + this.rand.nextInt(16) + 8;
            int var98 = this.rand.nextInt(128);
            int var108 = var5 + this.rand.nextInt(16) + 8;
            (new DeadbushFeature(Tile.DEADBUSH.id)).generate(this.field_2260, this.rand, var87, var98, var108);
        }

        if (this.rand.nextInt(2) == 0) {
            int var79 = var4 + this.rand.nextInt(16) + 8;
            int var88 = this.rand.nextInt(128);
            int var99 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.ROSE.id)).generate(this.field_2260, this.rand, var79, var88, var99);
        }

        if (this.rand.nextInt(4) == 0) {
            int var80 = var4 + this.rand.nextInt(16) + 8;
            int var89 = this.rand.nextInt(128);
            int var100 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.MUSHROOM_1.id)).generate(this.field_2260, this.rand, var80, var89, var100);
        }

        if (this.rand.nextInt(8) == 0) {
            int var81 = var4 + this.rand.nextInt(16) + 8;
            int var90 = this.rand.nextInt(128);
            int var101 = var5 + this.rand.nextInt(16) + 8;
            (new MushroomFeature(Tile.MUSHROOM_2.id)).generate(this.field_2260, this.rand, var81, var90, var101);
        }

        for(int var82 = 0; var82 < 10; ++var82) {
            int var91 = var4 + this.rand.nextInt(16) + 8;
            int var102 = this.rand.nextInt(128);
            int var109 = var5 + this.rand.nextInt(16) + 8;
            (new ReedsFeature()).generate(this.field_2260, this.rand, var91, var102, var109);
        }

        if (this.rand.nextInt(32) == 0) {
            int var83 = var4 + this.rand.nextInt(16) + 8;
            int var92 = this.rand.nextInt(128);
            int var103 = var5 + this.rand.nextInt(16) + 8;
            (new PumpkinPatch()).generate(this.field_2260, this.rand, var83, var92, var103);
        }

        int var84 = 0;
        if (var6 == Biome.DESERT) {
            var84 += 10;
        }

        for(int var93 = 0; var93 < var84; ++var93) {
            int var104 = var4 + this.rand.nextInt(16) + 8;
            int var110 = this.rand.nextInt(128);
            int var114 = var5 + this.rand.nextInt(16) + 8;
            (new CactusFeature()).generate(this.field_2260, this.rand, var104, var110, var114);
        }

        for(int var94 = 0; var94 < 50; ++var94) {
            int var105 = var4 + this.rand.nextInt(16) + 8;
            int var111 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            int var115 = var5 + this.rand.nextInt(16) + 8;
            (new Spring(Tile.FLOWING_WATER.id)).generate(this.field_2260, this.rand, var105, var111, var115);
        }

        for(int var95 = 0; var95 < 20; ++var95) {
            int var106 = var4 + this.rand.nextInt(16) + 8;
            int var112 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
            int var116 = var5 + this.rand.nextInt(16) + 8;
            (new Spring(Tile.FLOWING_LAVA.id)).generate(this.field_2260, this.rand, var106, var112, var116);
        }

        this.field_2267 = this.field_2260.getBiomeSource().method_1790(this.field_2267, var4 + 8, var5 + 8, 16, 16);

        for(int var96 = var4 + 8; var96 < var4 + 8 + 16; ++var96) {
            for(int var107 = var5 + 8; var107 < var5 + 8 + 16; ++var107) {
                int var113 = var96 - (var4 + 8);
                int var117 = var107 - (var5 + 8);
                int var22 = this.field_2260.method_228(var96, var107);
                double var23 = this.field_2267[var113 * 16 + var117] - (double)(var22 - 64) / 64.0D * 0.3D;
                if (var23 < 0.5D && var22 > 0 && var22 < 128 && this.field_2260.isAir(var96, var22, var107) && this.field_2260.getMaterial(var96, var22 - 1, var107).blocksMovement() && this.field_2260.getMaterial(var96, var22 - 1, var107) != Material.ICE) {
                    this.field_2260.setTile(var96, var22, var107, Tile.SNOW.id);
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
    public String toString() {
        return "RandomLevelSource";
    }
}

