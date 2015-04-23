package com.dynious.projectplanner;

import com.dynious.projectplanner.lib.Reference;
import com.dynious.projectplanner.proxy.CommonProxy;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = "com.dynious.projectplanner.config.GuiFactory")
public class ProjectPlanner
{
    @Mod.Instance(Reference.MOD_ID)
    public static ProjectPlanner instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static CommonProxy proxy;

    @EventHandler
    public void perInit(FMLPreInitializationEvent event)
    {
        proxy.registerKeybindings();
        proxy.registerEventHandlers();
    }
}
