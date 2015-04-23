package com.dynious.projectplanner.proxy;

import com.dynious.projectplanner.event.ClientEventHandler;
import com.dynious.projectplanner.lib.KeyBindings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerKeybindings()
    {
        ClientRegistry.registerKeyBinding(KeyBindings.openGui);
    }

    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();
        ClientEventHandler ev = new ClientEventHandler();
        FMLCommonHandler.instance().bus().register(ev);
    }
}
