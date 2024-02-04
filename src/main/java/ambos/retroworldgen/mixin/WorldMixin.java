package ambos.retroworldgen.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.type.WorldType;

@Mixin(value = World.class, remap = false)
public class WorldMixin {
    @Inject(method = "getSkyColor", at = @At("RETURN"), cancellable = true)
    private void changeSkyColor(ICamera camera, float partialTick, CallbackInfoReturnable<Vec3d> cir) {
        if (((World) ((Object) this)).worldType.getLanguageKey().equals("worldType.overworld.indev")) {
            float f1 = ((World) ((Object) this)).getCelestialAngle(partialTick);
            float f2 = MathHelper.cos(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
            int x = MathHelper.floor_double(camera.getX(partialTick));
            int y = MathHelper.floor_double(camera.getY(partialTick));
            int z = MathHelper.floor_double(camera.getZ(partialTick));
            float blockTemperature = (float)((World) ((Object) this)).getBlockTemperature(x, z);
            int skyColor = ((World) ((Object) this)).getBiomeProvider().getBiome(x, y, z).getSkyColor(blockTemperature);
            float r = (float)(skyColor >> 16 & 255) / 255.0F;
            float g = (float)(skyColor >> 8 & 255) / 255.0F;
            float b = (float)(skyColor & 255) / 255.0F;
            r *= f2;
            g *= f2;
            b *= f2;
            float f7 = 0.5F;
            float f12 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
            float f10 = 1.0F - f7 * 0.75F;
            r = r * f10 + f12 * (1.0F);
            g = g * f10 + f12 * (1.0F);
            b = b * f10 + f12 * (1.0F);
            /*r = r * f10 + f12 * (1.0F - f10);
            g = g * f10 + f12 * (1.0F - f10);
            b = b * f10 + f12 * (1.0F - f10); */

            cir.setReturnValue(Vec3d.createVector((double)r, (double)g, (double)b));

        }
    }

    /*@Redirect(method = "updateSkyBrightness", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/core/world/type/WorldType;getSkyDarken(Lnet/minecraft/core/world/World;JF)I", ordinal = 0))
    private int changeSkyBrightness(WorldType worldType, World world, long time, float f) {
        if (((World) ((Object) this)).worldType.getLanguageKey().equals("worldType.overworld.indev")) {
            return ((World) ((Object) this)).worldType.getSkyDarken(((World) ((Object) this)), ((World) ((Object) this)).getWorldTime(), 1.0F) - 10;
        }

        return ((World) ((Object) this)).worldType.getSkyDarken(((World) ((Object) this)), ((World) ((Object) this)).getWorldTime(), 1.0F);
    }*/
}
