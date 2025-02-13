package in.northwestw.examplemod.registries;

import com.mojang.serialization.Codec;
import in.northwestw.examplemod.platform.Services;
import in.northwestw.examplemod.registries.datacomponents.LastPosDataComponent;
import in.northwestw.examplemod.registries.datacomponents.UUIDDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.Supplier;

public class DataComponents {
    public static final Supplier<DataComponentType<UUIDDataComponent>> UUID = Services.REGISTRY.registerDataComponent("uuid", UUIDDataComponent::getBuilder);
    public static final Supplier<DataComponentType<Short>> SHORT = createShortDataComponent("short");
    public static final Supplier<DataComponentType<LastPosDataComponent>> LAST_POS = Services.REGISTRY.registerDataComponent("last_pos", LastPosDataComponent::getBuilder);
    public static final Supplier<DataComponentType<Boolean>> BIT = Services.REGISTRY.registerDataComponent("bit", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    private static Supplier<DataComponentType<Short>> createShortDataComponent(String name) {
        return Services.REGISTRY.registerDataComponent(name, builder -> builder.persistent(Codec.SHORT).networkSynchronized(ByteBufCodecs.SHORT));
    }

    public static void trigger() { }
}
