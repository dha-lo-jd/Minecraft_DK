package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.BlockLever;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class DefaultLeverActivateStrategy extends LeverActivateStrategy.Impl {

	public DefaultLeverActivateStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public boolean shouldStrategy() {
		return true;
	}

	@Override
	public void startStrategy() {
		doorKeeper.owner.getNavigator().clearPathEntity();
		super.startStrategy();
	}

	@Override
	protected boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
		return false;
	}

}
