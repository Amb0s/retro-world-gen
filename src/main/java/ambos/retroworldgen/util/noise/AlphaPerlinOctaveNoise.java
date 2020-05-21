package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public class AlphaPerlinOctaveNoise extends Noise {
    private AlphaPerlinNoise[] generatorCollection;
    private int field_1191_b;
    
    public AlphaPerlinOctaveNoise(final Random random, final int i) {
        this.field_1191_b = i;
        this.generatorCollection = new AlphaPerlinNoise[i];
        for (int j = 0; j < i; ++j) {
            this.generatorCollection[j] = new AlphaPerlinNoise(random);
        }
    }
    
    public double func_806_a(final double d, final double d1) {
        double d2 = 0.0;
        double d3 = 1.0;
        for (int i = 0; i < this.field_1191_b; ++i) {
            d2 += this.generatorCollection[i].func_801_a(d * d3, d1 * d3) / d3;
            d3 /= 2.0;
        }
        return d2;
    }
    
    public double[] generateNoiseOctaves(double[] ad, final double d, final double d1, final double d2, final int i, final int j, final int k, final double d3, final double d4, final double d5) {
        if (ad == null) {
            ad = new double[i * j * k];
        }
        else {
            for (int l = 0; l < ad.length; ++l) {
                ad[l] = 0.0;
            }
        }
        double d6 = 1.0;
        for (int i2 = 0; i2 < this.field_1191_b; ++i2) {
            this.generatorCollection[i2].func_805_a(ad, d, d1, d2, i, j, k, d3 * d6, d4 * d6, d5 * d6, d6);
            d6 /= 2.0;
        }
        return ad;
    }
    
    public double[] func_4109_a(final double[] ad, final int i, final int j, final int k, final int l, final double d, final double d1, final double d2) {
        return this.generateNoiseOctaves(ad, i, 10.0, j, k, 1, l, d, 1.0, d1);
    }
}
