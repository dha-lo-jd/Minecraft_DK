package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.BlockLever;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class LeverOffStrategy extends LeverActivateStrategy.Impl {

	public LeverOffStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public boolean shouldStrategy() {
		return true;
	}

	@Override
	protected boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
		return isLeverOn(px, py, pz, maid, lever);
	}

}
