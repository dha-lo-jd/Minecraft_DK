package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.commons.coords.Point3D;

public class DoorCloseStrategy extends DoorActivateStrategy.Impl {

	private static final int distToClose = 7 * 7;

	public DoorCloseStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public boolean shouldStrategy() {
		return true;
	}

	@Override
	protected boolean validateBlock(int px, int py, int pz) {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		if (maid.mstatMasterEntity == null) {
			return false;
		}

		int blockId = maid.worldObj.getBlockId(px, py, pz);
		if (blockId != Block.doorWood.blockID) {
			return false;
		}

		Point3D checkPos = new Point3D(px, py, pz);
		double distanceSq = checkPos.distanceToSq(maid.mstatMasterEntity.getPosition(1.0f));

		BlockDoor door = (BlockDoor) Block.doorWood;
		if (distanceSq > distToClose) {
			if (door.isDoorOpen(maid.worldObj, px, py, pz)) {
				return true;
			}
		}
		return false;
	}

}
