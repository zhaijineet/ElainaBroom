package net.zhaiji.elainabroom.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.zhaiji.elainabroom.ElainaBroomConfig;
import net.zhaiji.elainabroom.client.keymapping.KeyMappings;
import net.zhaiji.elainabroom.client.model.ElainaBroomModel;
import net.zhaiji.elainabroom.client.render.ElainaBroomRender;
import net.zhaiji.elainabroom.client.sound.BroomFlightSound;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;
import net.zhaiji.elainabroom.init.InitEntityType;
import net.zhaiji.elainabroom.init.InitItem;

import java.util.List;

public class ClientEventHandler {
    /**
     * 注册按键绑定
     */
    public static void handlerRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.BROOM_SUMMON);
        event.register(KeyMappings.BROOM_DISMOUNT);
        event.register(KeyMappings.BROOM_UP);
        event.register(KeyMappings.BROOM_DOWN);
    }

    /**
     * 注册扫帚实体渲染器
     */
    public static void handlerEntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(InitEntityType.ELAINA_BROOM.get(), ElainaBroomRender::new);
    }

    /**
     * 注册扫帚模型层
     */
    public static void handlerEntityRenderersEvent$RegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ElainaBroomModel.LAYER, ElainaBroomModel::createBodyLayer);
    }

    /**
     * 处理鼠标按键输入，发送对应网络包
     */
    public static void handlerInputEvent$MouseButton$Pre(InputEvent.MouseButton.Pre event) {
        if (event.getAction() != InputConstants.PRESS) return;
        KeyMappings.customKeyTrigger(InputConstants.Type.MOUSE.getOrCreate(event.getButton()));
    }

    /**
     * 处理键盘按键输入，发送对应网络包
     */
    public static void handlerInputEvent$Key(InputEvent.Key event) {
        if (event.getAction() != InputConstants.PRESS) return;
        KeyMappings.customKeyTrigger(InputConstants.getKey(event.getKey(), event.getScanCode()));
    }

    /**
     * 每个客户端 tick 开始时，将按键输入状态同步到扫帚实体
     */
    public static void handlerClientTickEvent$Pre(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        if (minecraft.player.getVehicle() instanceof ElainaBroomEntity broom) {
            broom.setInput(
                minecraft.options.keyUp.isDown(),
                minecraft.options.keyDown.isDown(),
                minecraft.options.keyLeft.isDown(),
                minecraft.options.keyRight.isDown(),
                KeyMappings.BROOM_UP.isDown(),
                KeyMappings.BROOM_DOWN.isDown()
            );
        }
    }

    /**
     * 骑上扫帚时播放飞行音效
     */
    public static void handlerEntityMountEvent(EntityMountEvent event) {
        if (event.getLevel().isClientSide() && event.getEntityBeingMounted() instanceof ElainaBroomEntity broom) {
            if (event.isMounting()) {
                Minecraft.getInstance().getSoundManager().play(new BroomFlightSound(broom));
            }
        }
    }

    /**
     * 为扫帚物品动态组装工具提示
     */
    public static void handlerItemTooltipEvent(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.is(InitItem.ELAINA_BROOM.get())) return;

        List<Component> tooltip = event.getToolTip();
        Player player = event.getEntity();
        if (player == null) {
            player = Minecraft.getInstance().player;
        }

        int index = 1;

        boolean showNeedLevel = ElainaBroomConfig.needLevel > 0;
        boolean showMaxLevel = !ElainaBroomConfig.unlimitedSpeed;
        if (showNeedLevel && showMaxLevel) {
            tooltip.add(
                index++, Component.translatable(
                    "tooltip.elainabroom.broom.ride_and_max_level",
                    ElainaBroomConfig.needLevel,
                    ElainaBroomConfig.maxLevel
                )
            );
        } else if (showNeedLevel) {
            tooltip.add(
                index++, Component.translatable(
                    "tooltip.elainabroom.broom.need_level",
                    ElainaBroomConfig.needLevel
                )
            );
        } else if (showMaxLevel) {
            tooltip.add(
                index++, Component.translatable(
                    "tooltip.elainabroom.broom.max_level",
                    ElainaBroomConfig.maxLevel
                )
            );
        }

        double speedScale = ElainaBroomEntity.calculateSpeedScale(player.experienceLevel, player.getAbilities().instabuild);
        double currentSpeed = 0.03 * ElainaBroomConfig.forwardSpeed * ElainaBroomConfig.speed * speedScale * ElainaBroomConfig.friction / (1.0 - ElainaBroomConfig.friction) * 20.0;
        tooltip.add(
            index++, Component.translatable(
                "tooltip.elainabroom.broom.speed",
                String.format("%.1f", currentSpeed)
            )
        );

        tooltip.add(
            index++, Component.translatable(
                "tooltip.elainabroom.broom.summon_and_recall",
                Component.keybind("key.elainabroom.broom_summon_recall"),
                ElainaBroomConfig.recallDistance
            )
        );
    }
}
