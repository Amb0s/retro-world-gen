package ambos.retroworldgen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.world.generate.chunk.indev.IndevWorldTheme;
import net.minecraft.core.world.generate.chunk.indev.IndevWorldType;
import net.minecraft.core.world.generate.chunk.indev.LevelGenerator;

@Mixin(value = LevelGenerator.class, remap = false)
public class LevelGeneratorMixin {
    @Shadow @Mutable private IndevWorldType worldType;
    @Shadow @Mutable private IndevWorldTheme worldTheme;
    @Shadow @Mutable private int sizeX;
    @Shadow @Mutable private int sizeY;
    @Shadow @Mutable private int sizeZ;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void changeWorldSettings(int sizeX, int sizeY, int sizeZ, IndevWorldType worldType, IndevWorldTheme worldTheme, CallbackInfo ci) {
        this.sizeX = 1024;
        this.sizeY = 127;
        this.sizeZ = 1024;
        this.worldType = IndevWorldType.ISLAND;
        this.worldTheme = IndevWorldTheme.NORMAL;
    }
}
