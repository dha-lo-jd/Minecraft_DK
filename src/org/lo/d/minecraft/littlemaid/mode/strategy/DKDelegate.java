package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.mode.LMMModeExHandler.TaskState;

public interface DKDelegate extends Strategy {
	public static abstract class Impl<T extends Strategy> extends Strategy.DefaultImpl implements DKDelegate {
		protected final LMM_EntityMode_DoorKeeper mode;

		public final StrategyUserHelper<T> helper;

		public Impl(LMM_EntityMode_DoorKeeper mode, T defaultStratey) {
			this.mode = mode;
			helper = new StrategyUserHelper<>(defaultStratey);
		}

		@Override
		public boolean checkBlock(int pMode, int px, int py, int pz) {
			return false;
		}

		@Override
		public boolean executeBlock(int pMode, int px, int py, int pz) {
			return false;
		}

		@Override
		public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
			return null;
		}

		@Override
		public boolean onCurrentStrategyUpdate() {
			return updateCurrentStrategy();
		}

		@Override
		public void onUpdateStrategy() {
			helper.getCurrentStrategy().onUpdateStrategy();
		}

		@Override
		public void startStrategy() {
			getCurrentStrategy().startStrategy();
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
		}

		protected T getCurrentStrategy() {
			return helper.getCurrentStrategy();
		}

	}

	public abstract boolean checkBlock(int pMode, int px, int py, int pz);

	public abstract boolean executeBlock(int pMode, int px, int py, int pz);

	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1);

	public abstract boolean updateCurrentStrategy();

	public abstract void updateTask(LMM_EntityLittleMaid maid, int maidMode);

}