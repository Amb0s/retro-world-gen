package ambos.retroworldgen.noise.unused;

import ambos.retroworldgen.noise.unused.InfdevImprovedNoise;
import net.minecraft.core.world.noise.BasePerlinNoise;

import java.util.Random;

public class InfdevPerlinNoise extends BasePerlinNoise<InfdevImprovedNoise> {
    public InfdevPerlinNoise(long seed, int levels) {
        super(seed, levels);
    }

    public InfdevPerlinNoise(long seed, int levels, int preLevels) {
        super(seed, levels, preLevels);
    }

    protected InfdevImprovedNoise[] newNoiseLevels(Random random, int numLevels) {
        InfdevImprovedNoise[] levels = new InfdevImprovedNoise[numLevels];

        for(int i = 0; i < numLevels; ++i) {
            levels[i] = new InfdevImprovedNoise(random);
        }

        return levels;
    }
}