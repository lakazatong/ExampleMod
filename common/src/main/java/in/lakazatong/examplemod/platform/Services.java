package in.lakazatong.examplemod.platform;

import in.lakazatong.examplemod.ExampleModCommon;
import in.lakazatong.examplemod.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ExampleModCommon.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}