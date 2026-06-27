package net.zhaiji.elainabroom.compat;

import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/**
 * 从精妙背包中取出的扫帚及其来源背包的存储 UUID
 */
public record FoundBroom(ItemStack broomStack, UUID sourceStorageUuid) {
}
