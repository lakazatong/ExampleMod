package in.northwestw.examplemod;


import in.northwestw.examplemod.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(ExampleModCommon.MOD_ID)
public class ExampleModNeoForge {
    public ExampleModNeoForge(IEventBus bus) {
        ExampleModCommon.init();
        NeoForgeRegistryHelper.BLOCK_ENTITIES.register(bus);
        NeoForgeRegistryHelper.BLOCKS.register(bus);
        NeoForgeRegistryHelper.CODECS.register(bus);
        NeoForgeRegistryHelper.DATA_COMPONENTS.register(bus);
        NeoForgeRegistryHelper.ITEMS.register(bus);
        NeoForgeRegistryHelper.MENUS.register(bus);
        NeoForgeRegistryHelper.SOUND_EVENTS.register(bus);
        NeoForgeRegistryHelper.CREATIVE_MODE_TABS.register(bus);
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class Registries {
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        }

        @SubscribeEvent
        public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        }
    }
}