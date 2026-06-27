package net.zhaiji.elainabroom.compat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.resource.ResourceStack;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
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
            ResourceHandler<ItemResource> curiosHandler = CuriosCompat.getCuriosItemHandler(player);
            if (curiosHandler != null) {
                for (int slot = 0; slot < curiosHandler.size(); slot++) {
                    addBackpackEntry(backpacks, ItemUtil.getStack(curiosHandler, slot));
                }
            }
        }

        addBackpackEntry(backpacks, player.getItemBySlot(EquipmentSlot.CHEST));

        for (ItemStack itemStack : player.getInventory().getNonEquipmentItems()) {
            addBackpackEntry(backpacks, itemStack);
        }

        addBackpackEntry(backpacks, player.getOffhandItem());

        return backpacks;
    }

    /**
     * 尝试将物品登记为可遍历背包条目，非背包时跳过
     */
    private static void addBackpackEntry(List<BackpackEntry> backpacks, ItemStack stack) {
        UUID storageUuid = stack.get(ModCoreDataComponents.STORAGE_UUID.get());
        if (storageUuid == null) return;
        ResourceHandler<ItemResource> resourceHandler = BackpackWrapper.fromStack(stack).getInventoryForUpgradeProcessing();
        backpacks.add(new BackpackEntry(storageUuid, resourceHandler));
    }

    /**
     * 检查玩家身上的精妙背包中是否存在扫帚，不取出物品
     */
    public static boolean hasBroomInBackpacks(Player player) {
        for (BackpackEntry entry : collectBackpacks(player)) {
            ResourceHandler<ItemResource> handler = entry.resourceHandler();
            for (int index = 0; index < handler.size(); index++) {
                if (handler.getResource(index).is(InitItem.ELAINA_BROOM.get())) {
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
            ResourceStack<ItemResource> result = ResourceHandlerUtil.extractFirst(
                entry.resourceHandler(),
                resource -> resource.is(InitItem.ELAINA_BROOM.get()),
                1,
                null
            );
            if (result != null) {
                return new FoundBroom(result.resource().toStack(result.amount()), entry.storageUuid());
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
            if (insertAll(entry.resourceHandler(), broomStack)) {
                return true;
            }
        }

        for (BackpackEntry entry : backpacks) {
            if (entry.storageUuid().equals(preferredStorageUuid)) {
                continue;
            }
            if (insertAll(entry.resourceHandler(), broomStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 尝试将整组物品跨槽位堆叠插入物品处理器，先模拟再实际插入
     */
    private static boolean insertAll(ResourceHandler<ItemResource> handler, ItemStack stack) {
        ItemResource resource = ItemResource.of(stack);
        try (Transaction simulate = Transaction.openRoot()) {
            int inserted = ResourceHandlerUtil.insertStacking(handler, resource, stack.getCount(), simulate);
            if (inserted != stack.getCount()) return false;
        }
        ResourceHandlerUtil.insertStacking(handler, resource, stack.getCount(), null);
        return true;
    }
}
