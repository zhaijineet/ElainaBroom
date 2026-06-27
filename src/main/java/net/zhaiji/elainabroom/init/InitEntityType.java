package net.zhaiji.elainabroom.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.elainabroom.ElainaBroom;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class InitEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ElainaBroom.MOD_ID);

    public static final RegistryObject<EntityType<ElainaBroomEntity>> ELAINA_BROOM = register(
            "elaina_broom",
            EntityType.Builder.<ElainaBroomEntity>of(ElainaBroomEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .sized(1.3f, 0.6f)
    );

    public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(ElainaBroom.of(name).toString()));
    }
}
