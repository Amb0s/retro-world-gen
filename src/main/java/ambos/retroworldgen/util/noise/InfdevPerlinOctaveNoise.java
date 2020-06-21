package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public final class InfdevPerlinOctaveNoise extends Noise {
    private InfdevPerlinNoise[] generatorCollection;
    private int octaves;
    
    public InfdevPerlinOctaveNoise(final Random random, final int i) {
        this.octaves = i;
        this.generatorCollection = new InfdevPerlinNoise[i];
        for (int j = 0; j < i; ++j) {
            this.generatorCollection[j] = new InfdevPerlinNoise(random);
        }
    }
    
    public double func_806_a(final double d, final double d1) {
        double d2 = 0.0;
        double d3 = 1.0;
        for (int i = 0; i < this.octaves; ++i) {
            d2 += this.generatorCollection[i].method_1204(d * d3, d1 * d3) / d3;
            d3 /= 2.0;
        }
        return d2;
    }
    
    public double generateNoise(final double d, final double d1, final double d2) {
        double d3 = 0.0;
        double d4 = 1.0;
        for (int i = 0; i < this.octaves; ++i) {
            d3 += this.generatorCollection[i].method_1204(d * d4, d1 * d4, d2 * d4) / d4;
            d4 /= 2.0;
        }
        return d3;
    }
    
    public double[] generateNoiseOctaves(double[] ad, final int i, final int j, final int k, final int l, final int i1, final int j1, final double d, final double d1, final double d2) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }
        else {
            for (int k2 = 0; k2 < ad.length; ++k2) {
                ad[k2] = 0.0;
            }
        }
        double d3 = 1.0;
        for (int l2 = 0; l2 < this.octaves; ++l2) {
            this.generatorCollection[l2].sample(ad, i, j, k, l, i1, j1, d * d3, d1 * d3, d2 * d3, d3);
            d3 /= 2.0;
        }
        return ad;
    }
}
