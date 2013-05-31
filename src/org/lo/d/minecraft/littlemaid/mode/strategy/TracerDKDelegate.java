package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class TracerDKDelegate extends DKDelegate.Impl<DoorActivateStrategy> implements DKDelegate {

	public TracerDKDelegate(LMM_EntityMode_DoorKeeper mode) {
		super(mode, new DoorCloseStrategy(mode));
		helper.add(new MasterLookingDoorOpenStrategy(mode));
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
		return mode.owner.isTracer();
	}

	@Override
	public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		getCurrentStrategy().updateTask(maid, maidMode);
	}

}
