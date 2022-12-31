package ambos.retroworldgen;

import ambos.retroworldgen.noise.IndevNoiseGeneratorOctaves;
import ambos.retroworldgen.noise.IndevNoiseGeneratorPerlin;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderRetro;
import net.minecraft.src.World;

import java.util.Random;

public class ChunkProviderRetro4 extends ChunkProviderRetro {
    private final Random rand;
    private IndevNoiseGeneratorOctaves upperInterpolationNoise;
    private IndevNoiseGeneratorOctaves lowerInterpolationNoise;
    private IndevNoiseGeneratorOctaves interpolationNoise;
    private IndevNoiseGeneratorOctaves beachNoise;
    private IndevNoiseGeneratorOctaves surfaceDepthNoise;
    private IndevNoiseGeneratorOctaves biomeNoise;

    private IndevNoiseGeneratorPerlin perlinNoise;


    public ChunkProviderRetro4(World world, long seed) {
        super(world, seed);
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.lowerInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.interpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 8);
        this.beachNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.surfaceDepthNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.biomeNoise = new IndevNoiseGeneratorOctaves((this.rand), 5);

        this.perlinNoise = new IndevNoiseGeneratorPerlin(this.rand);
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
            output += perlinNoise.generateNoise((x / xfact) * l, (y / yfact) * l) / l;
        }

        return output;
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] blocks, BiomeGenBase[] biomes, double[] temperatures) {
        int layers = 1;
        int height = 128;
        int seaLevel = 64;
        byte[] arrayOfByte = new byte[32768];
        int i = chunkX << 4;
        int j = chunkZ << 4;
        int jj = 0;
        int lx = 0; int lz = 0;

        for (int k = i; k < i + 16; k++) {
            for (int m = j; m < j + 16; m++) {
                int n = k / 1024;
                int i1 = m / 1024;
                float f1 = (float)(this.upperInterpolationNoise.generateNoiseOctaves(k / 0.03125F, 0.0D,
                        m / 0.03125F) - this.lowerInterpolationNoise.generateNoiseOctaves(k / 0.015625F,
                        0.0D, m / 0.015625F)) / 512.0F / 4.0F;
                float f2 = (float)this.surfaceDepthNoise.noiseGenerator(k / 4.0F, m / 4.0F);
                float f3 = (float)this.biomeNoise.noiseGenerator(k / 8.0F, m / 8.0F) / 8.0F;
                f2 = f2 > 0.0F ? (float)(this.interpolationNoise.noiseGenerator(k * 0.2571428F * 2.0F,
                        m * 0.2571428F * 2.0F) * f3 / 4.0D) : (float)(this.beachNoise.noiseGenerator(k *
                        0.2571428F, m * 0.2571428F) * f3);
                int i2 = (int)(f1 + 64.0F + f2);

                if ((float)this.surfaceDepthNoise.noiseGenerator(k, m) < 0.0F) {
                    i2 = i2 / 2 << 1;
                    if ((float)this.surfaceDepthNoise.noiseGenerator(k / 5, m / 5) < 0.0F) {
                        i2++;
                    }
                }

                // BEACH SETTINGS
                boolean flagSand = this.interpolationNoise.noiseGenerator(k, m) > 25D;
                boolean flagGravel = this.interpolationNoise.noiseGenerator(k, m) > 50D; //boolean flagGravel = noiseGen11.a(k, m) > 18D; //12D

                double ovar32 = clamp(getNoise(8, k, m, 70.3, 70.3, 0));
                int var77 = (int) ((ovar32 * (seaLevel / 2)) * 2) + seaLevel;

                // CREATE WORLD
                for (int i3 = 0; i3 < 256; i3++) {
                    int i4 = 0;
                    int beachHeight = seaLevel + 1;

                    // GENERATE GRASS
                    if ((i3 == i2) && i2 >= beachHeight) {
                            i4 = Block.grassRetro.blockID;
                    }

                    //BEACH GEN
                    else if (i3 == i2) {
                        if (flagGravel) {
                            i4 = Block.gravel.blockID;
                        }
                        else if (flagSand) {
                            i4 = Block.sand.blockID;
                        }
                        else if (i2 > seaLevel - 1) {
                            i4 = Block.grassRetro.blockID;
                        }
                        else {
                            i4 = Block.grassRetro.blockID;
                        }
                    } else if (i3 <= i2 - 2) { //GENERATE STONE
                        i4 = Block.stone.blockID;
                    } else if (i3 < i2) { //GENERATE DIRT
                        i4 = Block.dirt.blockID;
                    }

                    if (i3 < var77 && i4 != 0) {
                        if ((i3 > 60) && i4 == Block.gravel.blockID) {}
                        else if ((i3 > 60) && i4 == Block.sand.blockID) {}
                        else {
                            i4 = 0;
                        }
                    }

                    this.rand.setSeed(chunkX + chunkZ * 13871);
                    int i5 = (n << 10) + 256 + rand.nextInt(512);
                    int i6 = (i1 << 10) + 256 + rand.nextInt(512);
                    i5 = k - i5;
                    int i7 = m - i6;
                    if (i5 < 0) {
                        i5 = -i5;
                    }
                    if (i7 < 0) {
                        i7 = -i7;
                    }
                    if (i7 > i5) {
                        i5 = i7;
                    }
                    if ((i5 = 127 - i5) == 255) {
                        i5 = 1;
                    }
                    if (i5 < i2) {
                        i5 = i2;
                    }
                    if (i4 < 0) {
                        i4 = 0;
                    }

                    blocks[jj++] = (short)i4;
                }
            }
        }
    }
}
