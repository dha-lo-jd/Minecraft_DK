package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

public class FreedomDKDelegate implements Strategy, DKDelegate {

	private final LMM_EntityMode_DoorKeeper mode;

	public final StrategyUserHelper<LeverActivateStrategy> helper;

	public FreedomDKDelegate(LMM_EntityMode_DoorKeeper mode) {
		super();
		this.mode = mode;
		helper = new StrategyUserHelper<>(new LeverOffStrategy(mode));
		helper.add(new LeverOnStrategy(mode));
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
		return !mode.owner.isTracer() && mode.owner.isFreedom();
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

	private LeverActivateStrategy getCurrentStrategy() {
		return helper.getCurrentStrategy();
	}

}
