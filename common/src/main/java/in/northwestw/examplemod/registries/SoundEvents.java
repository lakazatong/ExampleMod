package in.northwestw.examplemod.registries;

import in.northwestw.examplemod.platform.Services;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class SoundEvents {
    public static final Supplier<SoundEvent> TRUTH_ASSIGNED = Services.REGISTRY.registerSound("truth_assigned");

    public static void trigger() { }
}
