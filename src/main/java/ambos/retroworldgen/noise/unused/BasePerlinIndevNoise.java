package ambos.retroworldgen.noise.unused;

import ambos.retroworldgen.noise.unused.BaseImprovedIndevNoise;
import net.minecraft.core.world.noise.SurfaceNoise;

import java.util.Random;

public class BasePerlinIndevNoise extends SurfaceNoise {
    private final BaseImprovedIndevNoise[] noiseLevels;
    private final int levels;

    public BasePerlinIndevNoise(Random random, int levels) {
        this.levels = levels;
        this.noiseLevels = new BaseImprovedIndevNoise[levels];

        for(int i = 0; i < levels; ++i) {
            this.noiseLevels[i] = new BaseImprovedIndevNoise(random);
        }
    }

    public double getValue(double d1, double d3) {
        double d5 = 0.0D;
        double d7 = 1.0D;

        for(int i9 = 0; i9 < this.levels; ++i9) {
            d5 += this.noiseLevels[i9].getValue(d1 / d7, d3 / d7) * d7;
            d7 *= 2.0D;
        }

        return d5;
    }

    public double getValue(double d1, double d3, double d5) {
        double d7 = 0.0D;
        double d9 = 1.0D;

        for(int i11 = 0; i11 < this.levels; ++i11) {
            d7 += this.noiseLevels[i11].getValue(d1 / d9, d3 / d9, d5 / d9) * d9;
            d9 *= 2.0D;
        }

        return d7;
    }
}
