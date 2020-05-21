package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public class AlphaPerlinNoise extends Noise {
    private int[] permutations;
    public double xCoord;
    public double yCoord;
    public double zCoord;
    
    public AlphaPerlinNoise() {
        this(new Random());
    }
    
    public AlphaPerlinNoise(final Random random) {
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
    
    public double generateNoise(final double d, final double d1, final double d2) {
        double d3 = d + this.xCoord;
        double d4 = d1 + this.yCoord;
        double d5 = d2 + this.zCoord;
        int i = (int)d3;
        int j = (int)d4;
        int k = (int)d5;
        if (d3 < i) {
            --i;
        }
        if (d4 < j) {
            --j;
        }
        if (d5 < k) {
            --k;
        }
        final int l = i & 0xFF;
        final int i2 = j & 0xFF;
        final int j2 = k & 0xFF;
        d3 -= i;
        d4 -= j;
        d5 -= k;
        final double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0 - 15.0) + 10.0);
        final double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0 - 15.0) + 10.0);
        final double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0 - 15.0) + 10.0);
        final int k2 = this.permutations[l] + i2;
        final int l2 = this.permutations[k2] + j2;
        final int i3 = this.permutations[k2 + 1] + j2;
        final int j3 = this.permutations[l + 1] + i2;
        final int k3 = this.permutations[j3] + j2;
        final int l3 = this.permutations[j3 + 1] + j2;
        return this.lerp(d8, this.lerp(d7, this.lerp(d6, this.grad(this.permutations[l2], d3, d4, d5), this.grad(this.permutations[k3], d3 - 1.0, d4, d5)), this.lerp(d6, this.grad(this.permutations[i3], d3, d4 - 1.0, d5), this.grad(this.permutations[l3], d3 - 1.0, d4 - 1.0, d5))), this.lerp(d7, this.lerp(d6, this.grad(this.permutations[l2 + 1], d3, d4, d5 - 1.0), this.grad(this.permutations[k3 + 1], d3 - 1.0, d4, d5 - 1.0)), this.lerp(d6, this.grad(this.permutations[i3 + 1], d3, d4 - 1.0, d5 - 1.0), this.grad(this.permutations[l3 + 1], d3 - 1.0, d4 - 1.0, d5 - 1.0))));
    }
    
    public final double lerp(final double d, final double d1, final double d2) {
        return d1 + d * (d2 - d1);
    }
    
    public final double grad(final int i, final double d, final double d1, final double d2) {
        final int j = i & 0xF;
        final double d3 = (j >= 8) ? d1 : d;
        final double d4 = (j >= 4) ? ((j != 12 && j != 14) ? d2 : d) : d1;
        return (((j & 0x1) != 0x0) ? (-d3) : d3) + (((j & 0x2) != 0x0) ? (-d4) : d4);
    }
    
    public double func_801_a(final double d, final double d1) {
        return this.generateNoise(d, d1, 0.0);
    }
    
    public void func_805_a(final double[] ad, final double d, final double d1, final double d2, final int i, final int j, final int k, final double d3, final double d4, final double d5, final double d6) {
        int l = 0;
        final double d7 = 1.0 / d6;
        int i2 = -1;
        final boolean flag = false;
        final boolean flag2 = false;
        final boolean flag3 = false;
        final boolean flag4 = false;
        final boolean flag5 = false;
        final boolean flag6 = false;
        double d8 = 0.0;
        double d9 = 0.0;
        double d10 = 0.0;
        double d11 = 0.0;
        for (int l2 = 0; l2 < i; ++l2) {
            double d12 = (d + l2) * d3 + this.xCoord;
            int i3 = (int)d12;
            if (d12 < i3) {
                --i3;
            }
            final int j2 = i3 & 0xFF;
            d12 -= i3;
            final double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0 - 15.0) + 10.0);
            for (int k2 = 0; k2 < k; ++k2) {
                double d14 = (d2 + k2) * d5 + this.zCoord;
                int l3 = (int)d14;
                if (d14 < l3) {
                    --l3;
                }
                final int i4 = l3 & 0xFF;
                d14 -= l3;
                final double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0 - 15.0) + 10.0);
                for (int j3 = 0; j3 < j; ++j3) {
                    double d16 = (d1 + j3) * d4 + this.yCoord;
                    int k3 = (int)d16;
                    if (d16 < k3) {
                        --k3;
                    }
                    final int l4 = k3 & 0xFF;
                    d16 -= k3;
                    final double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0 - 15.0) + 10.0);
                    if (j3 == 0 || l4 != i2) {
                        i2 = l4;
                        final int j4 = this.permutations[j2] + l4;
                        final int k4 = this.permutations[j4] + i4;
                        final int l5 = this.permutations[j4 + 1] + i4;
                        final int i5 = this.permutations[j2 + 1] + l4;
                        final int j5 = this.permutations[i5] + i4;
                        final int k5 = this.permutations[i5 + 1] + i4;
                        d8 = this.lerp(d13, this.grad(this.permutations[k4], d12, d16, d14), this.grad(this.permutations[j5], d12 - 1.0, d16, d14));
                        d9 = this.lerp(d13, this.grad(this.permutations[l5], d12, d16 - 1.0, d14), this.grad(this.permutations[k5], d12 - 1.0, d16 - 1.0, d14));
                        d10 = this.lerp(d13, this.grad(this.permutations[k4 + 1], d12, d16, d14 - 1.0), this.grad(this.permutations[j5 + 1], d12 - 1.0, d16, d14 - 1.0));
                        d11 = this.lerp(d13, this.grad(this.permutations[l5 + 1], d12, d16 - 1.0, d14 - 1.0), this.grad(this.permutations[k5 + 1], d12 - 1.0, d16 - 1.0, d14 - 1.0));
                    }
                    final double d18 = this.lerp(d17, d8, d9);
                    final double d19 = this.lerp(d17, d10, d11);
                    final double d20 = this.lerp(d15, d18, d19);
                    final int n = l++;
                    ad[n] += d20 * d7;
                }
            }
        }
    }
}
