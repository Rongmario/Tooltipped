package xyz.risingkingdom.tooltipped.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.risingkingdom.tooltipped.Tooltipped;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    //if isAdvanced ->
    //@Inject(method = "getTooltip", at = @At(value = "JUMP", opcode = Opcodes.IFNE, ordinal = 0))
    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void injectTooltips(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> ci) {
        Tooltipped.injectTooltip(this, context, ci.getReturnValue());
    }

}
