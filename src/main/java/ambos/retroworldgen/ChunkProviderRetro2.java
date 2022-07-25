package ambos.retroworldgen;

import ambos.retroworldgen.noise.InfdevNoiseGeneratorOctaves;
import net.minecraft.shared.Minecraft;
import net.minecraft.src.*;

import java.util.Random;

public class ChunkProviderRetro2 extends ChunkProviderRetro {
    private final Random random;
    private InfdevNoiseGeneratorOctaves upperInterpolationNoise;
    private InfdevNoiseGeneratorOctaves lowerInterpolationNoise;
    private InfdevNoiseGeneratorOctaves interpolationNoise;

    public ChunkProviderRetro2(World world, long seed) {
        super(world, seed);
        this.random = new Random(seed);
        this.upperInterpolationNoise = new InfdevNoiseGeneratorOctaves(this.random, 16);
        this.lowerInterpolationNoise = new InfdevNoiseGeneratorOctaves(this.random, 16);
        this.interpolationNoise = new InfdevNoiseGeneratorOctaves(this.random, 8);
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] blocks, BiomeGenBase[] biomes, double[] temperatures) {
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
                            int n20 = n16 + (i << 2) << Minecraft.WORLD_HEIGHT_BITS + 4 |
                                    (j << 2) << Minecraft.WORLD_HEIGHT_BITS | (k << 2) + n10;
                            char c = (char) Minecraft.WORLD_HEIGHT_BLOCKS;
                            for (int n21 = 0; n21 < 4; ++n21) {
                                double n22 = n18 + (n19 - n18) * (n21 / 4.0);
                                int n23 = 0;
                                if ((k << 2) + n10 < 64) {
                                    n23 = Block.fluidWaterStill.blockID;
                                }
                                if (n22 > 0.0) {
                                    n23 = Block.stone.blockID;
                                }
                                blocks[n20] = (short) n23;
                                n20 += c;
                            }
                        }
                    }
                }
            }
        }
    }

    private double calculateNoise(double double1, double double2, double double3) {
        double n;
        if ((n = double2 * 4.0 - 64.0) < 0.0) {
            n *= 3.0;
        }
        double n2;
        double n3;
        if ((n2 = interpolationNoise.generateNoise(double1 * 684.412 / 80.0, double2 * 684.412 / 400.0,
                double3 * 684.412 / 80.0) / 2.0) < -1.0) {
            if ((n3 = upperInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412,
                    double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else if (n2 > 1.0) {
            if ((n3 = lowerInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412,
                    double3 * 684.412) / 512.0 - n) < -10.0) {
                n3 = -10.0;
            }
            if (n3 > 10.0) {
                n3 = 10.0;
            }
        }
        else {
            double n4 = upperInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412,
                    double3 * 684.412) / 512.0 - n;
            double n5 = lowerInterpolationNoise.generateNoise(double1 * 684.412, double2 * 984.412,
                    double3 * 684.412) / 512.0 - n;
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
