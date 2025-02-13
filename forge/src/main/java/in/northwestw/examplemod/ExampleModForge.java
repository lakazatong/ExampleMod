package in.northwestw.examplemod;

import in.northwestw.examplemod.client.TruthAssignerScreen;
import in.northwestw.examplemod.platform.ForgeRegistryHelper;
import in.northwestw.examplemod.registries.BlockEntities;
import in.northwestw.examplemod.registries.Blocks;
import in.northwestw.examplemod.registries.Menus;
import in.northwestw.examplemod.registries.blockentityrenderers.CircuitBlockEntityRenderer;
import in.northwestw.examplemod.registries.blockentityrenderers.IntegratedCircuitBlockEntityRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleModCommon.MOD_ID)
public class ExampleModForge {

    public ExampleModForge(FMLJavaModLoadingContext context) {
        ExampleModCommon.init();

        // register
        IEventBus bus = context.getModEventBus();
        ForgeRegistryHelper.BLOCK_ENTITIES.register(bus);
        ForgeRegistryHelper.BLOCKS.register(bus);
        ForgeRegistryHelper.CODECS.register(bus);
        ForgeRegistryHelper.DATA_COMPONENTS.register(bus);
        ForgeRegistryHelper.ITEMS.register(bus);
        ForgeRegistryHelper.MENUS.register(bus);
        ForgeRegistryHelper.SOUND_EVENTS.register(bus);
        ForgeRegistryHelper.CREATIVE_MODE_TABS.register(bus);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registries {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntities.CIRCUIT.get(), CircuitBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(BlockEntities.INTEGRATED_CIRCUIT.get(), IntegratedCircuitBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> MenuScreens.register(Menus.TRUTH_ASSIGNER.get(), TruthAssignerScreen::new));
            // this should've been set in the block model json, but for whatever reason it refused to work
            event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(Blocks.CIRCUIT.get(), RenderType.cutout()));
            event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(Blocks.INTEGRATED_CIRCUIT.get(), RenderType.cutout()));
        }
    }
}