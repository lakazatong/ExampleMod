package in.northwestw.examplemod.registries;

import in.northwestw.examplemod.platform.Services;
import in.northwestw.examplemod.registries.menus.TruthAssignerMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class Menus {
    public static final Supplier<MenuType<TruthAssignerMenu>> TRUTH_ASSIGNER = Services.REGISTRY.registerMenu("truth_assigner", TruthAssignerMenu::new, FeatureFlags.DEFAULT_FLAGS);

    public static void trigger() { }
}
