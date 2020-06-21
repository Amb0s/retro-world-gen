package ambos.retroworldgen.level.source;

import ambos.retroworldgen.util.noise.IndevPerlinNoise;
import ambos.retroworldgen.util.noise.IndevPerlinOctaveNoise;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.tile.Tile;

import java.util.Random;

public final  class FloatingIslandsLevelSource extends RetroLevelSource{
    private IndevPerlinOctaveNoise upperInterpolationNoise;
    private IndevPerlinOctaveNoise lowerInterpolationNoise;
    private IndevPerlinOctaveNoise interpolationNoise;
    private IndevPerlinOctaveNoise beachNoise;
    private IndevPerlinOctaveNoise surfaceDepthNoise;
    private IndevPerlinOctaveNoise biomeNoise;
    private IndevPerlinOctaveNoise depthNoise;
    private IndevPerlinOctaveNoise treeNoise;
    private IndevPerlinOctaveNoise noiseGen11;
    private IndevPerlinNoise perlinGen1;

    public FloatingIslandsLevelSource(Level level, long seed) {
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
        this.noiseGen11 = new IndevPerlinOctaveNoise(this.rand, 8);
        this.perlinGen1 = new IndevPerlinNoise(this.rand);
    }

    private double clamp(double input) {
        if (input > 1.0D) {
            return 1.0D;
        }
        if (input < -1.0D) {
            return -1.0D;
        }

        return input;
    }

    private double getNoise(int level, int x, int y, double xfact, double yfact, double zstart) {
        double output = 0;
        for (double l = 1; l <= level*level; l *= 2) {
            output += perlinGen1.a((x / xfact) * l, (y / yfact) * l) / l;
        }
        return output;
    }

    @Override
    protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        int seaLevel = 64;
        int i = chunkX << 4;
        int j = chunkZ << 4;
        int jj;

        for(int layer = 0; layer < 2; layer++) {
            jj = 0;
            for (int k = i; k < i + 16; k++) {
                for (int m = j; m < j + 16; m++) {
                    float f1 = (float)(this.upperInterpolationNoise.a(k / 0.03125f, 0.0, m / 0.03125f) - this.lowerInterpolationNoise.a(k / 0.015625f, 0.0, m / 0.015625f)) / 512.0f / 4.0f;
                    float f2 = (float)this.beachNoise.func_806_a((k + (layer * 2000F)) / 4.0F, (m + (layer * 2000F)) / 4.0F);
                    float f3 = (float)this.biomeNoise.func_806_a(k / 8.0f, m / 8.0f) / 8.0f;
                    f2 = ((f2 <= 0.0f) ? ((float)(this.surfaceDepthNoise.func_806_a(k * 0.2571428f, m * 0.2571428f) * f3)) : ((float)(this.interpolationNoise.func_806_a(k * 0.2571428f * 2.0f, m * 0.2571428f * 2.0f) * f3 / 4.0)));
                    f1 = (float)(int)(f1 + 64.0f + f2);
                    int i2 = 35 + (layer * 45) + ((int) f2);

                    if(i2 < 1) {
                        i2 = 1;
                    }

                    if ((float)this.surfaceDepthNoise.func_806_a(k, m) < 0.0F) {
                        i2 = i2 / 2 << 1;
                        f1 = (float)((int)f1 / 2 << 1);
                        if ((float)this.surfaceDepthNoise.func_806_a(k / 5, m / 5) < 0.0F) {
                            i2++;
                            ++f1;
                        }
                    }

                    int thickness = -25;
                    int less = (int) Math.floor(Math.sqrt((k-0)*(k-0) + (m-0)*(m-0)) / 3D);
                    if(less > 36) { less = 36; }
                    thickness += less;

                    double ovar32 = clamp(getNoise(8, k + (layer * 2000), m + (layer * 2000), 50, 50, 0));
                    int var77 = (int) (ovar32 * (seaLevel / 2)) + 20 + (layer * 45) + thickness;

                    for (int i3 = 0; i3 < 128; i3++) {
                        jj++;

                        if (i3 > var77 && i3 < i2) {
                            tiles[jj] = (byte) Tile.STONE.id;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
        byte byte0 = 64;
        double d = 0.03125;
        this.sandNoises = this.beachNoise.generateNoiseOctaves(this.sandNoises, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d, d, 1.0);
        this.gravelNoises = this.beachNoise.generateNoiseOctaves(this.gravelNoises, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, d, 1.0, d);
        this.surfaceDepthNoises = this.surfaceDepthNoise.generateNoiseOctaves(this.surfaceDepthNoises, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome var10 = biomes[k + l * 16];
                boolean flag = this.sandNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 0.0;
                boolean flag2 = this.gravelNoises[k + l * 16] + this.rand.nextDouble() * 0.2 > 3.0;
                int i2 = (int)(this.surfaceDepthNoises[k + l * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int j2 = -1;
                boolean isSunlit = true;
                byte byte2 = var10.topTileId;
                byte byte3 = var10.underTileId;
                for (int k2 = 127; k2 >= 0; --k2) {
                    int l2 = (l * 16 + k) * 128 + k2;
                    byte byte4 = tiles[l2];
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
                            if (isSunlit) {
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
                                byte3 = (byte)Tile.SANDSTONE.id;
                            }
                        }
                        isSunlit = false;
                    }
                }
            }
        }
    }

    @Override
    protected double calculateTreeNoise(double d1, double d2) {
        return this.treeNoise.func_806_a(d1, d2);
    }
}
