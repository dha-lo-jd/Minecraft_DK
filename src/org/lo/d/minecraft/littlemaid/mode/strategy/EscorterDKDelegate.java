package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class EscorterDKDelegate extends DKDelegate.Impl<DoorActivateStrategy> {

	public EscorterDKDelegate(LMM_EntityMode_DoorKeeper mode, StrategyUserHelper<DoorActivateStrategy> subHelper) {
		super(mode, subHelper);
	}

	@Override
	public boolean checkBlock(int pMode, int px, int py, int pz) {
		return getCurrentStrategy().checkBlock(pMode, px, py, pz);
	}

	@Override
	public boolean executeBlock(int pMode, int px, int py, int pz) {
		return getCurrentStrategy().executeBlock(pMode, px, py, pz);
	}

	@Override
	public boolean shouldStrategy() {
		return !mode.owner.isMaidWait() && !mode.owner.isFreedom();
	}

	@Override
	public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		getCurrentStrategy().updateTask(maid, maidMode);
	}

}
