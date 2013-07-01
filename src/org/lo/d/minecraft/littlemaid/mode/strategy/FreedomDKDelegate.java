package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.mode.LMMModeExHandler.TaskState;

public class FreedomDKDelegate extends DKDelegate.Impl<LeverActivateStrategy> {

	public FreedomDKDelegate(LMM_EntityMode_DoorKeeper mode, StrategyUserHelper<LeverActivateStrategy> subHelper) {
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
	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
		for (LeverActivateStrategy strategy : helper.getStrategies()) {
			if (strategy.handleHealthUpdate(maid, maidMode, par1) == TaskState.BREAK) {
				return TaskState.BREAK;
			}
		}
		return TaskState.CONTINUE;
	}

	@Override
	public boolean shouldStrategy() {
		return mode.owner.isFreedom();
	}

	@Override
	public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		getCurrentStrategy().updateTask(maid, maidMode);
	}

}
