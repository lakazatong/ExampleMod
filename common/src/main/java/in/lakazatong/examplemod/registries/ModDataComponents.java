package in.lakazatong.examplemod.registries;

import in.lakazatong.examplemod.platform.Services;
import in.lakazatong.examplemod.registries.datacomponents.LastPosDataComponent;
import in.lakazatong.examplemod.registries.datacomponents.UUIDDataComponent;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final Supplier<DataComponentType<UUIDDataComponent>> UUID = Services.REGISTRY.registerDataComponent("uuid", UUIDDataComponent::getBuilder);
    public static final Supplier<DataComponentType<LastPosDataComponent>> LAST_POS = Services.REGISTRY.registerDataComponent("last_pos", LastPosDataComponent::getBuilder);

    public static void trigger() { }
}
