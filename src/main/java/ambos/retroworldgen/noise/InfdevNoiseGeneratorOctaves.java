package ambos.retroworldgen.noise;

import net.minecraft.src.NoiseGenerator;

import java.util.Random;

public class InfdevNoiseGeneratorOctaves extends NoiseGenerator {
    private InfdevNoiseGeneratorPerlin[] generatorCollection;
    private int octaves;

    public InfdevNoiseGeneratorOctaves(Random random, int i) {
        this.octaves = i;
        this.generatorCollection = new InfdevNoiseGeneratorPerlin[i];
        for (int j = 0; j < i; ++j) {
            this.generatorCollection[j] = new InfdevNoiseGeneratorPerlin(random);
        }
    }

    public double generateNoise(double d, double d1, double d2) {
        double d3 = 0.0;
        double d4 = 1.0;
        for (int i = 0; i < this.octaves; ++i) {
            d3 += this.generatorCollection[i].sample2(d * d4, d1 * d4, d2 * d4) / d4;
            d4 /= 2.0;
        }
        return d3;
    }
}
