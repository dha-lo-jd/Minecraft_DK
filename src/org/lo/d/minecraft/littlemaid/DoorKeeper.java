package org.lo.d.minecraft.littlemaid;

import java.lang.reflect.InvocationTargetException;

import org.lo.d.commons.configuration.ConfigurationSupport;
import org.lo.d.commons.configuration.ConfigurationSupport.IntConfig;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "LMM_EntityMode_DoorKeeper", name = "LMM Mode DoorKeeper", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@ConfigurationSupport.ConfigurationMod
public class DoorKeeper {

	@IntConfig(defaultValue = 60, name = "waitMargin")
	public static int waitMargin;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		ConfigurationSupport.load(getClass(), event);
	}
}
