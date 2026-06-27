package net.zhaiji.elainabroom.compat;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.UUID;

/**
 * 一个可遍历的精妙背包条目，包含存储 UUID 与物品处理器
 */
record BackpackEntry(UUID storageUuid, ResourceHandler<ItemResource> resourceHandler) {
}
