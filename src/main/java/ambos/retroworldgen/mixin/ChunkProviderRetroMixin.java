package ambos.retroworldgen.mixin;

import ambos.retroworldgen.ChunkProviderRetro2;
import ambos.retroworldgen.ChunkProviderRetro3;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkProviderRetro.class, remap = false)
final class ChunkProviderRetroMixin {
    @Shadow
    private Random rand;
    @Shadow
    private World worldObj;
    @Shadow
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private static final int TREE_DENSITY_MULTIPLIER_INFDEV = 3;
    private static final int TREE_DENSITY_MULTIPLIER_INDEV = 5;

    @Redirect(method = "populate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 28))
    private int onNextRandomInt(Random random, int i) {
        if (((Object)this) instanceof ChunkProviderRetro2) {
            return 0; // Only generates big trees.
        }

        if (((Object)this) instanceof ChunkProviderRetro3) {
            return -1; // Only generates small trees.
        }

        return random.nextInt(i);
    }

    @Inject(method = "populate", at = @At(value = "FIELD", target = "Lnet/minecraft/src/BlockSand;fallInstantly:Z",
            ordinal = 1))
    private void increaseTreeDensity(IChunkProvider ichunkprovider, int i, int j, CallbackInfo ci) {
        if (((Object)this) instanceof ChunkProviderRetro2 || ((Object)this) instanceof ChunkProviderRetro3) {
            /* Initialize variables */
            int k, l, treeNoise = (int) ((this.mobSpawnerNoise.func_806_a((double) i * 16 * 0.25,
                    (double) j * 16 * 0.25) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
            Object obj;
            if (((Object)this) instanceof ChunkProviderRetro2) {
                obj = new WorldGenTreeShapeFancy(Block.leavesOakRetro.blockID, Block.logOak.blockID);
            } else {
                obj = new WorldGenTreeShapeDefault(Block.leavesOakRetro.blockID, Block.logOak.blockID, 4);
            }

            /* Get multiplier value */
            int treeDensityMultiplier;

            if (((Object)this) instanceof ChunkProviderRetro2) {
                treeDensityMultiplier = TREE_DENSITY_MULTIPLIER_INFDEV;
            } else {
                treeDensityMultiplier = TREE_DENSITY_MULTIPLIER_INDEV;
            }

            /* Increase tree density */
            for (int m = 0; m < treeNoise * treeDensityMultiplier; ++m) {
                k = i * 16 + this.rand.nextInt(16) + 8;
                l = j * 16 + this.rand.nextInt(16) + 8;
                ((WorldGenerator) ((WorldGenerator) obj)).func_517_a(1.0, 1.0, 1.0);
                ((WorldGenerator) ((WorldGenerator) obj)).generate(this.worldObj, this.rand, k,
                        this.worldObj.getHeightValue(k, l), l);
            }
        }
    }
}
