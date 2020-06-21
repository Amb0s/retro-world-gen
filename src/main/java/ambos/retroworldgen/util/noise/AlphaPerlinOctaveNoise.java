package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public final class AlphaPerlinOctaveNoise extends Noise {
    private AlphaPerlinNoise[] field_1744;
    private int field_1745;
    
    public AlphaPerlinOctaveNoise(Random random, int i) {
        this.field_1745 = i;
        this.field_1744 = new AlphaPerlinNoise[i];
        for (int j = 0; j < i; ++j) {
            this.field_1744[j] = new AlphaPerlinNoise(random);
        }
    }
    
    public double sample(double d, double d1) {
        double d2 = 0.0;
        double d3 = 1.0;
        for (int i = 0; i < this.field_1745; ++i) {
            d2 += this.field_1744[i].method_1204(d * d3, d1 * d3) / d3;
            d3 /= 2.0;
        }
        return d2;
    }
    
    public double[] sample(double[] ds, double d, double d1, double d2, int i, int j, int k, double d3, double d4, double d5) {
        if (ds == null) {
            ds = new double[i * j * k];
        }
        else {
            for (int l = 0; l < ds.length; ++l) {
                ds[l] = 0.0;
            }
        }
        double d6 = 1.0;
        for (int i2 = 0; i2 < this.field_1745; ++i2) {
            this.field_1744[i2].sample(ds, d, d1, d2, i, j, k, d3 * d6, d4 * d6, d5 * d6, d6);
            d6 /= 2.0;
        }
        return ds;
    }
    
    public double[] sample(double[] ds, int i, int j, int k, int l, double d, double d1, double d2) {
        return this.sample(ds, i, 10.0, j, k, 1, l, d, 1.0, d1);
    }
}
