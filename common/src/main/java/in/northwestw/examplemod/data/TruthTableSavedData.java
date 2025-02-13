package in.northwestw.examplemod.data;

import com.google.common.collect.Maps;
import in.northwestw.examplemod.Constants;
import in.northwestw.examplemod.properties.RelativeDirection;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TruthTableSavedData extends SavedData {
    private final Map<UUID, TruthTable> truthTables;

    public TruthTableSavedData() {
        this.truthTables = Maps.newHashMap();
    }

    public static TruthTableSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        TruthTableSavedData data = new TruthTableSavedData();
        for (Tag tt : tag.getList("tables", Tag.TAG_COMPOUND)) {
            CompoundTag tuple = (CompoundTag) tt;
            UUID uuid = tuple.getUUID("uuid");
            data.truthTables.put(uuid, TruthTable.load(tuple));
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        this.truthTables.forEach((uuid, table) -> {
            CompoundTag tuple = new CompoundTag();
            tuple.putUUID("uuid", uuid);
            table.save(tuple);
            list.add(tuple);
        });
        tag.put("tables", list);
        return tag;
    }

    public Map<RelativeDirection, Integer> getSignals(UUID uuid, Map<RelativeDirection, Integer> inputs) {
        Map<RelativeDirection, Integer> signals = Maps.newHashMap();
        if (!this.truthTables.containsKey(uuid)) return signals;
        TruthTable table = this.truthTables.get(uuid);
        int input = 0;
        for (RelativeDirection dir : table.inputs) {
            input <<= table.bits;
            // merge 4-bit into amount specified by table.bits
            // i haven't had time to look into the mathematical relationships yet
            int val = inputs.getOrDefault(dir, 0);
            if (table.bits == 4) input |= val;
            else if (table.bits == 2) input |= (((val >> 2) > 1 ? 1 : 0) << 1) | ((val & 0x3) > 1 ? 1 : 0);
            else input |= val > 0 ? 1 : 0;
        }
        int output = table.signals.getOrDefault(input, table.defaultValue);
        for (RelativeDirection dir: table.outputs.reversed()) {
            signals.put(dir, output & 0xF);
            output >>= 4;
        }
        return signals;
    }

    public UUID insertTruthTable(UUID uuid, List<RelativeDirection> inputs, List<RelativeDirection> outputs, Map<Integer, Integer> signals, int bits) {
        // optimization
        Map<Integer, Integer> reverseMapCount = Maps.newHashMap();
        for (int output: signals.values()) {
            int count = reverseMapCount.getOrDefault(output, 0);
            reverseMapCount.put(output, count + 1);
        }
        int defaultValue = reverseMapCount.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
        Map<Integer, Integer> optimizedMap = Maps.newHashMap();
        for (Map.Entry<Integer, Integer> entry : signals.entrySet())
            if (entry.getValue() != defaultValue)
                optimizedMap.put(entry.getKey(), entry.getValue());
        
        // find if the truth table repeats
        for (Map.Entry<UUID, TruthTable> entry : this.truthTables.entrySet()) {
            if (entry.getValue().isSame(inputs, outputs, optimizedMap, defaultValue, bits))
                return entry.getKey();
        }
        this.truthTables.put(uuid, new TruthTable(inputs, outputs, optimizedMap, defaultValue, bits));
        this.setDirty();
        return uuid;
    }

    public static TruthTableSavedData getTruthTableData(ServerLevel level) {
        ServerLevel circuitBoardLevel = level.getServer().getLevel(Constants.CIRCUIT_BOARD_DIMENSION);
        DimensionDataStorage storage = circuitBoardLevel.getDataStorage();
        return storage.computeIfAbsent(new SavedData.Factory<>(TruthTableSavedData::new, TruthTableSavedData::load, null), "truth_table");
    }
}
