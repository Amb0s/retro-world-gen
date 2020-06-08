package ambos.retroworldgen.mixin;

import ambos.retroworldgen.LevelType;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.CreateLevelScreen;
import net.minecraft.client.gui.widgets.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreateLevelScreen.class)
final class CreateLevelScreenMixin extends Screen {
    @Inject(method = "init", at = @At("RETURN"), cancellable = true)
    private void onInitGui(CallbackInfo ci) {
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 96 + 6, "World Type: " + LevelType.selected));
    }

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widgets/Button;<init>(IIILjava/lang/String;)V"))
    public void changeButtonPositions(Args args) {
        if (args.get(0) == Integer.valueOf(0)) {
            args.set(2, this.height / 4 + 120 + 12);
        }

        if (args.get(0) == Integer.valueOf(1)) {
            args.set(2, this.height / 4 + 120 + 36);
        }
    }

    @Inject(method = "buttonClicked", at = @At("RETURN"), cancellable = true)
    private void onButtonClicked(Button arg, CallbackInfo ci) {
        if (arg.id == 2) {
            LevelType.selected = LevelType.selected.next();
            arg.text = "World Type: " + LevelType.selected;
        }
    }
}
