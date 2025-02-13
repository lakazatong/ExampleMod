package in.lakazatong.examplemod.registries;

import in.lakazatong.examplemod.platform.Services;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ModItems {
    private static Supplier<BlockItem> registerSimpleBlockItem(String name, Supplier<Block> supplier) {
        return Services.REGISTRY.registerItem(name, properties -> new BlockItem(supplier.get(), properties), new Item.Properties());
    }

    public static void trigger() { }
}
