package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lo.d.commons.coords.EntityPoint3DDouble;
import org.lo.d.commons.coords.Point3D;

public class MasterLookingDoorOpenStrategy extends DoorActivateStrategy.Impl {

	private static final int distToOpen = 6 * 6;

	public MasterLookingDoorOpenStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public boolean shouldStrategy() {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		Point3D mstLookAtPos = getBlockPositionMasterLookAt();
		if (mstLookAtPos != null) {
			double distanceSq = mstLookAtPos.distanceToSq(new EntityPoint3DDouble(maid.mstatMasterEntity));
			if (distanceSq <= distToOpen) {
				int px = mstLookAtPos.getX();
				int py = mstLookAtPos.getY();
				int pz = mstLookAtPos.getZ();
				int blockId = maid.worldObj.getBlockId(px, py, pz);
				if (blockId != Block.doorWood.blockID) {
					return false;
				}
				BlockDoor door = (BlockDoor) Block.doorWood;
				if (!door.isDoorOpen(maid.worldObj, px, py, pz)) {
					return true;
				}
			}
		}
		return false;
	}

	private Point3D getBlockPositionMasterLookAt() {
		EntityPlayer player = doorKeeper.owner.mstatMasterEntity;
		if (player == null) {
			return null;
		}
		MovingObjectPosition mop;
		{
			float rpt = 1.0f;
			double distance = 8;
			Vec3 vec3 = player.getPosition(rpt);
			vec3 = vec3.addVector(0, player.getEyeHeight(), 0);
			Vec3 vec31 = player.getLook(rpt);
			Vec3 vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
			mop = player.worldObj.rayTraceBlocks(vec3, vec32);
		}
		if (mop != null && mop.typeOfHit == EnumMovingObjectType.TILE) {
			return new Point3D(mop.blockX, mop.blockY, mop.blockZ);
		}
		return null;
	}

	@Override
	protected boolean validateBlock(int px, int py, int pz) {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		int blockId = maid.worldObj.getBlockId(px, py, pz);
		if (blockId != Block.doorWood.blockID) {
			return false;
		}
		Point3D checkPos = new Point3D(px, py, pz);
		double distanceSq = checkPos.distanceToSq(new EntityPoint3DDouble(maid.mstatMasterEntity));

		BlockDoor door = (BlockDoor) Block.doorWood;
		if (distanceSq <= distToOpen) {
			Point3D mstLookAtPos = getBlockPositionMasterLookAt();
			if (mstLookAtPos != null && mstLookAtPos.equals(checkPos)) {
				if (!door.isDoorOpen(maid.worldObj, px, py, pz)) {
					return true;
				}
			}
		}
		return false;
	}

}
