package io.github.bluesheep2804.mekanicalcreativity.registries;

import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorBlockEntity;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.MODID;

public class MekCreContainerTypes {
    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MODID);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<InfuseExtractorBlockEntity>> INFUSE_EXTRACTOR = CONTAINER_TYPES.register("infuse_extractor", InfuseExtractorBlockEntity.class);
}
