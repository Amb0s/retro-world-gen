package ambos.retroworldgen.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = GuiCreateWorld.class, remap = false)
final class GuiCreateWorldMixin {
    @Shadow
    private WorldType[] worldTypes;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeAvailableWorldTypeList(GuiScreen guiscreen, CallbackInfo ci) {
        this.worldTypes = Arrays.copyOf(this.worldTypes, this.worldTypes.length + 2);
        this.worldTypes[this.worldTypes.length - 1] = WorldType.worldTypes[15];
        this.worldTypes[this.worldTypes.length - 2] = WorldType.worldTypes[14];
    }
}
