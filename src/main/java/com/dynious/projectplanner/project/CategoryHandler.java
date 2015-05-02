package com.dynious.projectplanner.project;

import codechicken.nei.ItemPanel;
import codechicken.nei.LayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CategoryHandler
{
    public static final CategoryHandler INSTANCE = new CategoryHandler();
    public static final int LIST_SIZE = 50;
    public List<CategorySupplier> supplierList = new ArrayList<CategorySupplier>();

    public List<IProject> internalProjectList = new ArrayList<IProject>();

    public void updateProjectListFromText(String text)
    {
        //TODO: other categories

        LayoutManager.searchField.setText(text);
        LayoutManager.searchField.onTextChange(null);
    }

    public List<IProject> getProjects()
    {
        List<IProject> list = new ArrayList<IProject>(internalProjectList);
        if (list.size() < LIST_SIZE)
        {
            //TODO: this is not the way.
            LayoutManager.itemPanel.resize();
            for (int i = 0; i < LIST_SIZE - list.size() && i < ItemPanel.items.size(); i++)
            {
                list.add(new ItemStackProject(ItemPanel.items.get(i)));
            }
        }
        return list;
    }
}
