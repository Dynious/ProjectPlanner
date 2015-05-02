package com.dynious.projectplanner.api;

public interface ICategory
{
    /**
     * This is an array of the names of your category. The first name will be the one used as display name.
     * You can add more entries to the array that act like nicknames, so your category shows up even if
     * users don't use the technically correct term for it.
     *
     * @return An array of names for the category.
     */
    public String[] getCategoryNames();

    /**
     * Return the Object that will be used as icon for the category here. This can be an Item, Block, ItemStack
     * or IIcon.
     *
     * @return The icon object.
     */
    public Object getIconObject();
}
