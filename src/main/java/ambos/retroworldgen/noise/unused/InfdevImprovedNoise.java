package ambos.retroworldgen.noise.unused;

import net.minecraft.core.world.noise.BaseImprovedNoise;

import java.util.Random;

public class InfdevImprovedNoise extends BaseImprovedNoise {
    public InfdevImprovedNoise(Random random) {
        super(random);
    }

    @Override
    public void add(double[] arr, double x, double y, double z, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale, double levelScale) {
        double d4 = x + this.xo;
        double d5 = y + this.yo;
        double d6 = z + this.zo;
        x = (int) d4;
        int i = (int) d5;
        y = (int) d6;
        if (d4 < x) {
            --x;
        }
        if (d5 < i) {
            --i;
        }
        if (d6 < y) {
            --y;
        }
        int j = (int) x & 0xFF;
        z = (i & 0xFF);
        int k = (int) y & 0xFF;
        d4 -= x;
        d5 -= i;
        d6 -= y;
        double d7 = d4 * d4 * d4 * (d4 * (d4 * 6.0 - 15.0) + 10.0);
        double d8 = d5 * d5 * d5 * (d5 * (d5 * 6.0 - 15.0) + 10.0);
        double d9 = d6 * d6 * d6 * (d6 * (d6 * 6.0 - 15.0) + 10.0);
        x = this.p[j] + z;
        i = this.p[(int) x] + k;
        x = this.p[(int) x + 1] + k;
        y = this.p[j + 1] + z;
        j = this.p[(int) y] + k;
        y = this.p[(int) y + 1] + k;
        /*return this.lerp(d9, this.lerp(d8, this.lerp(d7, this.grad(this.p[i], d4, d5, d6),
                                this.grad(this.p[j], d4 - 1.0, d5, d6)),
                        this.lerp(d7, this.grad(this.p[(int) x], d4, d5 - 1.0, d6),
                                this.grad(this.p[(int) y], d4 - 1.0, d5 - 1.0, d6))),
                this.lerp(d8, this.lerp(d7, this.grad(this.p[i + 1], d4, d5, d6 - 1.0),
                                this.grad(this.p[j + 1], d4 - 1.0, d5, d6 - 1.0)),
                        this.lerp(d7, this.grad(this.p[(int) x + 1], d4, d5 - 1.0, d6 - 1.0),
                                this.grad(this.permutations[(int) y + 1], d4 - 1.0, d5 - 1.0, d6 - 1.0))));*/
    }

    @Override
    protected double grad(int hash, double x, double y, double z) {
        double d4 = ((hash &= 0xF) >= 8) ? y : x;
        double d5 = (hash >= 4) ? ((hash != 12 && hash != 14) ? z : x) : y;
        return (((hash & 0x1) != 0x0) ? (-d4) : d4) + (((hash & 0x2) != 0x0) ? (-d5) : d5);
    }
}
