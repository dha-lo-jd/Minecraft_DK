package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.BlockLever;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.DoorKeeper;

public class LeverOffStrategy extends LeverActivateStrategy.Impl {

	private Long firstMissingTime = null;

	public LeverOffStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public void onChangeStrategy() {
		firstMissingTime = null;
		super.onChangeStrategy();
	}

	@Override
	public boolean shouldStrategy() {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		EntityPlayer player = maid.mstatMasterEntity;
		if (maid == null || player == null) {
			return false;
		}

		if (maid.getDistanceSqToEntity(player) > 10 * 10) {
			return true;
		}
		if (maid.canEntityBeSeen(player)) {
			return false;
		}

		long time = maid.worldObj.getWorldTime();
		if (firstMissingTime == null) {
			firstMissingTime = time;
			return false;
		} else if (time - firstMissingTime < DoorKeeper.waitMargin) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
		return isLeverOn(px, py, pz, maid, lever);
	}

}
