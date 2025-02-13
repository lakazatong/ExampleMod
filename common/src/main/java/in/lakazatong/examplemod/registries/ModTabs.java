package in.lakazatong.examplemod.registries;

import in.lakazatong.examplemod.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class ModTabs {
    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = Services.REGISTRY.registerCreativeModeTab(
            "example_mod",
            Component.translatable("itemGroup.example_mod"),
            Items.GRASS_BLOCK::getDefaultInstance, () -> Items.GRASS_BLOCK);

    public static void trigger() { }
}
