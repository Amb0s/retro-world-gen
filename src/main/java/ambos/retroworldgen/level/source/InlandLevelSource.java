package ambos.retroworldgen.level.source;

import ambos.retroworldgen.util.noise.IndevPerlinOctaveNoise;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.tile.Tile;

import java.util.Random;

public final class InlandLevelSource extends RetroLevelSource {
    private IndevPerlinOctaveNoise upperInterpolationNoise;
    private IndevPerlinOctaveNoise lowerInterpolationNoise;
    private IndevPerlinOctaveNoise interpolationNoise;
    private IndevPerlinOctaveNoise beachNoise;
    private IndevPerlinOctaveNoise surfaceDepthNoise;
    private IndevPerlinOctaveNoise biomeNoise;
    private IndevPerlinOctaveNoise depthNoise;
    private IndevPerlinOctaveNoise treeNoise;

    public InlandLevelSource(Level level, long seed) {
        this.level = level;
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new IndevPerlinOctaveNoise(this.rand, 16);
        this.lowerInterpolationNoise = new IndevPerlinOctaveNoise(this.rand, 16);
        this.interpolationNoise = new IndevPerlinOctaveNoise(this.rand, 8);
        this.beachNoise = new IndevPerlinOctaveNoise(this.rand, 4);
        this.surfaceDepthNoise = new IndevPerlinOctaveNoise(this.rand, 4);
        this.biomeNoise = new IndevPerlinOctaveNoise(this.rand, 5);
        this.depthNoise = new IndevPerlinOctaveNoise(this.rand, 5);
        this.treeNoise = new IndevPerlinOctaveNoise(this.rand, 8);
    }

    @Override
    protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        int k = chunkX << 4;
        int g11 = chunkZ << 4;
        int l = 0;
        for (int m = k; m < k + 16; ++m) {
            for (int l2 = g11; l2 < g11 + 16; ++l2) {
                float f1 = (float) (this.upperInterpolationNoise.sample(m / 0.03125f, 0.0, l2 / 0.03125f) - this.lowerInterpolationNoise.sample(m / 0.015625f, 0.0, l2 / 0.015625f)) / 512.0f / 4.0f;
                float f2 = (float) this.beachNoise.sample(m / 4.0f, l2 / 4.0f);
                float f3 = (float) this.biomeNoise.sample(m / 8.0f, l2 / 8.0f) / 8.0f;
                f2 = ((f2 <= 0.0f) ? ((float) (this.surfaceDepthNoise.sample(m * 0.2571428f, l2 * 0.2571428f) * f3)) : ((float) (this.interpolationNoise.sample(m * 0.2571428f * 2.0f, l2 * 0.2571428f * 2.0f) * f3 / 4.0)));
                f1 = (float) (int) (f1 + 64.0f + f2);
                if ((float) this.surfaceDepthNoise.sample(m, l2) < 0.0f) {
                    f1 = (float) ((int) f1 / 2 << 1);
                    if ((float) this.surfaceDepthNoise.sample(m / 5.0, l2 / 5.0) < 0.0f) {
                        ++f1;
                    }
                }
                for (int genBlockY = 0; genBlockY < 128; ++genBlockY) {
                    // double var53 = ds[((abs(m) % 4) * 4 + (abs(l2) % 4)) * 16 + (abs(l2) % 4) * 4 + (genBlockY % 5)];
                    int l3 = 0;
                    if (((m == 0 && chunkX == 0) || (l2 == 0 && chunkZ == 0)) && genBlockY <= f1) {
                        l3 = Tile.STONE.id;
                    }
                    if (genBlockY == f1 + 1.0f && f1 >= 64.0f && Math.random() < 0.02) {
                        l3 = 0;
                    }
                    else if (genBlockY == f1 && f1 >= 64.0f) {
                        l3 = Tile.STONE.id;
                    }
                    else if (genBlockY <= f1 - 2.0f) {
                        l3 = Tile.STONE.id;
                    }
                    else if (genBlockY <= f1) {
                        l3 = Tile.STONE.id;
                    }
                    else if (genBlockY < 64) {
                        /*if (var53 < 0.5D && genBlockY >= 64 - 1) {
                            l3 = Tile.ICE.id;
                        }
                        else {

                        }*/
                        l3 = Tile.STILL_WATER.id;
                    }
                    this.rand.setSeed(chunkX + chunkZ * 13871);
                    int i12 = (chunkX << 10) + 128 + this.rand.nextInt(512);
                    int j12 = (chunkZ << 10) + 128 + this.rand.nextInt(512);
                    i12 = m - i12;
                    j12 = l2 - j12;
                    if (i12 < 0) {
                        i12 = -i12;
                    }
                    if (j12 < 0) {
                        j12 = -j12;
                    }
                    if (j12 > i12) {
                        i12 = j12;
                    }
                    if ((i12 = 127 - i12) == 255) {
                        i12 = 1;
                    }
                    if (i12 < f1) {
                        i12 = (int) f1;
                    }
                    if (l3 < 0) {
                        l3 = 0;
                    }
                    tiles[l++] = (byte) l3;
                }
            }
        }
    }

    @Override
    protected void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
        byte byte0 = 64;
        double d = 0.03125;
        this.sandNoises = this.beachNoise.sample(this.sandNoises, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d, d, 1.0);
        this.gravelNoises = this.beachNoise.sample(this.gravelNoises, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, d, 1.0, d);
        this.surfaceDepthNoises = this.surfaceDepthNoise.sample(this.surfaceDepthNoises, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome var10 = biomes[k + l * 16];
                boolean flag = this.sandNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 0.0;
                boolean flag2 = this.gravelNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 3.0;
                int i2 = (int) (this.surfaceDepthNoises[k + l * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int j2 = -1;
                byte byte2 = var10.topTileId;
                byte byte3 = var10.underTileId;
                for (int k2 = 127; k2 >= 0; --k2) {
                    int l2 = (l * 16 + k) * 128 + k2;
                    if (k2 <= this.rand.nextInt(5)) {
                        tiles[l2] = (byte) Tile.BEDROCK.id;
                    }
                    else {
                        byte byte4 = tiles[l2];
                        if (byte4 == 0) {
                            j2 = -1;
                        }
                        else if (byte4 == Tile.STONE.id) {
                            if (j2 == -1) {
                                if (i2 <= 0) {
                                    byte2 = 0;
                                    byte3 = (byte) Tile.STONE.id;
                                }
                                else if (k2 >= byte0 - 4 && k2 <= byte0 + 1) {
                                    byte2 = var10.topTileId;
                                    byte3 = var10.underTileId;
                                    if (flag2) {
                                        byte2 = 0;
                                    }
                                    if (flag2) {
                                        byte3 = (byte) Tile.GRAVEL.id;
                                    }
                                    if (flag) {
                                        byte2 = (byte) Tile.SAND.id;
                                    }
                                    if (flag) {
                                        byte3 = (byte) Tile.SAND.id;
                                    }
                                }
                                if (k2 < byte0 && byte2 == 0) {
                                    byte2 = (byte) Tile.STILL_WATER.id;
                                }
                                j2 = i2;
                                if (k2 >= byte0 - 1) {
                                    tiles[l2] = byte2;
                                }
                                else {
                                    tiles[l2] = byte3;
                                }
                            }
                            else if (j2 > 0) {
                                --j2;
                                tiles[l2] = byte3;
                                if (j2 == 0 && byte3 == Tile.SAND.id) {
                                    j2 = this.rand.nextInt(4);
                                    byte3 = (byte) Tile.SANDSTONE.id;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected double calculateTreeNoise(double d1, double d2) {
        return this.treeNoise.sample(d1, d2);
    }
}
