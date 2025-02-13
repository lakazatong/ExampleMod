package in.northwestw.examplemod.registries;

import in.northwestw.examplemod.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Supplier;

public class Tabs {
    public static final Supplier<CreativeModeTab> SHORT_CIRCUIT_TAB = Services.REGISTRY.registerCreativeModeTab(
            "example_mod",
            Component.translatable("itemGroup.example_mod"),
            () -> Items.CIRCUIT.get().getDefaultInstance(),
            Items.CIRCUIT, Items.POKING_STICK, Items.LABELLING_STICK, Items.TRUTH_ASSIGNER);

    public static void trigger() { }
}
