package com.dynious.projectplanner.event;

import com.dynious.projectplanner.client.gui.GuiProjectPlanner;
import com.dynious.projectplanner.lib.KeyBindings;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class ClientEventHandler
{
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event)
    {
        if (!FMLClientHandler.instance().isGUIOpen(GuiProjectPlanner.class) && KeyBindings.openGui.isPressed())
        {
            FMLClientHandler.instance().showGuiScreen(new GuiProjectPlanner());
        }
    }
}
