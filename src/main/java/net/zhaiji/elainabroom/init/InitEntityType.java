package net.zhaiji.elainabroom.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class InitEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, ElainaBroom.MOD_ID);

    public static final RegistryObject<EntityType<ElainaBroomEntity>> ELAINA_BROOM = ENTITY_TYPE.register("elaina_broom", () -> ElainaBroomEntity.TYPE);
}
