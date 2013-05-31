package org.lo.d.minecraft.littlemaid.mode.strategy;

import java.util.Random;

import net.minecraft.block.BlockLever;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.LMM_EntityLittleMaid;
import net.minecraft.src.LMM_EntityMode_DoorKeeper;

import org.lo.d.minecraft.littlemaid.mode.LMMModeExHandler.TaskState;

public class LeverOnStrategy extends LeverActivateStrategy.Impl {

	public LeverOnStrategy(LMM_EntityMode_DoorKeeper doorKeeper) {
		super(doorKeeper);
	}

	@Override
	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
		switch (par1) {
		case 80:
			Random rng = maid.getRNG();
			int r = rng.nextInt() % 3 + 2;
			for (int i = 0; i < r; ++i)
			{
				double d0 = rng.nextGaussian() * 0.02D;
				double d1 = rng.nextGaussian() * 0.02D;
				double d2 = rng.nextGaussian() * 0.02D;
				maid.worldObj.spawnParticle("heart", maid.posX + rng.nextFloat() * maid.width * 2.0F - maid.width,
						maid.posY + 0.5D + rng.nextFloat() * maid.height, maid.posZ + rng.nextFloat() * maid.width
								* 2.0F - maid.width, d0, d1, d2);
			}
			return TaskState.BREAK;
		}
		return super.handleHealthUpdate(maid, maidMode, par1);
	}

	@Override
	public boolean shouldStrategy() {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		EntityPlayer player = maid.mstatMasterEntity;
		if (maid == null || player == null) {
			return false;
		}

		if (maid.getDistanceSqToEntity(player) > 8 * 8) {
			return false;
		}
		if (!maid.canEntityBeSeen(player)) {
			return false;
		}

		return true;
	}

	@Override
	public void startStrategy() {
		LMM_EntityLittleMaid maid = doorKeeper.owner;
		maid.worldObj.setEntityState(maid, (byte) 80);
		super.startStrategy();
	}

	@Override
	protected boolean validateLeverState(int px, int py, int pz, LMM_EntityLittleMaid maid, BlockLever lever) {
		return !isLeverOn(px, py, pz, maid, lever);
	}

}
