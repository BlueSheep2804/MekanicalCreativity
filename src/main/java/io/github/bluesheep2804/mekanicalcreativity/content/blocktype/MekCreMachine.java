package io.github.bluesheep2804.mekanicalcreativity.content.blocktype;

import java.util.EnumSet;
import java.util.function.Supplier;

import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativityLang;
import mekanism.api.Upgrade;
import mekanism.common.block.attribute.AttributeFactoryType;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.AttributeStateFacing;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.AttributeUpgradeable;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.FactoryType;
import mekanism.common.lib.math.Pos3D;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.tier.FactoryTier;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

// This MekCreMachine is based on Mekanism's Machine class.
// https://github.com/mekanism/Mekanism/blob/95b03e838aa94128596db42154a1527048372999/src/main/java/mekanism/common/content/blocktype/Machine.java
public class MekCreMachine<TILE extends TileEntityMekanism> extends BlockTypeTile<TILE> {

    public MekCreMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, MekanicalCreativityLang description) {
        super(tileEntityRegistrar, description);
        // add default particle effects
        add(new AttributeParticleFX()
                .add(ParticleTypes.SMOKE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52))
                .add(DustParticleOptions.REDSTONE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, 0.52)));
        add(Attributes.ACTIVE_LIGHT, new AttributeStateFacing(), Attributes.INVENTORY, Attributes.SECURITY, Attributes.REDSTONE, Attributes.COMPARATOR);
        add(new AttributeUpgradeSupport(EnumSet.of(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING)));
    }

    public static class FactoryMachine<TILE extends TileEntityMekanism> extends MekCreMachine<TILE> {

        public FactoryMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntitySupplier, MekanicalCreativityLang description, FactoryType factoryType) {
            super(tileEntitySupplier, description);
            add(new AttributeFactoryType(factoryType), new AttributeUpgradeable(() -> MekanismBlocks.getFactory(FactoryTier.BASIC, getFactoryType())));
        }

        public FactoryType getFactoryType() {
            return get(AttributeFactoryType.class).getFactoryType();
        }
    }

    public static class MachineBuilder<MACHINE extends MekCreMachine<TILE>, TILE extends TileEntityMekanism, T extends MachineBuilder<MACHINE, TILE, T>> extends BlockTileBuilder<MACHINE, TILE, T> {

        protected MachineBuilder(MACHINE holder) {
            super(holder);
        }

        public static <TILE extends TileEntityMekanism> MachineBuilder<MekCreMachine<TILE>, TILE, ?> createMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar, MekanicalCreativityLang description) {
            return new MachineBuilder<>(new MekCreMachine<>(tileEntityRegistrar, description));
        }

        public static <TILE extends TileEntityMekanism> MachineBuilder<FactoryMachine<TILE>, TILE, ?> createFactoryMachine(Supplier<TileEntityTypeRegistryObject<TILE>> tileEntityRegistrar,
                                                                                                                           MekanicalCreativityLang description, FactoryType factoryType) {
            return new MachineBuilder<>(new FactoryMachine<>(tileEntityRegistrar, description, factoryType));
        }
    }
}