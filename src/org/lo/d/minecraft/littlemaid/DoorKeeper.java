package org.lo.d.minecraft.littlemaid;

import java.lang.reflect.InvocationTargetException;

import net.minecraftforge.common.Configuration;

import org.lo.d.commons.configuration.ConfigurationSupport;
import org.lo.d.commons.configuration.ConfigurationSupport.IntConfig;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "LMM_EntityMode_DoorKeeper", name = "LMM Mode DoorKeeper", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
@ConfigurationSupport.ConfigurationMod
public class DoorKeeper {

	@IntConfig(defaultValue = 60, name = "waitMargin")
	public static int waitMargin;

	@Mod.Init
	public void init(FMLInitializationEvent event) {
	}

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ConfigurationSupport.load(getClass(), event, config);
		config.save();
	}
}
