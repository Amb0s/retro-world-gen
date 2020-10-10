package ambos.retroworldgen.mixin;

import ambos.retroworldgen.LevelType;

import ambos.retroworldgen.LevelTypeAccessor;
import ambos.retroworldgen.level.source.AlphaLevelSource;
import ambos.retroworldgen.level.source.FloatingIslandsLevelSource;
import ambos.retroworldgen.level.source.InlandLevelSource;
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
final class DimensionMixin  {
    @Shadow
    public Level level;

    @Inject(method = "createLevelSource", at = @At("HEAD"), cancellable = true)
    private void onCreateLevelSource(CallbackInfoReturnable cir) {
        LevelType levelType = level.field_215 ? LevelType.selected : LevelType.values()[((LevelTypeAccessor) level.getProperties()).getLevelType()];

        switch (levelType) {
            case ALPHA:
                cir.setReturnValue(new AlphaLevelSource(level, level.getSeed()));
                break;
            /*
            case INLAND:
                cir.setReturnValue(new InlandLevelSource(level, level.getSeed()));
                break;

            case FLOATING_ISLANDS:
                cir.setReturnValue(new FloatingIslandsLevelSource(level, level.getSeed()));
                break;
            */
            case INFDEV:
                cir.setReturnValue(new InfdevLevelSource(level, level.getSeed()));
                break;

            case SKYLANDS:
                cir.setReturnValue(new SkylandsLevelSource(level, level.getSeed()));
                break;

            default:
                break;
        }

        ((LevelTypeAccessor) level.getProperties()).setLevelType(levelType.ordinal());
    }
}
