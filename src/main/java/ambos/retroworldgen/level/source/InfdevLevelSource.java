package ambos.retroworldgen.level.source;

import ambos.retroworldgen.util.noise.InfdevPerlinOctaveNoise;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.tile.Tile;

import java.util.Random;

public final class InfdevLevelSource extends RetroLevelSource {
    private InfdevPerlinOctaveNoise upperInterpolationNoise;
    private InfdevPerlinOctaveNoise lowerInterpolationNoise;
    private InfdevPerlinOctaveNoise interpolationNoise;
    private InfdevPerlinOctaveNoise beachNoise;
    private InfdevPerlinOctaveNoise surfaceDepthNoise;
    private InfdevPerlinOctaveNoise biomeNoise;
    private InfdevPerlinOctaveNoise depthNoise;
    private InfdevPerlinOctaveNoise treeNoise;

    public InfdevLevelSource(Level level, long seed) {
        this.level = level;
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new InfdevPerlinOctaveNoise(this.rand, 16);
        this.lowerInterpolationNoise = new InfdevPerlinOctaveNoise(this.rand, 16);
        this.interpolationNoise = new InfdevPerlinOctaveNoise(this.rand, 8);
        this.beachNoise = new InfdevPerlinOctaveNoise(this.rand, 4);
        this.surfaceDepthNoise = new InfdevPerlinOctaveNoise(this.rand, 4);
        this.biomeNoise = new InfdevPerlinOctaveNoise(this.rand, 5);
        this.depthNoise = new InfdevPerlinOctaveNoise(this.rand, 5);
        this.treeNoise = new InfdevPerlinOctaveNoise(this.rand, 8);
    }

    @Override
    protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                double[][] array = new double[33][4];
                int k = (chunkX << 2) + i;
                int n = (chunkZ << 2) + j;
                for (int l = 0; l < array.length; ++l) {
                    array[l][0] = this.calculateNoise(k, l, n);
                    array[l][1] = this.calculateNoise(k, l, n + 1);
                    array[l][2] = this.calculateNoise(k + 1, l, n);
                    array[l][3] = this.calculateNoise(k + 1, l, n + 1);
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
                            int n20 = n16 + (i << 2) << 11 | (j << 2) << 7 | (k << 2) + n10;
                            for (int n21 = 0; n21 < 4; ++n21) {
                                double var53 = temperatures[(i * 4 + n16) * 16 + j * 4 + n21];
                                double n22 = n18 + (n19 - n18) * (n21 / 4.0);
                                int n23 = 0;
                                if ((k << 2) + n10 < 64) {
                                    if (var53 < 0.5D && (k << 2) + n10 >= 64 - 1) {
                                        n23 = Tile.ICE.id;
                                    } else {
                                        n23 = Tile.STILL_WATER.id;
                                    }
                                }
                                if (n22 > 0.0) {
                                    n23 = Tile.STONE.id;
                                }
                                tiles[n20] = (byte)n23;
                                n20 += 128;
                            }
                        }
                    }
                }
            }
        }
    }

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
                byte byte2 = var10.topTileId;
                byte byte3 = var10.underTileId;
                for (int k2 = 127; k2 >= 0; --k2) {
                    int l2 = (l * 16 + k) * 128 + k2;
                    if (k2 <= 0 + this.rand.nextInt(5)) {
                        tiles[l2] = (byte)Tile.BEDROCK.id;
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
                        }
                    }
                }
            }
        }
    }

    @Override
    protected double calculateTreeNoise(double d1, double d2) {
        return this.treeNoise.func_806_a(d1, d2);
    }

    private double calculateNoise(double double1, double double2, double double3) {
        double n;
        if ((n = double2 * 4.0 - 64.0) < 0.0) {
            n *= 3.0;
        }
        double n2;
        double n3;
        if ((n2 = interpolationNoise.generateNoise(double1 * 684.412 / 80.0, double2 * 684.412 / 400.0, double3 * 684.412 / 80.0) / 2.0) < -1.0) {
            if ((n3 = upperInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else if (n2 > 1.0) {
            if ((n3 = lowerInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else {
            double n4 = upperInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
            double n5 = lowerInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412, double3 * 684.412) / 512.0 - n;
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
}

