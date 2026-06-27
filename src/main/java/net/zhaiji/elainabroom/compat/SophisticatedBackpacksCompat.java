package net.zhaiji.elainabroom.compat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.zhaiji.elainabroom.init.InitItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 与精妙背包交互的封装，通过 NeoForge 标准 Capability 与精妙核心注册的组件完成存取
 */
public final class SophisticatedBackpacksCompat {
    /**
     * 收集玩家身上的所有精妙背包，按饰品栏、胸甲槽、主物品栏、副手的顺序返回
     */
    private static List<BackpackEntry> collectBackpacks(Player player) {
        List<BackpackEntry> backpacks = new ArrayList<>();

        if (CompatManager.CURIOS_LOADED) {
            IItemHandler curiosHandler = CuriosCompat.getCuriosItemHandler(player);
            if (curiosHandler != null) {
                for (int slot = 0; slot < curiosHandler.getSlots(); slot++) {
                    addBackpackEntry(backpacks, curiosHandler.getStackInSlot(slot));
                }
            }
        }

        addBackpackEntry(backpacks, player.getItemBySlot(EquipmentSlot.CHEST));

        for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
            addBackpackEntry(backpacks, player.getInventory().items.get(slot));
        }

        for (int slot = 0; slot < player.getInventory().offhand.size(); slot++) {
            addBackpackEntry(backpacks, player.getInventory().offhand.get(slot));
        }

        return backpacks;
    }

    /**
     * 尝试将物品登记为可遍历背包条目，非背包或未暴露能力时跳过
     */
    private static void addBackpackEntry(List<BackpackEntry> backpacks, ItemStack stack) {
        UUID storageUuid = stack.get(ModCoreDataComponents.STORAGE_UUID.get());
        if (storageUuid == null) return;
        IItemHandler itemHandler = stack.getCapability(Capabilities.ItemHandler.ITEM);
        if (itemHandler == null) return;
        backpacks.add(new BackpackEntry(storageUuid, itemHandler));
    }

    /**
     * 检查玩家身上的精妙背包中是否存在扫帚，不取出物品
     */
    public static boolean hasBroomInBackpacks(Player player) {
        for (BackpackEntry entry : collectBackpacks(player)) {
            for (int slot = 0; slot < entry.itemHandler().getSlots(); slot++) {
                if (entry.itemHandler().getStackInSlot(slot).is(InitItem.ELAINA_BROOM.get())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 在玩家身上的所有精妙背包中查找并取出一个扫帚，返回取出的物品及其来源背包 UUID
     */
    @Nullable
    public static FoundBroom findBroomInBackpacks(Player player) {
        for (BackpackEntry entry : collectBackpacks(player)) {
            for (int slot = 0; slot < entry.itemHandler().getSlots(); slot++) {
                ItemStack slotStack = entry.itemHandler().getStackInSlot(slot);
                if (slotStack.is(InitItem.ELAINA_BROOM.get())) {
                    if (entry.itemHandler().extractItem(slot, 1, true).isEmpty()) {
                        continue;
                    }
                    ItemStack broomStack = entry.itemHandler().extractItem(slot, 1, false);
                    return new FoundBroom(broomStack, entry.storageUuid());
                }
            }
        }
        return null;
    }

    /**
     * 按优先级将扫帚归还到玩家身上的精妙背包中，成功插入返回 true
     */
    public static boolean returnBroomToBackpack(Player player, ItemStack broomStack, UUID preferredStorageUuid) {
        List<BackpackEntry> backpacks = collectBackpacks(player);

        for (BackpackEntry entry : backpacks) {
            if (!entry.storageUuid().equals(preferredStorageUuid)) {
                continue;
            }
            if (insertAll(entry.itemHandler(), broomStack)) {
                return true;
            }
        }

        for (BackpackEntry entry : backpacks) {
            if (entry.storageUuid().equals(preferredStorageUuid)) {
                continue;
            }
            if (insertAll(entry.itemHandler(), broomStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 尝试将整组物品插入物品处理器，先模拟再实际插入
     */
    private static boolean insertAll(IItemHandler itemHandler, ItemStack stack) {
        ItemStack remaining = ItemHandlerHelper.insertItemStacked(itemHandler, stack.copy(), true);
        if (!remaining.isEmpty()) return false;
        ItemHandlerHelper.insertItemStacked(itemHandler, stack, false);
        return true;
    }
}
