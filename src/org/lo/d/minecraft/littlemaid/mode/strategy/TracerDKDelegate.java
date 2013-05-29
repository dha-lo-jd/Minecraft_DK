package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class TracerDKDelegate implements Strategy, DKDelegate {

	private final LMM_EntityMode_DoorKeeper mode;

	public final StrategyUserHelper<DoorActivateStrategy> helper;

	public TracerDKDelegate(LMM_EntityMode_DoorKeeper mode) {
		super();
		this.mode = mode;
		helper = new StrategyUserHelper<>(new DoorCloseStrategy(mode));
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
	public void onChangeStrategy() {
	}

	@Override
	public boolean shouldStrategy() {
		return mode.owner.isTracer();
	}

	@Override
	public void stopStrategy() {
		getCurrentStrategy().stopStrategy();
	}

	@Override
	public boolean updateCurrentStrategy() {
		return helper.updateCurrentStrategy();
	}

	@Override
	public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		getCurrentStrategy().updateTask(maid, maidMode);
	}

	private DoorActivateStrategy getCurrentStrategy() {
		return helper.getCurrentStrategy();
	}

}
