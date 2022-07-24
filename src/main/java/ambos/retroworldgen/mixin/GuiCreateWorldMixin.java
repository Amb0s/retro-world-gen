package ambos.retroworldgen.mixin;

import ambos.retroworldgen.ChunkProviderRetro2;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiCreateWorld.class, remap = false)
public class GuiCreateWorldMixin {
    @Shadow
    private WorldType[] worldTypes;

    private WorldType overworldRetro2 = (new WorldType(7, "retro")).setLanguageKey("worldType.retro").setWorldProvider(new WorldProviderGeneric(ChunkProviderRetro2.class)).setSeasonList();


    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeWorlTypeList(GuiScreen guiscreen, CallbackInfo ci){
        this.worldTypes = new WorldType[]{WorldType.overworldDeeper, WorldType.overworld, WorldType.overworldWinter, WorldType.overworldIslands, WorldType.overworldFlat, WorldType.overworldHell, WorldType.overworldWoods, WorldType.overworldParadise, overworldRetro2};
    }
}
