package ambos.retroworldgen;

import ambos.retroworldgen.noise.IndevNoiseGeneratorOctaves;
import ambos.retroworldgen.noise.InfdevNoiseGeneratorOctaves;
import net.minecraft.shared.Minecraft;
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


    public ChunkProviderRetro3(World world, long l) {
        super(world, l);
        this.rand = new Random(l);
        this.upperInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.lowerInterpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 16);
        this.interpolationNoise = new IndevNoiseGeneratorOctaves(this.rand, 8);
        this.beachNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.surfaceDepthNoise = new IndevNoiseGeneratorOctaves((this.rand), 4);
        this.biomeNoise = new IndevNoiseGeneratorOctaves((this.rand), 5);
        this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
        this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] tiles, BiomeGenBase[] biomes, double[] temperatures) {
        int k = chunkX << 4;
        int g11 = chunkZ << 4;
        int l = 0;
        for (int m = k; m < k + 16; ++m) {
            for (int l2 = g11; l2 < g11 + 16; ++l2) {
                float f1 = (float) (this.upperInterpolationNoise.generateNoiseOctaves(m / 0.03125f, 0.0, l2 / 0.03125f) - this.lowerInterpolationNoise.generateNoiseOctaves(m / 0.015625f, 0.0, l2 / 0.015625f)) / 512.0f / 4.0f;
                float f2 = (float) this.beachNoise.noiseGenerator(m / 4.0f, l2 / 4.0f);
                float f3 = (float) this.biomeNoise.noiseGenerator(m / 8.0f, l2 / 8.0f) / 8.0f;
                f2 = ((f2 <= 0.0f) ? ((float) (this.surfaceDepthNoise.noiseGenerator(m * 0.2571428f, l2 * 0.2571428f) * f3)) : ((float) (this.interpolationNoise.noiseGenerator(m * 0.2571428f * 2.0f, l2 * 0.2571428f * 2.0f) * f3 / 4.0)));
                f1 = (float) (int) (f1 + 64.0f + f2);
                if ((float) this.surfaceDepthNoise.noiseGenerator(m, l2) < 0.0f) {
                    f1 = (float) ((int) f1 / 2 << 1);
                    if ((float) this.surfaceDepthNoise.noiseGenerator(m / 5.0, l2 / 5.0) < 0.0f) {
                        ++f1;
                    }
                }
                for (int genBlockY = 0; genBlockY < 256; ++genBlockY) {
                    // double var53 = ds[((abs(m) % 4) * 4 + (abs(l2) % 4)) * 16 + (abs(l2) % 4) * 4 + (genBlockY % 5)];
                    int l3 = 0;
                    if (((m == 0 && chunkX == 0) || (l2 == 0 && chunkZ == 0)) && genBlockY <= f1) {
                        l3 = Block.stone.blockID;
                    }
                    if (genBlockY == f1 + 1.0f && f1 >= 64.0f && Math.random() < 0.02) {
                        l3 = 0;
                    }
                    else if (genBlockY == f1 && f1 >= 64.0f) {
                        l3 = Block.stone.blockID;
                    }
                    else if (genBlockY <= f1 - 2.0f) {
                        l3 = Block.stone.blockID;
                    }
                    else if (genBlockY <= f1) {
                        l3 = Block.stone.blockID;
                    }
                    else if (genBlockY < 64) {
                        /*if (var53 < 0.5D && genBlockY >= 64 - 1) {
                            l3 = Tile.ICE.id;
                        }
                        else {
                        }*/
                        l3 = Block.fluidWaterStill.blockID;
                    }
                    this.rand.setSeed(chunkX + chunkZ * 13871);
                    int i12 = (chunkX << 10) + 256 + this.rand.nextInt(512);
                    int j12 = (chunkZ << 10) + 256 + this.rand.nextInt(512);
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
                    tiles[l++] = (short) l3;
                }
            }
        }
    }
}