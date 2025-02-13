package in.lakazatong.examplemod.platform;

import com.mojang.serialization.MapCodec;
import in.lakazatong.examplemod.ExampleModCommon;
import in.lakazatong.examplemod.platform.services.IRegistryHelper;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends Block>> CODECS = DeferredRegister.create(Registries.BLOCK_TYPE, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, ExampleModCommon.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleModCommon.MOD_ID);

    @Override
    public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntityType(String name, BlockEntitySupplier<T> factory, Supplier<Block> ...blocks) {
        return BLOCK_ENTITIES.register(name, () -> new BlockEntityType<>(factory::create, Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toSet())));
    }

    @Override
    public Supplier<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, () -> factory.apply(properties.setId(ResourceKey.create(Registries.BLOCK, ExampleModCommon.rl(name)))));
    }

    @Override
    public <T extends Block> Supplier<MapCodec<T>> registerCodec(String name, Supplier<MapCodec<T>> supplier) {
        return CODECS.register(name, supplier);
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

    @Override
    public <T extends Item> Supplier<T> registerItem(String name, Function<Item.Properties, T> function, Item.Properties properties) {
        return ITEMS.register(name, () -> function.apply(properties.setId(ResourceKey.create(Registries.ITEM, ExampleModCommon.rl(name)))));
    }

    @Override
    public <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenu(String name, MenuSupplier<T> constructor, FeatureFlagSet requiredFeatures) {
        return MENUS.register(name, () -> new MenuType<>(constructor::create, requiredFeatures));
    }

    @Override
    public Supplier<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ExampleModCommon.MOD_ID, name)));
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Supplier<? extends Item>... items) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).title(title).icon(icon).displayItems((params, output) -> {
            for (Supplier<? extends Item> item : items)
                output.accept(item.get());
        }).build());
    }
}
