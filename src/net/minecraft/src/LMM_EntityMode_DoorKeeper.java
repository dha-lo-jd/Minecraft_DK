package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.src.LMM_EntityMode_AcceptBookCommand.ModeAlias;

import org.lo.d.minecraft.littlemaid.entity.ai.EntityAIFindBlockEx;
import org.lo.d.minecraft.littlemaid.mode.LMM_EntityModeBaseEx;
import org.lo.d.minecraft.littlemaid.mode.strategy.DKDelegate;
import org.lo.d.minecraft.littlemaid.mode.strategy.FreedomDKDelegate;
import org.lo.d.minecraft.littlemaid.mode.strategy.StrategyUserHelper;
import org.lo.d.minecraft.littlemaid.mode.strategy.TracerDKDelegate;

import com.google.common.collect.Lists;

public class LMM_EntityMode_DoorKeeper extends LMM_EntityModeBaseEx {
	public enum State {
		TO_OPEN, TO_CLOSE, WAIT,
	}

	public static final int MODE_ID = 0x0203;

	public static final String MODE_NAME = "DoorKeeper";

	public final StrategyUserHelper<DKDelegate> helper;

	private boolean strategyChanged = false;

	public LMM_EntityMode_DoorKeeper(LMM_EntityLittleMaid pEntity) {
		super(pEntity);
		helper = new StrategyUserHelper<>(new TracerDKDelegate(this));
		helper.add(new FreedomDKDelegate(this));
	}

	@Override
	public void addEntityMode(EntityAITasks pDefaultMove, EntityAITasks pDefaultTargeting) {
		// Healer:0x0082
		EntityAITasks[] ltasks = new EntityAITasks[2];
		ltasks[0] = new EntityAITasks(owner.aiProfiler);
		@SuppressWarnings("unchecked")
		ArrayList<?> copyTasks = Lists.newArrayList(pDefaultMove.taskEntries);
		ltasks[0].taskEntries = copyTasks;
		ltasks[0].removeTask(owner.aiFindBlock);
		ltasks[0].addTask(4, new EntityAIFindBlockEx(owner));
		ltasks[1] = new EntityAITasks(owner.aiProfiler);

		// 索敵系
		ltasks[1].addTask(1, new EntityAIHurtByTarget(owner, true));

		owner.addMaidMode(ltasks, MODE_NAME, MODE_ID);
	}

	@Override
	public boolean checkBlock(int pMode, int px, int py, int pz) {
		return getCurrentStrategy().checkBlock(pMode, px, py, pz);
	}

	@Override
	public boolean checkItemStack(ItemStack pItemStack) {
		return pItemStack.getItem() instanceof ItemFood || pItemStack.getItem() instanceof ItemPotion;
	}

	@Override
	public boolean executeBlock(int pMode, int px, int py, int pz) {
		return getCurrentStrategy().executeBlock(pMode, px, py, pz);
	}

	public DKDelegate getCurrentStrategy() {
		return helper.getCurrentStrategy();
	}

	@Override
	public TaskState handleHealthUpdate(LMM_EntityLittleMaid maid, int maidMode, byte par1) {
		for (DKDelegate strategy : helper.getStrategies()) {
			if (strategy.handleHealthUpdate(maid, maidMode, par1) == TaskState.BREAK) {
				return TaskState.BREAK;
			}
		}
		return TaskState.CONTINUE;
	}

	@Override
	public void init() {
		// 登録モードの名称追加
		addLocalization(MODE_NAME);
		LMM_EntityMode_AcceptBookCommand.add(new ModeAlias(MODE_ID, MODE_NAME, "Dk"));
	}

	@Override
	public boolean isSearchBlock() {
		return true;
	}

	@Override
	public void onUpdate(int pMode) {
		if (pMode == MODE_ID) {
			strategyChanged = strategyChanged | helper.updateCurrentStrategy();
			helper.getCurrentStrategy().onUpdateStrategy();
		}
	}

	@Override
	public TaskState onValidateTask(LMM_EntityLittleMaid maid, int maidMode) {
		if (maidMode == MODE_ID) {
			if (strategyChanged | getCurrentStrategy().updateCurrentStrategy()) {
				strategyChanged = false;
				return TaskState.BREAK;
			}
		}
		return TaskState.CONTINUE;
	}

	@Override
	public boolean outrangeBlock(int pMode, int pX, int pY, int pZ) {
		return super.outrangeBlock(pMode, pX, pY, pZ);
	}

	@Override
	public int priority() {
		return 7100;
	}

	@Override
	public boolean setMode(int pMode) {
		if (pMode == MODE_ID) {
			owner.setBloodsuck(false);
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldBlock(int pMode) {
		return true;
	}

	@Override
	public void updateAITick(int pMode) {
	}

	@Override
	public void updateTask(LMM_EntityLittleMaid maid, int maidMode) {
		getCurrentStrategy().updateTask(maid, maidMode);
	}

}
