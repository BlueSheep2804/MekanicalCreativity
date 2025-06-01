package io.github.bluesheep2804.mekanicalcreativity.client;

import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity;
import io.github.bluesheep2804.mekanicalcreativity.client.gui.GuiInfuseExtractor;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreContainerTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = MekanicalCreativity.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainer(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(MekCreContainerTypes.INFUSE_EXTRACTOR, GuiInfuseExtractor::new);
        });
    }
}
