package in.lakazatong.examplemod;

import in.lakazatong.examplemod.registries.*;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleModCommon {
    public static final String MOD_ID = "example_mod";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        Config.load();

        // blocks must be registered before block entities for fabric
        ModBlocks.trigger();
        ModBlockEntities.trigger();
        ModCodecs.trigger();
        ModDataComponents.trigger();
        ModItems.trigger();
        ModMenus.trigger();
        ModSoundEvents.trigger();
        ModTabs.trigger();
    }

    public static ResourceLocation rl(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
