package ambos.retroworldgen.mixin;

import ambos.retroworldgen.LevelType;

import ambos.retroworldgen.level.source.AlphaLevelSource;
import ambos.retroworldgen.level.source.IndevLevelSource;
import ambos.retroworldgen.level.source.InfdevLevelSource;
import net.minecraft.level.Level;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.source.SkylandsLevelSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Dimension.class)
final class DimensionMixin {
    @Shadow
    public Level level;

    @Inject(method = "createLevelSource", at = @At("HEAD"), cancellable = true)
    private void onGetChunkProvider(CallbackInfoReturnable cir) {
        switch (LevelType.selected) {
            case ALPHA:
                cir.setReturnValue(new AlphaLevelSource(level, level.getSeed()));
                break;

            case INDEV_INLAND:
                cir.setReturnValue(new IndevLevelSource(level, level.getSeed()));
                break;

            case INFDEV:
                cir.setReturnValue(new InfdevLevelSource(level, level.getSeed()));
                break;

            case FLOATING_ISLANDS:
                cir.setReturnValue(new SkylandsLevelSource(level, level.getSeed()));
                break;

            default:
                break;
        }
    }
}
