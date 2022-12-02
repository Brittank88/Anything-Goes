package com.brittank88.anything_goes.mixin;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    /**
     * Clamps the enchantment level extracted from the NBT to 1 - Integer.MAX_VALUE by default.
     * <br /> This effectively removes the cap on enchantment levels.
     * @param x The level to clamp.
     * @return The maximum value for clamping.
     */
    @ModifyArg(method = "getEnchantmentLevel(Lnet/minecraft/nbt/CompoundTag;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I"), index = 2)
    private static int getEnchantmentLevel$AnythingGoes$ModifyArg(int x) {
        return Integer.MAX_VALUE;   // TODO: Make this configurable.
    }
}
