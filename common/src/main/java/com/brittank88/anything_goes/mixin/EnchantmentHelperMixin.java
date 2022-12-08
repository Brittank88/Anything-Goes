package com.brittank88.anything_goes.mixin;

import com.brittank88.anything_goes.AnythingGoes;
import com.brittank88.anything_goes.config.AnythingGoesConfig;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    /**
     * Clamps the enchantment level extracted from the NBT to 1 - <code>Integer.MAX_VALUE</code>.
     * <br /> This effectively removes the cap on enchantment levels.
     * <br /> This behaviour is configurable via <code>overLeveledEnchantmentsPlusPlus</code>.
     * @param max The maximum value to clamp to.
     * @return The maximum value to clamp to.
     */
    @ModifyArg(method = "getEnchantmentLevel(Lnet/minecraft/nbt/CompoundTag;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I"), index = 2)
    private static int getEnchantmentLevel$AnythingGoes(int max) {
        return AnythingGoesConfig.SERVER_CONFIG.UNCAPS.overLeveledEnchantmentsPlusPlus.get() ? Integer.MAX_VALUE : max;
    }
}
