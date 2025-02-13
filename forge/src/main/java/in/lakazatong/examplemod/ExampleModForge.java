package in.lakazatong.examplemod;

import in.lakazatong.examplemod.platform.ForgeRegistryHelper;
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
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            // this should've been set in the block model json, but for whatever reason it refused to work
        }
    }
}