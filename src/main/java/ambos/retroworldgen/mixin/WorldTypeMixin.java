package ambos.retroworldgen.mixin;

import ambos.retroworldgen.ChunkProviderRetro2;
import ambos.retroworldgen.ChunkProviderRetro3;
import ambos.retroworldgen.ChunkProviderRetro4;
import net.minecraft.src.WorldProviderGeneric;
import net.minecraft.src.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldType.class, remap = false)
final class WorldTypeMixin {
    @Shadow
    public static WorldType[] worldTypes;
    private static WorldType overworldRetro2;
    private static WorldType overworldRetro3;

    private static WorldType overworldRetro4;

    @Inject(method = "<clinit>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/src/WorldType;<init>(ILjava/lang/String;)V", ordinal = 0))
    private static void changeWorldTypeListLength(CallbackInfo ci) {
        worldTypes = new WorldType[17]; // Called before the first world type instantiation.
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void changeWorldTypeList(CallbackInfo ci) {
        overworldRetro2 = (new WorldType(14, "retro2")).setLanguageKey("worldType.retro2")
                .setWorldProvider(new WorldProviderGeneric(ChunkProviderRetro2.class)).setSeasonList();
        overworldRetro3 = (new WorldType(15, "retro3")).setLanguageKey("worldType.retro3")
                .setWorldProvider(new WorldProviderGeneric(ChunkProviderRetro3.class)).setSeasonList();
        overworldRetro4 = (new WorldType(16, "retro4")).setLanguageKey("worldType.retro4")
                .setWorldProvider(new WorldProviderGeneric(ChunkProviderRetro4.class)).setSeasonList();
    }
}
