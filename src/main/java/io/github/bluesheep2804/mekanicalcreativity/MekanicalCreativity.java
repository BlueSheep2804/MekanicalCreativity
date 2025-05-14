package io.github.bluesheep2804.mekanicalcreativity;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import io.github.bluesheep2804.mekanicalcreativity.data.MekanicalCreativityDataGen;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlockEntities;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlocks;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(MekanicalCreativity.MODID)
public class MekanicalCreativity {

    public static final String MODID = "mekanical_creativity";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mekanical_creativity")).icon(() -> MekCreBlocks.OSMIUM_CASING.asItem().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MekCreBlocks.MECHANICAL_INFUSER.asItem());
        output.accept(MekCreBlocks.OSMIUM_CASING.asItem());
        output.accept(MekCreItems.OSMIUM_SHEET);
    }).build());

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public MekanicalCreativity(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, MekanicalCreativityDataGen::gatherData);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        REGISTRATE.registerEventListeners(modEventBus);
        MekCreBlocks.register();
        MekCreBlockEntities.register();
        MekCreItems.register();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> MekanicalCreativityClient::init);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
