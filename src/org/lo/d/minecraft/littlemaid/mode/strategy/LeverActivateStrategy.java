package org.lo.d.minecraft.littlemaid.mode.strategy;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.commons.coords.Point3D;
import org.lo.d.minecraft.littlemaid.mode.LMMModeExHandler.TaskState;

import com.google.common.collect.Sets;

public interface LeverActivateStrategy extends Strategy {

	public abstract class Impl extends Strategy.DefaultImpl implements LeverActivateStrategy {
		protected final LMM_EntityMode_DoorKeeper doorKeeper;

		protected Point3D target = null;

		protected Set<Point3D> activatedLevers = Sets.newHashSet();

		public Impl(LMM_EntityMode_DoorKeeper doorKeeper) {
			this.doorKeeper = doorKeeper;
		}

		@Override
		public boolean checkBlock(int pMode, int px, int py, int pz) {
			if (validateBlock(px, py, pz)) {
				target = new Point3D(px, py, pz);
				return true;
			} else {
				target = null;
				return false;
			}
		}

		@Override
		public boolean executeBlock(int pMode, int px, int py, int pz) {
			BlockLever lever = (BlockLever) Block.lever;
			LMM_EntityLittleMaid maid = doorKeeper.owner;
			target = null;
			if (validateBlock(px, py, pz)) {
				lever.onBlockActivated(maid.worldObj, px, py, pz, maid.maidAvatar, 0, (float) maid.posX,
						(float) maid.posY, (float) maid.posZ);
				activatedLevers.add(new Point3D(px, py, pz));
				return true;
			}
			return false;
		}

		@Override
		public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
			return null;
		}

		@Override
		public void onChangeStrategy() {
			activatedLevers = Sets.newHashSet();
		}

		@Override
		public void stopStrategy() {
			LMM_EntityLittleMaid maid = doorKeeper.owner;
			if (target != null) {
				maid.getNavigator().clearPathEntity();
				target = null;
			}
		}

		@Override
		public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
			if (target != null) {
				double ld = maid.getDistanceSq(target.getX(), target.getY(), target.getZ());
				if (ld <= 5.0D) {
					// 射程距離
					stopStrategy();
				}
			}
		}

		protected boolean isLeverOn(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
			return lever.isProvidingWeakPower(maid.worldObj, px, py, pz, 0) > 0;
		}

		protected boolean validateBlock(int px, int py, int pz) {
			LMM_EntityLittleMaid maid = doorKeeper.owner;
			if (maid.mstatMasterEntity == null) {
				return false;
			}

			if (activatedLevers.contains(new Point3D(px, py, pz))) {
				return false;
			}

			int blockId = maid.worldObj.getBlockId(px, py, pz);
			if (blockId != Block.lever.blockID) {
				return false;
			}

			BlockLever lever = (BlockLever) Block.lever;
			if (validateLeverState(px, py, pz, maid, lever)) {
				return true;
			}
			return false;
		}

		protected abstract boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid,
				BlockLever lever);
	}

	public boolean checkBlock(int pMode, int px, int py, int pz);

	public boolean executeBlock(int pMode, int px, int py, int pz);

	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1);

	public void updateTask(LMM_EntityLittleMaid maid, int maidMode);
}
