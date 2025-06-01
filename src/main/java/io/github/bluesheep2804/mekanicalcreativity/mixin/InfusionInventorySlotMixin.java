package io.github.bluesheep2804.mekanicalcreativity.mixin;

import mekanism.api.IContentsListener;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.common.inventory.slot.chemical.InfusionInventorySlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;
import java.util.function.Supplier;

@Mixin(InfusionInventorySlot.class)
public interface InfusionInventorySlotMixin {
    @Invoker("<init>")
    static InfusionInventorySlot init(IInfusionTank infusionTank, Supplier<Level> worldSupplier, Predicate<@NotNull ItemStack> canExtract, Predicate<@NotNull ItemStack> canInsert, Predicate<@NotNull ItemStack> validator, @Nullable IContentsListener listener, int x, int y) {
        throw new AssertionError();
    }
}
