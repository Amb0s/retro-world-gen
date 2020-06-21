package ambos.retroworldgen.mixin;

import ambos.retroworldgen.LevelTypeAccessor;
import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
final class LevelPropertiesMixin implements LevelTypeAccessor {
    private int levelType;

    @Inject(method = "<init>", at = @At("RETURN"), cancellable = true)
    private void constructor(CompoundTag arg, CallbackInfo cbi) {
        levelType = arg.getInt("LevelType");
    }

    @Inject(method = "updateProperties", at = @At("HEAD"), cancellable = true)
    private void onUpdateProperties(CompoundTag arg, CompoundTag arg1, CallbackInfo cbi) {
        arg.put("LevelType", levelType);
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int i) {
        levelType = i;
    }
}
