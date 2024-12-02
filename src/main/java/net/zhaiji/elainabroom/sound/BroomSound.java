package net.zhaiji.elainabroom.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.elainabroom.entity.ElainaBroomEntity;

public class BroomSound extends AbstractTickableSoundInstance {
    public static final int DELAY = 20;
    private final ElainaBroomEntity broom;
    private int time;

    public BroomSound(ElainaBroomEntity broom) {
        super(SoundEvents.ELYTRA_FLYING, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.broom = broom;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.1F;
    }

    @Override
    public void tick() {
        if (!this.broom.isRemoved() && this.broom.getControllingPassenger() instanceof Player) {
            this.x = (float) this.broom.getX();
            this.y = (float) this.broom.getY();
            this.z = (float) this.broom.getZ();
            float f = (float) this.broom.getDeltaMovement().lengthSqr();
            if ((double) f >= 1.0E-7) {
                this.volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
            } else {
                this.volume = 0.0F;
            }

            if (this.time < 20) {
                this.volume = 0.0F;
            } else if (this.time < 40) {
                this.volume = this.volume * ((float) (this.time - 20) / 20.0F);
            }

            float f1 = 0.8F;
            if (this.volume > 0.8F) {
                this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
                this.pitch = 1.0F;
            }
        } else {
            this.stop();
        }
        this.time++;
    }
}
