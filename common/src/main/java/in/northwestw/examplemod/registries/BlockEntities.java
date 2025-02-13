package in.northwestw.examplemod.registries;

import in.northwestw.examplemod.platform.Services;
import in.northwestw.examplemod.registries.blockentities.CircuitBlockEntity;
import in.northwestw.examplemod.registries.blockentities.CircuitBoardBlockEntity;
import in.northwestw.examplemod.registries.blockentities.IntegratedCircuitBlockEntity;
import in.northwestw.examplemod.registries.blockentities.TruthAssignerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BlockEntities {
    public static final Supplier<BlockEntityType<CircuitBlockEntity>> CIRCUIT = Services.REGISTRY.registerBlockEntityType("circuit", CircuitBlockEntity::new, Blocks.CIRCUIT);
    public static final Supplier<BlockEntityType<CircuitBoardBlockEntity>> CIRCUIT_BOARD = Services.REGISTRY.registerBlockEntityType("circuit_board", CircuitBoardBlockEntity::new, Blocks.CIRCUIT_BOARD);
    public static final Supplier<BlockEntityType<TruthAssignerBlockEntity>> TRUTH_ASSIGNER = Services.REGISTRY.registerBlockEntityType("truth_assigner", TruthAssignerBlockEntity::new, Blocks.TRUTH_ASSIGNER);
    public static final Supplier<BlockEntityType<IntegratedCircuitBlockEntity>> INTEGRATED_CIRCUIT = Services.REGISTRY.registerBlockEntityType("integrated_circuit", IntegratedCircuitBlockEntity::new, Blocks.INTEGRATED_CIRCUIT);

    public static void trigger() { }
}
