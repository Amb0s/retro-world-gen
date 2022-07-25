package ambos.retroworldgen.noise;

import net.minecraft.src.NoiseGenerator;

import java.util.Random;

public class InfdevNoiseGeneratorPerlin extends NoiseGenerator {
    private int[] permutations;
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public InfdevNoiseGeneratorPerlin(Random random) {
        this.permutations = new int[512];
        this.xCoord = random.nextDouble() * 256.0;
        this.yCoord = random.nextDouble() * 256.0;
        this.zCoord = random.nextDouble() * 256.0;
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
        double d4 = d + this.xCoord;
        double d5 = d1 + this.yCoord;
        double d6 = d2 + this.zCoord;
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
        return this.lerp(d9, this.lerp(d8, this.lerp(d7, this.grad(this.permutations[i], d4, d5, d6),
                this.grad(this.permutations[j], d4 - 1.0, d5, d6)),
                this.lerp(d7, this.grad(this.permutations[(int) d], d4, d5 - 1.0, d6),
                        this.grad(this.permutations[(int) d1], d4 - 1.0, d5 - 1.0, d6))),
                this.lerp(d8, this.lerp(d7, this.grad(this.permutations[i + 1], d4, d5, d6 - 1.0),
                        this.grad(this.permutations[j + 1], d4 - 1.0, d5, d6 - 1.0)),
                        this.lerp(d7, this.grad(this.permutations[(int) d + 1], d4, d5 - 1.0, d6 - 1.0),
                                this.grad(this.permutations[(int) d1 + 1], d4 - 1.0, d5 - 1.0, d6 - 1.0))));
    }

    public final double lerp(double d, double d1, double d2) {
        return d1 + d * (d2 - d1);
    }

    public final double grad(int i, double d, double d1, double d2) {
        double d4 = ((i &= 0xF) >= 8) ? d1 : d;
        double d5 = (i >= 4) ? ((i != 12 && i != 14) ? d2 : d) : d1;
        return (((i & 0x1) != 0x0) ? (-d4) : d4) + (((i & 0x2) != 0x0) ? (-d5) : d5);
    }

    public double sample2(double d, double d1, double d2) {
        return this.sample(d, d1, d2);
    }
}
