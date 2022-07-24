package ambos.retroworldgen.noise;

import net.minecraft.src.NoiseGenerator;

import java.util.Random;

public class InfdevNoiseGeneratorPerlin extends NoiseGenerator {
    private int[] permutations = new int[512];
    public double xOffset;
    public double yOffset;
    public double zOffset;

    public InfdevNoiseGeneratorPerlin() {
        this(new Random());
    }

    public InfdevNoiseGeneratorPerlin(Random random) {
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
        double d4 = d + this.xOffset;
        double d5 = d1 + this.yOffset;
        double d6 = d2 + this.zOffset;
        d = (int) d4;
        int i = (int) d5;
        d1 = (int) d6;
        if (d4 < d) {
            --d;
        }
        if (d5 < i) {
            --i;
        }
        if (d6 < d1) {
            --d1;
        }
        int j = (int) d & 0xFF;
        d2 = (i & 0xFF);
        int k = (int) d1 & 0xFF;
        d4 -= d;
        d5 -= i;
        d6 -= d1;
        double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0 - 15.0) + 10.0);
        double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0 - 15.0) + 10.0);
        double d9 = d6 * d6 * d6 * (d6 * (d6 * 6.0 - 15.0) + 10.0);
        d = this.permutations[j] + d2;
        i = this.permutations[(int) d] + k;
        d = this.permutations[(int) d + 1] + k;
        d1 = this.permutations[j + 1] + d2;
        j = this.permutations[(int) d1] + k;
        d1 = this.permutations[(int) d1 + 1] + k;
        return this.method_1203(d9, this.method_1203(d8, this.method_1203(d7, this.method_1201(this.permutations[i], d4, d5, d6), this.method_1201(this.permutations[j], d4 - 1.0, d5, d6)), this.method_1203(d7, this.method_1201(this.permutations[(int) d], d4, d5 - 1.0, d6), this.method_1201(this.permutations[(int) d1], d4 - 1.0, d5 - 1.0, d6))), this.method_1203(d8, this.method_1203(d7, this.method_1201(this.permutations[i + 1], d4, d5, d6 - 1.0), this.method_1201(this.permutations[j + 1], d4 - 1.0, d5, d6 - 1.0)), this.method_1203(d7, this.method_1201(this.permutations[(int) d + 1], d4, d5 - 1.0, d6 - 1.0), this.method_1201(this.permutations[(int) d1 + 1], d4 - 1.0, d5 - 1.0, d6 - 1.0))));
    }

    public final double method_1203(double d, double d1, double d2) {
        return d1 + d * (d2 - d1);
    }

    public final double method_1201(int i, double d, double d1, double d2) {
        double d4 = ((i &= 0xF) >= 8) ? d1 : d;
        double d5 = (i >= 4) ? ((i != 12 && i != 14) ? d2 : d) : d1;
        return (((i & 0x1) != 0x0) ? (-d4) : d4) + (((i & 0x2) != 0x0) ? (-d5) : d5);
    }

    public double method_1204(double d, double d1) {
        return this.sample(d, d1, 0.0);
    }

    public double method_1204(double d, double d1, double d2) {
        return this.sample(d, d1, d2);
    }

    public void sample(double[] array, int i, int j, int k, int l, int i1, int j1, double d1, double d2, double d3, double d4) {
        int l2 = 0;
        double d5 = 1.0 / d4;
        d4 = -1.0;
        double d6 = 0.0;
        double d7 = 0.0;
        double d8 = 0.0;
        double d9 = 0.0;
        for (int l3 = 0; l3 < l; ++l3) {
            double d10;
            int k2 = (int) (d10 = (i + l3) * d1 + this.xOffset);
            if (d10 < k2) {
                --k2;
            }
            int i2 = k2 & 0xFF;
            double d11 = (d10 -= k2) * d10 * d10 * (d10 * (d10 * 6.0 - 15.0) + 10.0);
            for (int j2 = 0; j2 < j1; ++j2) {
                double d12;
                k2 = (int) (d12 = (k + j2) * d3 + this.zOffset);
                if (d12 < k2) {
                    --k2;
                }
                int k3 = k2 & 0xFF;
                double d13 = (d12 -= k2) * d12 * d12 * (d12 * (d12 * 6.0 - 15.0) + 10.0);
                for (int l4 = 0; l4 < i1; ++l4) {
                    double d14;
                    k2 = (int) (d14 = (j + l4) * d2 + this.yOffset);
                    if (d14 < k2) {
                        --k2;
                    }
                    int j3 = k2 & 0xFF;
                    double d15 = (d14 -= k2) * d14 * d14 * (d14 * (d14 * 6.0 - 15.0) + 10.0);
                    if (l4 == 0 || j3 != d4) {
                        d4 = j3;
                        k2 = this.permutations[i2] + j3;
                        int i3 = this.permutations[k2] + k3;
                        k2 = this.permutations[k2 + 1] + k3;
                        j3 += this.permutations[i2 + 1];
                        int k4 = this.permutations[j3] + k3;
                        j3 = this.permutations[j3 + 1] + k3;
                        d6 = this.method_1203(d11, this.method_1201(this.permutations[i3], d10, d14, d12), this.method_1201(this.permutations[k4], d10 - 1.0, d14, d12));
                        d7 = this.method_1203(d11, this.method_1201(this.permutations[k2], d10, d14 - 1.0, d12), this.method_1201(this.permutations[j3], d10 - 1.0, d14 - 1.0, d12));
                        d8 = this.method_1203(d11, this.method_1201(this.permutations[i3 + 1], d10, d14, d12 - 1.0), this.method_1201(this.permutations[k4 + 1], d10 - 1.0, d14, d12 - 1.0));
                        d9 = this.method_1203(d11, this.method_1201(this.permutations[k2 + 1], d10, d14 - 1.0, d12 - 1.0), this.method_1201(this.permutations[j3 + 1], d10 - 1.0, d14 - 1.0, d12 - 1.0));
                    }
                    double d16 = this.method_1203(d15, d6, d7);
                    double d17 = this.method_1203(d15, d8, d9);
                    double d18 = this.method_1203(d13, d16, d17);
                    int n = l2++;
                    array[n] += d18 * d5;
                }
            }
        }
    }
}
