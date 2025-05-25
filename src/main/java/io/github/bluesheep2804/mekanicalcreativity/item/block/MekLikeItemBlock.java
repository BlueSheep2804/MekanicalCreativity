package io.github.bluesheep2804.mekanicalcreativity.item.block;

import mekanism.api.text.TextComponentUtil;
import mekanism.api.tier.ITier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MekLikeItemBlock<BLOCK extends Block> extends BlockItem {
    public MekLikeItemBlock(BLOCK pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @NotNull
    @Override
    public BLOCK getBlock() {
        return (BLOCK) super.getBlock();
    }

    public ITier getTier() {
        return null;
    }

    @NotNull
    @Override
    public Component getName(@NotNull ItemStack pStack) {
        ITier tier = getTier();
        if (tier == null)
            return super.getName(pStack);
        return TextComponentUtil.build(tier.getBaseTier().getColor(), super.getName(pStack));
    }
}
