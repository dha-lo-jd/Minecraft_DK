package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.BlockLever;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.DoorKeeper;

public class LeverOnStrategy extends LeverActivateStrategy.Impl {

	private long lastWaitTime;

	public LeverOnStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public boolean shouldStrategy() {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		EntityPlayer player = maid.mstatMasterEntity;
		if (maid == null || player == null) {
			return false;
		}

		long time = maid.worldObj.getWorldTime();
		if (maid.getDistanceSqToEntity(player) > 10 * 10) {
			lastWaitTime = -DoorKeeper.waitMargin;
			return false;
		}
		if (!maid.canEntityBeSeen(player)) {
			if (time - lastWaitTime > DoorKeeper.waitMargin) {
				return false;
			}
		} else {
			lastWaitTime = time;
		}

		return true;
	}

	@Override
	protected boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
		return !isLeverOn(px, py, pz, maid, lever);
	}

}
