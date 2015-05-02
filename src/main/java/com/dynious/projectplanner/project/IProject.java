package com.dynious.projectplanner.project;

public interface IProject
{
    /**
     * Return the display name of your Project.
     *
     * @return the display name of your Project.
     */
    public String getProjectName();

    /**
     * Return the Object that will be used as icon for the category here. This can be an Item, Block, ItemStack
     * or IIcon.
     *
     * @return The icon object.
     */
    public Object getIconObject();
}
