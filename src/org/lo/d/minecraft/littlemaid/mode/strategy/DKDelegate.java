package org.lo.d.minecraft.littlemaid.mode.strategy;

import net.minecraft.src.LMM_EntityLittleMaid;

public interface DKDelegate extends Strategy {

	public abstract boolean checkBlock(int pMode, int px, int py, int pz);

	public abstract boolean executeBlock(int pMode, int px, int py, int pz);

	public abstract boolean updateCurrentStrategy();

	public abstract void updateTask(LMM_EntityLittleMaid maid, int maidMode);

}