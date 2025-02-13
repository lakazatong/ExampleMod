package in.northwestw.examplemod;

import in.northwestw.examplemod.platform.FabricRegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public class ExampleModFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        ExampleModCommon.init();
        for (Map.Entry<ResourceKey<CreativeModeTab>, List<Item>> entry : FabricRegistryHelper.CREATIVE_MODE_TAB_ITEMS.entrySet())
            ItemGroupEvents.modifyEntriesEvent(entry.getKey()).register(group ->
                    group.addAfter((stack) -> true,
                            entry.getValue().stream().map(Item::getDefaultInstance).toList(),
                            CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)
            );
    }

    @Override
    public void onInitializeClient() {
    }
}
