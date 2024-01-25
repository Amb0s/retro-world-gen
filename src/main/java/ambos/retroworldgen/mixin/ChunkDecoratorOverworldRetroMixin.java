package ambos.retroworldgen.mixin;

import ambos.retroworldgen.world.type.WorldTypeOverworldAmplifiedIndev;
import ambos.retroworldgen.world.type.WorldTypeOverworldInfdev;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.overworld.retro.ChunkDecoratorOverworldRetro;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeFancy;
import net.minecraft.core.world.noise.RetroPerlinNoise;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = ChunkDecoratorOverworldRetro.class, remap = false)
final class ChunkDecoratorOverworldRetroMixin {
    @Shadow
    private World world;
    @Shadow @Final private RetroPerlinNoise treeDensityNoise;
    private static final int TREE_DENSITY_MULTIPLIER_INFDEV = 3;
    private static final int TREE_DENSITY_MULTIPLIER_INDEV = 5;

    @Redirect(method = "decorate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 28))
    private int onNextRandomInt(Random random, int i) {
        if (world.getWorldType() instanceof WorldTypeOverworldInfdev) {
            return 0; // Only generates big trees.
        }

        if (world.getWorldType() instanceof WorldTypeOverworldAmplifiedIndev) {
            return -1; // Only generates small trees.
        }

        return random.nextInt(i);
    }

    @Inject(method = "decorate", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/BlockSand;fallInstantly:Z", ordinal = 1))
    private void increaseTreeDensity(Chunk chunk, CallbackInfo ci) {
        if (world.getWorldType() instanceof WorldTypeOverworldInfdev || world.getWorldType() instanceof WorldTypeOverworldAmplifiedIndev) {
            /* Initialize variables */
            Random rand = new Random(this.world.getRandomSeed());
            Object obj;
            int k, l;
            int treeNoise = (int) ((this.treeDensityNoise.get((double) chunk.xPosition * 16 * 0.25,
                    (double) chunk.zPosition * 16 * 0.25) / 8.0 +
                    rand.nextDouble() * 4.0 + 4.0) / 3.0);

            if (world.getWorldType() instanceof WorldTypeOverworldAmplifiedIndev) {
                obj = new WorldFeatureTree(Block.leavesOakRetro.id, Block.logOak.id, 4);
            } else {
                obj = new WorldFeatureTreeFancy(Block.leavesOakRetro.id, Block.logOak.id);
            }

            /* Get multiplier value */
            int treeDensityMultiplier;

            if (world.getWorldType() instanceof WorldTypeOverworldInfdev) {
                treeDensityMultiplier = TREE_DENSITY_MULTIPLIER_INFDEV;
            } else {
                treeDensityMultiplier = TREE_DENSITY_MULTIPLIER_INDEV;
            }

            /* Increase tree density */
            for (int m = 0; m < treeNoise * treeDensityMultiplier; ++m) {
                k = chunk.xPosition * 16 + rand.nextInt(16) + 8;
                l = chunk.zPosition * 16 + rand.nextInt(16) + 8;
                ((WorldFeature)obj).func_517_a(1.0, 1.0, 1.0);
                ((WorldFeature)obj).generate(this.world, rand, k, this.world.getHeightValue(k, l), l);
            }
        }
    }
}
