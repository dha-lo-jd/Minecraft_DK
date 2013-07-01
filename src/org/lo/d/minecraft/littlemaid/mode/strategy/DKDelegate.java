package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.mode.LMMModeExHandler.TaskState;

public interface DKDelegate extends Strategy {
	public static abstract class Impl<T extends Strategy> extends Strategy.DefaultImpl implements DKDelegate {
		protected final LMM_EntityMode_DoorKeeper mode;

		public final StrategyUserHelper<T> helper;

		public Impl(LMM_EntityMode_DoorKeeper mode, StrategyUserHelper<T> subHelper) {
			this.mode = mode;
			helper = subHelper;
		}

		@Override
		public boolean checkBlock(int pMode, int px, int py, int pz) {
			return false;
		}

		@Override
		public boolean executeBlock(int pMode, int px, int py, int pz) {
			return false;
		}

		public T getCurrentStrategy() {
			return helper.getCurrentStrategy();
		}

		@Override
		public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
			return null;
		}

		@Override
		public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		}

	}

	public abstract boolean checkBlock(int pMode, int px, int py, int pz);

	public abstract boolean executeBlock(int pMode, int px, int py, int pz);

	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1);

	public abstract void updateTask(LMM_EntityLittleMaid maid, int maidMode);

}