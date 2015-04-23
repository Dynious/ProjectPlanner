package com.dynious.projectplanner.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyBindings
{
    public static final KeyBinding openGui = new KeyBinding("key.projectplanner", Keyboard.KEY_P, "key.categories.projectplanner");
}
