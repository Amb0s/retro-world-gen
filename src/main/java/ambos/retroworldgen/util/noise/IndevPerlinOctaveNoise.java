package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public final class IndevPerlinOctaveNoise extends Noise {
    private IndevPerlinNoise[] field_1744;
    private int field_1745;
    
    public IndevPerlinOctaveNoise(Random random, int i) {
        this.field_1745 = i;
        this.field_1744 = new IndevPerlinNoise[i];
        for (int j = 0; j < i; ++j) {
            this.field_1744[j] = new IndevPerlinNoise(random);
        }
    }
    
    public double sample(double d, double d1) {
        double d2 = 0.0;
        double d3 = 1.0;
        for (int i = 0; i < this.field_1745; ++i) {
            d2 += this.field_1744[i].method_1204(d / d3, d1 / d3) * d3;
            d3 *= 2.0;
        }
        return d2;
    }
    
    public double sample(double d, double d1, double d2) {
        double d3 = 0.0;
        double d4 = 1.0;
        for (d1 = 0.0; d1 < this.field_1745; ++d1) {
            d3 += this.field_1744[(int)d1].method_1204(d / d4, 0.0 / d4, d2 / d4) * d4;
            d4 *= 2.0;
        }
        return d3;
    }

    public double[] sample(double[] ds, int i, int j, int k, int l, int i1, int j1, double d, double d1, double d2) {
        if (ds == null) {
            ds = new double[l * i1 * j1];
        }
        else {
            for (int k2 = 0; k2 < ds.length; ++k2) {
                ds[k2] = 0.0;
            }
        }
        double d3 = 1.0;
        for (int l2 = 0; l2 < this.field_1745; ++l2) {
            this.field_1744[l2].sample(ds, i, j, k, l, i1, j1, d * d3, d1 * d3, d2 * d3, d3);
            d3 /= 2.0;
        }
        return ds;
    }
}
