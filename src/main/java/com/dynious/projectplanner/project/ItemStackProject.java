package com.dynious.projectplanner.project;

import net.minecraft.item.ItemStack;

public class ItemStackProject implements IProject
{
    private ItemStack stack;

    public ItemStackProject(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public String getProjectName()
    {
        return stack.getDisplayName();
    }

    @Override
    public Object getIconObject()
    {
        return stack;
    }
}
