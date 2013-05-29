package org.lo.d.minecraft.littlemaid.mode.strategy;

public interface Strategy {

	public abstract void onChangeStrategy();

	public abstract boolean shouldStrategy();

	public abstract void stopStrategy();

}