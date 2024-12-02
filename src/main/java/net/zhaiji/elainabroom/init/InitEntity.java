package net.zhaiji.elainabroom.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

import java.util.function.Supplier;

public class InitEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ElainaBroom.MOD_ID);

    public static Supplier<EntityType<ElainaBroomEntity>> ELAINA_BROOM = ENTITY_TYPES.register("elaina_broom", () -> ElainaBroomEntity.TYPE);
}