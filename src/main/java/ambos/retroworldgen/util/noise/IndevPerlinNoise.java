package ambos.retroworldgen.util.noise;

import net.minecraft.util.maths.MathsHelper;
import net.minecraft.util.noise.Noise;

import java.util.Random;

public class IndevPerlinNoise extends Noise {
    private int[] permutations;
    private double xCoord;
    private double yCoord;
    private double zCoord;
    
    public IndevPerlinNoise() {
        this(new Random());
    }
    
    public IndevPerlinNoise(final Random random) {
        this.permutations = new int[512];
        this.xCoord = random.nextDouble() * 256.0;
        this.yCoord = random.nextDouble() * 256.0;
        this.zCoord = random.nextDouble() * 256.0;
        for (int i = 0; i < 256; ++i) {
            this.permutations[i] = i;
        }
        for (int j = 0; j < 256; ++j) {
            final int k = random.nextInt(256 - j) + j;
            final int l = this.permutations[j];
            this.permutations[j] = this.permutations[k];
            this.permutations[k] = l;
            this.permutations[j + 256] = this.permutations[j];
        }
    }
    
    private double generateNoise(double d1, double d2, double d3) {
        double d4 = d1 + this.xCoord;
        double d5 = d2 + this.yCoord;
        double d6 = d3 + this.zCoord;
        d1 = (MathsHelper.floor(d4) & 0xFF);
        double i = MathsHelper.floor(d5) & 0xFF;
        d2 = (MathsHelper.floor(d6) & 0xFF);
        d4 -= MathsHelper.floor(d4);
        d5 -= MathsHelper.floor(d5);
        d6 -= MathsHelper.floor(d6);
        final double d7 = a(d4);
        final double d8 = a(d5);
        final double d9 = a(d6);
        double j = this.permutations[(int)d1] + i;
        d3 = this.permutations[(int)j] + d2;
        j = this.permutations[(int)j + 1] + d2;
        d1 = this.permutations[(int)d1 + 1] + i;
        i = this.permutations[(int)d1] + d2;
        d1 = this.permutations[(int)d1 + 1] + d2;
        return lerp(d9, lerp(d8, lerp(d7, grad(this.permutations[(int)d3], d4, d5, d6), grad(this.permutations[(int)i], d4 - 1.0, d5, d6)), lerp(d7, grad(this.permutations[(int)j], d4, d5 - 1.0, d6), grad(this.permutations[(int)d1], d4 - 1.0, d5 - 1.0, d6))), lerp(d8, lerp(d7, grad(this.permutations[(int)d3 + 1], d4, d5, d6 - 1.0), grad(this.permutations[(int)i + 1], d4 - 1.0, d5, d6 - 1.0)), lerp(d7, grad(this.permutations[(int)j + 1], d4, d5 - 1.0, d6 - 1.0), grad(this.permutations[(int)d1 + 1], d4 - 1.0, d5 - 1.0, d6 - 1.0))));
    }
    
    private static double a(final double d1) {
        return d1 * d1 * d1 * (d1 * (d1 * 6.0 - 15.0) + 10.0);
    }
    
    private static double lerp(final double d1, final double d2, final double d3) {
        return d2 + d1 * (d3 - d2);
    }
    
    private static double grad(int i, final double d1, final double d2, final double d3) {
        final double d4 = ((i &= 0xF) >= 8) ? d2 : d1;
        final double d5 = (i >= 4) ? ((i != 12 && i != 14) ? d3 : d1) : d2;
        return (((i & 0x1) != 0x0) ? (-d4) : d4) + (((i & 0x2) != 0x0) ? (-d5) : d5);
    }
    
    public double a(final double d1, final double d2) {
        return this.generateNoise(d1, d2, 0.0);
    }
    
    public double a(final double d1, final double d2, final double d3) {
        return this.generateNoise(d1, d2, d3);
    }

    public void func_805_a(final double[] ad, final int i, final int j, final int k, final int l, final int i1, final int j1, final double d1, final double d2, final double d3, double d4) {
        int l2 = 0;
        final double d5 = 1.0 / d4;
        d4 = -1.0;
        double d6 = 0.0;
        double d7 = 0.0;
        double d8 = 0.0;
        double d9 = 0.0;
        for (int l3 = 0; l3 < l; ++l3) {
            double d10;
            int k2 = (int)(d10 = (i + l3) * d1 + this.xCoord);
            if (d10 < k2) {
                --k2;
            }
            final int i2 = k2 & 0xFF;
            final double d11 = (d10 -= k2) * d10 * d10 * (d10 * (d10 * 6.0 - 15.0) + 10.0);
            for (int j2 = 0; j2 < j1; ++j2) {
                double d12;
                k2 = (int)(d12 = (k + j2) * d3 + this.zCoord);
                if (d12 < k2) {
                    --k2;
                }
                final int k3 = k2 & 0xFF;
                final double d13 = (d12 -= k2) * d12 * d12 * (d12 * (d12 * 6.0 - 15.0) + 10.0);
                for (int l4 = 0; l4 < i1; ++l4) {
                    double d14;
                    k2 = (int)(d14 = (j + l4) * d2 + this.yCoord);
                    if (d14 < k2) {
                        --k2;
                    }
                    int j3 = k2 & 0xFF;
                    final double d15 = (d14 -= k2) * d14 * d14 * (d14 * (d14 * 6.0 - 15.0) + 10.0);
                    if (l4 == 0 || j3 != d4) {
                        d4 = j3;
                        k2 = this.permutations[i2] + j3;
                        final int i3 = this.permutations[k2] + k3;
                        k2 = this.permutations[k2 + 1] + k3;
                        j3 += this.permutations[i2 + 1];
                        final int k4 = this.permutations[j3] + k3;
                        j3 = this.permutations[j3 + 1] + k3;
                        d6 = this.lerp(d11, this.grad(this.permutations[i3], d10, d14, d12), this.grad(this.permutations[k4], d10 - 1.0, d14, d12));
                        d7 = this.lerp(d11, this.grad(this.permutations[k2], d10, d14 - 1.0, d12), this.grad(this.permutations[j3], d10 - 1.0, d14 - 1.0, d12));
                        d8 = this.lerp(d11, this.grad(this.permutations[i3 + 1], d10, d14, d12 - 1.0), this.grad(this.permutations[k4 + 1], d10 - 1.0, d14, d12 - 1.0));
                        d9 = this.lerp(d11, this.grad(this.permutations[k2 + 1], d10, d14 - 1.0, d12 - 1.0), this.grad(this.permutations[j3 + 1], d10 - 1.0, d14 - 1.0, d12 - 1.0));
                    }
                    final double d16 = this.lerp(d15, d6, d7);
                    final double d17 = this.lerp(d15, d8, d9);
                    final double d18 = this.lerp(d13, d16, d17);
                    final int n = l2++;
                    ad[n] += d18 * d5;
                }
            }
        }
    }
}
