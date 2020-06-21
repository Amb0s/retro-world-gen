package ambos.retroworldgen.util.noise;

import net.minecraft.util.noise.Noise;

import java.util.Random;

public final class AlphaPerlinNoise extends Noise {
    private int[] permutations = new int[512];
    public double xOffset;
    public double yOffset;
    public double zOffset;
    
    public AlphaPerlinNoise() {
        this(new Random());
    }
    
    public AlphaPerlinNoise(Random random) {
        this.xOffset = random.nextDouble() * 256.0;
        this.yOffset = random.nextDouble() * 256.0;
        this.zOffset = random.nextDouble() * 256.0;
        for (int i = 0; i < 256; ++i) {
            this.permutations[i] = i;
        }
        for (int j = 0; j < 256; ++j) {
            int k = random.nextInt(256 - j) + j;
            int l = this.permutations[j];
            this.permutations[j] = this.permutations[k];
            this.permutations[k] = l;
            this.permutations[j + 256] = this.permutations[j];
        }
    }
    
    public double sample(double d, double d1, double d2) {
        double d3 = d + this.xOffset;
        double d4 = d1 + this.yOffset;
        double d5 = d2 + this.zOffset;
        int i = (int) d3;
        int j = (int) d4;
        int k = (int) d5;
        if (d3 < i) {
            --i;
        }
        if (d4 < j) {
            --j;
        }
        if (d5 < k) {
            --k;
        }
        int l = i & 0xFF;
        int i2 = j & 0xFF;
        int j2 = k & 0xFF;
        d3 -= i;
        d4 -= j;
        d5 -= k;
        double d6 = d3 * d3 * d3 * (d3 * (d3 * 6.0 - 15.0) + 10.0);
        double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0 - 15.0) + 10.0);
        double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0 - 15.0) + 10.0);
        int k2 = this.permutations[l] + i2;
        int l2 = this.permutations[k2] + j2;
        int i3 = this.permutations[k2 + 1] + j2;
        int j3 = this.permutations[l + 1] + i2;
        int k3 = this.permutations[j3] + j2;
        int l3 = this.permutations[j3 + 1] + j2;
        return this.method_1203(d8, this.method_1203(d7, this.method_1203(d6, this.method_1201(this.permutations[l2], d3, d4, d5), this.method_1201(this.permutations[k3], d3 - 1.0, d4, d5)), this.method_1203(d6, this.method_1201(this.permutations[i3], d3, d4 - 1.0, d5), this.method_1201(this.permutations[l3], d3 - 1.0, d4 - 1.0, d5))), this.method_1203(d7, this.method_1203(d6, this.method_1201(this.permutations[l2 + 1], d3, d4, d5 - 1.0), this.method_1201(this.permutations[k3 + 1], d3 - 1.0, d4, d5 - 1.0)), this.method_1203(d6, this.method_1201(this.permutations[i3 + 1], d3, d4 - 1.0, d5 - 1.0), this.method_1201(this.permutations[l3 + 1], d3 - 1.0, d4 - 1.0, d5 - 1.0))));
    }
    
    public final double method_1203(double d, double d1, double d2) {
        return d1 + d * (d2 - d1);
    }
    
    public final double method_1201(int i, double d, double d1, double d2) {
        int j = i & 0xF;
        double d3 = (j >= 8) ? d1 : d;
        double d4 = (j >= 4) ? ((j != 12 && j != 14) ? d2 : d) : d1;
        return (((j & 0x1) != 0x0) ? (-d3) : d3) + (((j & 0x2) != 0x0) ? (-d4) : d4);
    }
    
    public double method_1204(double d, double d1) {
        return this.sample(d, d1, 0.0);
    }
    
    public void sample(double[] array, double d, double d1, double d2, int i, int j, int k, double d3, double d4, double d5, double d6) {
        int l = 0;
        double d7 = 1.0 / d6;
        int i2 = -1;
        double d8 = 0.0;
        double d9 = 0.0;
        double d10 = 0.0;
        double d11 = 0.0;
        for (int l2 = 0; l2 < i; ++l2) {
            double d12 = (d + l2) * d3 + this.xOffset;
            int i3 = (int) d12;
            if (d12 < i3) {
                --i3;
            }
            int j2 = i3 & 0xFF;
            d12 -= i3;
            double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0 - 15.0) + 10.0);
            for (int k2 = 0; k2 < k; ++k2) {
                double d14 = (d2 + k2) * d5 + this.zOffset;
                int l3 = (int) d14;
                if (d14 < l3) {
                    --l3;
                }
                int i4 = l3 & 0xFF;
                d14 -= l3;
                double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0 - 15.0) + 10.0);
                for (int j3 = 0; j3 < j; ++j3) {
                    double d16 = (d1 + j3) * d4 + this.yOffset;
                    int k3 = (int) d16;
                    if (d16 < k3) {
                        --k3;
                    }
                    int l4 = k3 & 0xFF;
                    d16 -= k3;
                    double d17 = d16 * d16 * d16 * (d16 * (d16 * 6.0 - 15.0) + 10.0);
                    if (j3 == 0 || l4 != i2) {
                        i2 = l4;
                        int j4 = this.permutations[j2] + l4;
                        int k4 = this.permutations[j4] + i4;
                        int l5 = this.permutations[j4 + 1] + i4;
                        int i5 = this.permutations[j2 + 1] + l4;
                        int j5 = this.permutations[i5] + i4;
                        int k5 = this.permutations[i5 + 1] + i4;
                        d8 = this.method_1203(d13, this.method_1201(this.permutations[k4], d12, d16, d14), this.method_1201(this.permutations[j5], d12 - 1.0, d16, d14));
                        d9 = this.method_1203(d13, this.method_1201(this.permutations[l5], d12, d16 - 1.0, d14), this.method_1201(this.permutations[k5], d12 - 1.0, d16 - 1.0, d14));
                        d10 = this.method_1203(d13, this.method_1201(this.permutations[k4 + 1], d12, d16, d14 - 1.0), this.method_1201(this.permutations[j5 + 1], d12 - 1.0, d16, d14 - 1.0));
                        d11 = this.method_1203(d13, this.method_1201(this.permutations[l5 + 1], d12, d16 - 1.0, d14 - 1.0), this.method_1201(this.permutations[k5 + 1], d12 - 1.0, d16 - 1.0, d14 - 1.0));
                    }
                    double d18 = this.method_1203(d17, d8, d9);
                    double d19 = this.method_1203(d17, d10, d11);
                    double d20 = this.method_1203(d15, d18, d19);
                    int n = l++;
                    array[n] += d20 * d7;
                }
            }
        }
    }
}
