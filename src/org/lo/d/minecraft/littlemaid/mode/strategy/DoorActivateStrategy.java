package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.commons.coords.Point3D;

public interface DoorActivateStrategy extends DependencyStrategy {
	public abstract class Impl extends DependencyStrategy.DefaultImpl implements DoorActivateStrategy {
		protected final LMM_EntityMode_DoorKeeper doorKeeper;

		protected Point3D target = null;

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
			BlockDoor door = (BlockDoor) Block.doorWood;
			LMM_EntityLittleMaid maid = doorKeeper.owner;
			target = null;
			if (validateBlock(px, py, pz)) {
				door.onBlockActivated(maid.worldObj, px, py, pz, maid.maidAvatar, 0, (float) maid.posX,
						(float) maid.posY, (float) maid.posZ);
				return true;
			}
			return false;
		}

		@Override
		public void notifyDependencyStrategyChanged() {
			stopStrategy();
			onChangeStrategy();
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

		protected abstract boolean validateBlock(int px, int py, int pz);
	}

	public boolean checkBlock(int pMode, int px, int py, int pz);

	public boolean executeBlock(int pMode, int px, int py, int pz);

	public void updateTask(LMM_EntityLittleMaid maid, int maidMode);
}
