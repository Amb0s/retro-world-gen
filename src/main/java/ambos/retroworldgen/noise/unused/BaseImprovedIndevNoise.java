package ambos.retroworldgen.noise.unused;

import net.minecraft.core.util.helper.MathHelper;

import java.util.Random;

public class BaseImprovedIndevNoise {
    protected final int[] p = new int[512];
    public final double xo;
    public final double yo;
    public final double zo;

    public BaseImprovedIndevNoise(Random random) {
        this.xo = random.nextDouble() * 256.0;
        this.yo = random.nextDouble() * 256.0;
        this.zo = random.nextDouble() * 256.0;

        int i;
        for(i = 0; i < 256; this.p[i] = i++) {
        }

        for(i = 0; i < 256; ++i) {
            int newI = random.nextInt(256 - i) + i;
            int temp = this.p[i];
            this.p[i] = this.p[newI];
            this.p[newI] = temp;
            this.p[i + 256] = this.p[i];
        }

    }

    public double getValue(double x, double y) {
        return this.getValue(x, y, 0.0);
    }

    public double getValue(double x, double y, double z) {
        double X = x + this.xo;
        double Y = y + this.yo;
        double Z = z + this.zo;
        int Xs = MathHelper.floor_double(X) & 255;
        int Ys = MathHelper.floor_double(Y) & 255;
        int Zs = MathHelper.floor_double(Z) & 255;
        X -= MathHelper.floor_double(X);
        Y -= MathHelper.floor_double(Y);
        Z -= MathHelper.floor_double(Z);
        double u = fade(X);
        double v = fade(Y);
        double w = fade(Z);
        int A = this.p[Xs] + Ys;
        int AA = this.p[A] + Zs;
        A = this.p[A + 1] + Zs;
        Xs = this.p[Xs + 1] + Ys;
        Ys = this.p[Xs] + Zs;
        Xs = this.p[Xs + 1] + Zs;
        return lerp(w, lerp(v, lerp(u, grad(this.p[AA], X, Y, Z), grad(this.p[Ys], X - 1.0D, Y, Z)), lerp(u, grad(this.p[A], X, Y - 1.0D, Z), grad(this.p[Xs], X - 1.0D, Y - 1.0D, Z))), lerp(v, lerp(u, grad(this.p[AA + 1], X, Y, Z - 1.0D), grad(this.p[Ys + 1], X - 1.0D, Y, Z - 1.0D)), lerp(u, grad(this.p[A + 1], X, Y - 1.0D, Z - 1.0D), grad(this.p[Xs + 1], X - 1.0D, Y - 1.0D, Z - 1.0D))));
    }

    protected double fade(double t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
    }

    protected double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    protected double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : (h != 12 && h != 14 ? z : x);
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
}
