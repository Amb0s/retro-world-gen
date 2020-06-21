package ambos.retroworldgen.level.source;

import ambos.retroworldgen.util.noise.AlphaPerlinOctaveNoise;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.tile.Tile;

import java.util.Random;

public final class AlphaLevelSource extends RetroLevelSource {
    private AlphaPerlinOctaveNoise upperInterpolationNoise;
    private AlphaPerlinOctaveNoise lowerInterpolationNoise;
    private AlphaPerlinOctaveNoise interpolationNoise;
    private AlphaPerlinOctaveNoise beachNoise;
    private AlphaPerlinOctaveNoise surfaceDepthNoise;
    private AlphaPerlinOctaveNoise biomeNoise;
    private AlphaPerlinOctaveNoise depthNoise;
    private AlphaPerlinOctaveNoise treeNoise;
    
    public AlphaLevelSource(Level level, long seed) {
        this.level = level;
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new AlphaPerlinOctaveNoise(this.rand, 16);
        this.lowerInterpolationNoise = new AlphaPerlinOctaveNoise(this.rand, 16);
        this.interpolationNoise = new AlphaPerlinOctaveNoise(this.rand, 8);
        this.beachNoise = new AlphaPerlinOctaveNoise(this.rand, 4);
        this.surfaceDepthNoise = new AlphaPerlinOctaveNoise(this.rand, 4);
        this.biomeNoise = new AlphaPerlinOctaveNoise(this.rand, 10);
        this.depthNoise = new AlphaPerlinOctaveNoise(this.rand, 16);
        this.treeNoise = new AlphaPerlinOctaveNoise(this.rand, 8);
    }

    protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        byte byte0 = 4;
        byte byte2 = 64;
        int k = byte0 + 1;
        byte byte3 = 17;
        int l = byte0 + 1;
        this.noises = this.calculateNoise(this.noises, chunkX * byte0, 0, chunkZ * byte0, k, byte3, l);
        for (int i2 = 0; i2 < byte0; ++i2) {
            for (int j2 = 0; j2 < byte0; ++j2) {
                for (int k2 = 0; k2 < 16; ++k2) {
                    double d = 0.125;
                    double d2 = this.noises[((i2 + 0) * l + (j2 + 0)) * byte3 + (k2 + 0)];
                    double d3 = this.noises[((i2 + 0) * l + (j2 + 1)) * byte3 + (k2 + 0)];
                    double d4 = this.noises[((i2 + 1) * l + (j2 + 0)) * byte3 + (k2 + 0)];
                    double d5 = this.noises[((i2 + 1) * l + (j2 + 1)) * byte3 + (k2 + 0)];
                    double d6 = (this.noises[((i2 + 0) * l + (j2 + 0)) * byte3 + (k2 + 1)] - d2) * d;
                    double d7 = (this.noises[((i2 + 0) * l + (j2 + 1)) * byte3 + (k2 + 1)] - d3) * d;
                    double d8 = (this.noises[((i2 + 1) * l + (j2 + 0)) * byte3 + (k2 + 1)] - d4) * d;
                    double d9 = (this.noises[((i2 + 1) * l + (j2 + 1)) * byte3 + (k2 + 1)] - d5) * d;
                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d10 = 0.25;
                        double d11 = d2;
                        double d12 = d3;
                        double d13 = (d4 - d2) * d10;
                        double d14 = (d5 - d3) * d10;
                        for (int i3 = 0; i3 < 4; ++i3) {
                            int j3 = i3 + i2 * 4 << 11 | j2 * 4 << 7 | k2 * 8 + l2;
                            char c = '\u0080';
                            double d15 = 0.25;
                            double d16 = d11;
                            double d17 = (d12 - d11) * d15;
                            for (int k3 = 0; k3 < 4; ++k3) {
                                double var53 = temperatures[(i2 * 4 + i3) * 16 + j2 * 4 + k3];
                                int l3 = 0;
                                if (k2 * 8 + l2 < byte2) {
                                    if (var53 < 0.5D && k2 * 8 + l2 >= byte2 - 1) {
                                        l3 = Tile.ICE.id;
                                    }
                                    else {
                                        l3 = Tile.STILL_WATER.id;
                                    }
                                }
                                if (d16 > 0.0) {
                                    l3 = Tile.STONE.id;
                                }
                                tiles[j3] = (byte) l3;
                                j3 += c;
                                d16 += d17;
                            }
                            d11 += d13;
                            d12 += d14;
                        }
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                        d5 += d9;
                    }
                }
            }
        }
    }

    protected void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
        byte var5 = 64;
        double var6 = 0.03125D;
        this.sandNoises = this.beachNoise.sample(this.sandNoises, (double) (chunkX * 16), (double) (chunkZ * 16), 0.0D, 16, 16, 1, var6, var6, 1.0D);
        this.gravelNoises = this.beachNoise.sample(this.gravelNoises, (double) (chunkX * 16), 109.0134D, (double) (chunkZ * 16), 16, 1, 16, var6, 1.0D, var6);
        this.surfaceDepthNoises = this.surfaceDepthNoise.sample(this.surfaceDepthNoises, (double) (chunkX * 16), (double) (chunkZ * 16), 0.0D, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var9 = 0; var9 < 16; ++var9) {
                Biome var10 = biomes[var8 + var9 * 16];
                boolean var11 = this.sandNoises[var8 + var9 * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean var12 = this.gravelNoises[var8 + var9 * 16] + this.rand.nextDouble() * 0.2D > 3.0D;
                int var13 = (int) (this.surfaceDepthNoises[var8 + var9 * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int var14 = -1;
                byte var15 = var10.topTileId;
                byte var16 = var10.underTileId;
                for (int var17 = 127; var17 >= 0; --var17) {
                    int var18 = (var9 * 16 + var8) * 128 + var17;
                    if (var17 <= this.rand.nextInt(5)) {
                        tiles[var18] = (byte) Tile.BEDROCK.id;
                    } else {
                        byte var19 = tiles[var18];
                        if (var19 == 0) {
                            var14 = -1;
                        } else if (var19 == Tile.STONE.id) {
                            if (var14 == -1) {
                                if (var13 <= 0) {
                                    var15 = 0;
                                    var16 = (byte) Tile.STONE.id;
                                } else if (var17 >= var5 - 4 && var17 <= var5 + 1) {
                                    var15 = var10.topTileId;
                                    var16 = var10.underTileId;
                                    if (var12) {
                                        var15 = 0;
                                    }
                                    if (var12) {
                                        var16 = (byte) Tile.GRAVEL.id;
                                    }
                                    if (var11) {
                                        var15 = (byte) Tile.SAND.id;
                                    }
                                    if (var11) {
                                        var16 = (byte) Tile.SAND.id;
                                    }
                                }
                                if (var17 < var5 && var15 == 0) {
                                    var15 = (byte) Tile.STILL_WATER.id;
                                }
                                var14 = var13;
                                if (var17 >= var5 - 1) {
                                    tiles[var18] = var15;
                                } else {
                                    tiles[var18] = var16;
                                }
                            } else if (var14 > 0) {
                                --var14;
                                tiles[var18] = var16;
                                if (var14 == 0 && var16 == Tile.SAND.id) {
                                    var14 = this.rand.nextInt(4);
                                    var16 = (byte) Tile.SANDSTONE.id;
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

    private double[] calculateNoise(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }
        double d = 684.412;
        double d2 = 684.412;
        this.biomeNoises = this.biomeNoise.sample(this.biomeNoises, i, k, k, l, 1, j1, 1.0, 0.0, 1.0);
        this.depthNoises = this.depthNoise.sample(this.depthNoises, i, k, k, l, 1, j1, 100.0, 0.0, 100.0);
        this.interpolationNoises = this.interpolationNoise.sample(this.interpolationNoises, i, j, k, l, i1, j1, d / 80.0, d2 / 160.0, d / 80.0);
        this.upperInterpolationNoises = this.upperInterpolationNoise.sample(this.upperInterpolationNoises, i, j, k, l, i1, j1, d, d2, d);
        this.lowerInterpolationNoises = this.lowerInterpolationNoise.sample(this.lowerInterpolationNoises, i, j, k, l, i1, j1, d, d2, d);
        int k2 = 0;
        int l2 = 0;
        for (int i2 = 0; i2 < l; ++i2) {
            for (int j2 = 0; j2 < j1; ++j2) {
                double d3 = (this.biomeNoises[l2] + 256.0) / 512.0;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }
                double d4 = 0.0;
                double d5 = this.depthNoises[l2] / 8000.0;
                if (d5 < 0.0) {
                    d5 = -d5;
                }
                d5 = d5 * 3.0 - 3.0;
                if (d5 < 0.0) {
                    d5 /= 2.0;
                    if (d5 < -1.0) {
                        d5 = -1.0;
                    }
                    d5 /= 1.4;
                    d5 /= 2.0;
                    d3 = 0.0;
                }
                else {
                    if (d5 > 1.0) {
                        d5 = 1.0;
                    }
                    d5 /= 6.0;
                }
                d3 += 0.5;
                d5 = d5 * i1 / 16.0;
                double d6 = i1 / 2.0 + d5 * 4.0;
                ++l2;
                for (int k3 = 0; k3 < i1; ++k3) {
                    double d7 = 0.0;
                    double d8 = (k3 - d6) * 12.0 / d3;
                    if (d8 < 0.0) {
                        d8 *= 4.0;
                    }
                    double d9 = this.upperInterpolationNoises[k2] / 512.0;
                    double d10 = this.lowerInterpolationNoises[k2] / 512.0;
                    double d11 = (this.interpolationNoises[k2] / 10.0 + 1.0) / 2.0;
                    if (d11 < 0.0) {
                        d7 = d9;
                    }
                    else if (d11 > 1.0) {
                        d7 = d10;
                    }
                    else {
                        d7 = d9 + (d10 - d9) * d11;
                    }
                    d7 -= d8;
                    if (k3 > i1 - 4) {
                        double d12 = (k3 - (i1 - 4)) / 3.0f;
                        d7 = d7 * (1.0 - d12) + -10.0 * d12;
                    }
                    if (k3 < d4) {
                        double d13 = (d4 - k3) / 4.0;
                        if (d13 < 0.0) {
                            d13 = 0.0;
                        }
                        if (d13 > 1.0) {
                            d13 = 1.0;
                        }
                        d7 = d7 * (1.0 - d13) + -10.0 * d13;
                    }
                    ad[k2] = d7;
                    ++k2;
                }
            }
        }
        return ad;
    }
}
