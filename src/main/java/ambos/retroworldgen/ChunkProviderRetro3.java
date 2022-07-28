package ambos.retroworldgen;

import ambos.retroworldgen.noise.IndevNoiseGeneratorOctaves;
import net.minecraft.src.*;

import java.util.Random;

public class ChunkProviderRetro3 extends ChunkProviderRetro {
    private final Random rand;
    private IndevNoiseGeneratorOctaves upperInterpolationNoise;
    private IndevNoiseGeneratorOctaves lowerInterpolationNoise;
    private IndevNoiseGeneratorOctaves interpolationNoise;
    private IndevNoiseGeneratorOctaves beachNoise;
    private IndevNoiseGeneratorOctaves surfaceDepthNoise;
    private IndevNoiseGeneratorOctaves biomeNoise;


    public ChunkProviderRetro3(World world, long seed) {
        super(world, seed);
        this.rand = new Random(seed);
        this.upperInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.lowerInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.interpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 8);
        this.beachNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.surfaceDepthNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.biomeNoise = new IndevNoiseGeneratorOctaves((this.rand), 5);
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] blocks, BiomeGenBase[] biomes, double[] temperatures) {
        int i1 = chunkX << 4;
        int i2 = chunkZ << 4;
        int i5 = 0;
        for (int i6 = i1; i6 < i1 + 16; ++i6) {
            for (int i7 = i2; i7 < i2 + 16; ++i7) {
                float f1 = (float) (this.upperInterpolationNoise.generateNoiseOctaves(i6 / 0.03125f, 0.0,
                        i7 / 0.03125f) - this.lowerInterpolationNoise.generateNoiseOctaves(i6 / 0.015625f,
                        0.0, i7 / 0.015625f)) / 512.0f / 4.0f;
                float f2 = (float) this.beachNoise.noiseGenerator(i6 / 4.0f, i7 / 4.0f);
                float f3 = (float) this.biomeNoise.noiseGenerator(i6 / 8.0f, i7 / 8.0f) / 8.0f;
                f2 = ((f2 <= 0.0f) ? ((float) (this.surfaceDepthNoise.noiseGenerator(i6 * 0.2571428f,
                        i7 * 0.2571428f) * f3)) : ((float) (this.interpolationNoise.noiseGenerator(i6 *
                        0.2571428f * 2.0f, i7 * 0.2571428f * 2.0f) * f3 / 4.0)));
                f1 = (float) (int) (f1 + 64.0f + f2);
                if ((float) this.surfaceDepthNoise.noiseGenerator(i6, i7) < 0.0f) {
                    f1 = (float) ((int) f1 / 2 << 1);
                    if ((float) this.surfaceDepthNoise.noiseGenerator(i6 / 5.0, i7 / 5.0) < 0.0f) {
                        ++f1;
                    }
                }
                for (int i14 = 0; i14 < 256; ++i14) {
                    int i15 = 0;
                    if (((i6 == 0 && chunkX == 0) || (i7 == 0 && chunkZ == 0)) && i14 <= f1) {
                        i15 = Block.stone.blockID;
                    }
                    if (i14 == f1 + 1.0f && f1 >= 64.0f && Math.random() < 0.02) {
                        i15 = 0;
                    }
                    else if (i14 == f1 && f1 >= 64.0f) {
                        i15 = Block.stone.blockID;
                    }
                    else if (i14 <= f1 - 2.0f) {
                        i15 = Block.stone.blockID;
                    }
                    else if (i14 <= f1) {
                        i15 = Block.stone.blockID;
                    }
                    else if (i14 < 64) {
                        i15 = Block.fluidWaterStill.blockID;
                    }
                    this.rand.setSeed(chunkX + chunkZ * 13871);
                    int i16 = (chunkX << 10) + 256 + this.rand.nextInt(512);
                    int i17 = (chunkZ << 10) + 256 + this.rand.nextInt(512);
                    i16 = i6 - i16;
                    i17 = i7 - i17;
                    if (i16 < 0) {
                        i16 = -i16;
                    }
                    if (i17 < 0) {
                        i17 = -i17;
                    }
                    if (i17 > i16) {
                        i16 = i17;
                    }
                    if ((i16 = 127 - i16) == 255) {
                        i16 = 1;
                    }
                    if (i16 < f1) {
                        i16 = (int) f1;
                    }
                    if (i15 < 0) {
                        i15 = 0;
                    }
                    blocks[i5++] = (short) i15;
                }
            }
        }
    }
}