package ambos.retroworldgen;

import ambos.retroworldgen.noise.IndevNoiseGeneratorOctaves;
import ambos.retroworldgen.noise.InfdevNoiseGeneratorOctaves;
import net.minecraft.shared.Minecraft;
import net.minecraft.src.*;

import java.util.Random;

public class ChunkProviderRetro3 extends ChunkProviderRetro {
    private Random rand = new Random();
    private Random rand2 = new Random();
    private IndevNoiseGeneratorOctaves noiseGen3 = new IndevNoiseGeneratorOctaves(16);
    private IndevNoiseGeneratorOctaves mobSpawnerNoise = new IndevNoiseGeneratorOctaves(16);
    private IndevNoiseGeneratorOctaves f = new IndevNoiseGeneratorOctaves(8);
    private IndevNoiseGeneratorOctaves g = new IndevNoiseGeneratorOctaves(4);
    private IndevNoiseGeneratorOctaves h = new IndevNoiseGeneratorOctaves(4);
    private IndevNoiseGeneratorOctaves i = new IndevNoiseGeneratorOctaves(5);

    public ChunkProviderRetro3(World world, long l) {
        super(world, l);
        this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
        this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
    }

    @Override
    public void generateTerrain(int chunkX, int chunkZ, short[] tiles, BiomeGenBase[] biomes, double[] temperatures) {
        int i1 = chunkX << 4;
        int i2 = chunkZ << 4;
        int i5 = 0;
        for(int i6 = i1; i6 < i1 + 16; ++i6) {
            for(int i7 = i2; i7 < i2 + 16; ++i7) {
                int i8 = i6 / 1024;
                int i9 = i7 / 1024;
                float f10 = (float)(this.noiseGen3.generateNoiseOctaves((double)((float)i6 / 0.03125F), 0.0D, (double)((float)i7 / 0.03125F)) - this.mobSpawnerNoise.generateNoiseOctaves((double)((float)i6 / 0.015625F), 0.0D, (double)((float)i7 / 0.015625F))) / 512.0F / 4.0F;
                float f11 = (float)this.h.noiseGenerator((double)((float)i6 / 4.0F), (double)((float)i7 / 4.0F));
                float f12 = (float)this.i.noiseGenerator((double)((float)i6 / 8.0F), (double)((float)i7 / 8.0F)) / 8.0F;
                f11 = f11 > 0.0F ? (float)(this.f.noiseGenerator((double)((float)i6 * 0.25714284F * 2.0F), (double)((float)i7 * 0.25714284F * 2.0F)) * (double)f12 / 4.0D) : (float)(this.g.noiseGenerator((double)((float)i6 * 0.25714284F), (double)((float)i7 * 0.25714284F)) * (double)f12);
                int i13 = (int)(f10 + 64.0F + f11);
                if((float)this.h.noiseGenerator((double)i6, (double)i7) < 0.0F) {
                    i13 = i13 / 2 << 1;
                    if((float)this.h.noiseGenerator((double)(i6 / 5), (double)(i7 / 5)) < 0.0F) {
                        ++i13;
                    }
                }

                for(int i14 = 0; i14 < 256; ++i14) {
                    int i15 = 0;
                    if(i14 == i13 && i13 >= 63) {
                        i15 = Block.stone.blockID;
                    } else if(i14 == i13) {
                        i15 = Block.stone.blockID;
                    } else if(i14 <= i13 - 2) {
                        i15 = Block.stone.blockID;
                    } else if(i14 <= i13) {
                        i15 = Block.stone.blockID;
                    } else if(i14 < 64) {
                        i15 = Block.fluidWaterStill.blockID;
                    }

                    this.rand2.setSeed((long)(i8 + i9 * 13871));
                    int i16 = (i8 << 10) + 256 + this.rand2.nextInt(512);
                    int i17 = (i9 << 10) + 256 + this.rand2.nextInt(512);
                    i16 = i6 - i16;
                    i17 = i7 - i17;
                    if(i16 < 0) {
                        i16 = -i16;
                    }

                    if(i17 < 0) {
                        i17 = -i17;
                    }

                    if(i17 > i16) {
                        i16 = i17;
                    }

                    if((i16 = 127 - i16) == 255) {
                        i16 = 1;
                    }

                    if(i16 < i13) {
                        i16 = i13;
                    }

                    if(i15 < 0) {
                        i15 = 0;
                    }

                    tiles[i5++] = (short)i15;
                }
            }
        }
    }
}
