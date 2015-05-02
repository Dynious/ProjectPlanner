package com.dynious.projectplanner.client.gui;

import codechicken.nei.LayoutManager;
import com.dynious.projectplanner.lib.Resources;
import com.dynious.projectplanner.project.CategoryHandler;
import com.dynious.projectplanner.project.CategorySupplier;
import com.dynious.projectplanner.project.IProject;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class GuiProjectPlanner extends GuiProjectPlannerBase
{
    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 166;

    public final static int PART_WIDTH = 73;
    public final static int PART_HEIGHT = 30;
    public final static int SCROLLBAR_WIDTH = 7;
    public final static int SCROLLBAR_MAX_HEIGHT = 129;
    public final static int SCROLLBAR_X_START_POS = 160;
    public final static int SCROLLBAR_Y_START_POS = 27;
    public final static int LIST_SCREEN_HEIGHT = 128;

    private int listLength = 25;
    private int scroll = 0;
    private int scrollBarLength = 20;
    private int scrollBarXPos;
    private int scrollBarYPos;
    private int mouseClickY = -1;

    private GuiTextField textField;

    public GuiProjectPlanner()
    {
        recalculateScrollBar();
    }

    @Override
    public void initGui()
    {
        super.initGui();

        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;

        textField = new GuiTextField(fontRendererObj, xPos + 25, yPos + 9, 128, 12);
        textField.setFocused(true);
        textField.setCanLoseFocus(false);
        textField.setText(LayoutManager.searchField.text());
        CategoryHandler.INSTANCE.updateProjectListFromText(textField.getText());

        scrollBarXPos = xPos + SCROLLBAR_X_START_POS;
        scrollBarYPos = yPos + SCROLLBAR_Y_START_POS;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Resources.GUI_PROJECT_PLANNER);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        checkMouseMovement(yPos, mouseY);

        List<IProject> projectList = CategoryHandler.INSTANCE.getProjects();
        if (listLength != projectList.size())
        {
            listLength = projectList.size();
            recalculateScrollBar();
        }

        int scrollAmount = scroll % (PART_HEIGHT + 1);
        int startIndex = 2*(scroll / (PART_HEIGHT + 1));

        for (int i = 0; i < 10; i++)
        {
            if ((i + startIndex) >= listLength)
                break;

            IProject project = projectList.get(startIndex + i);

            int x = 10 + (i % 2 == 1 ? 74 : 0);
            int y = 28 + ((i / 2) * 31) - scrollAmount;

            if (y >= 27)
            {
                drawTexturedModalRect(xPos + x, yPos + y, 178, 0, PART_WIDTH, Math.min(PART_HEIGHT, ySize - y - 9));
            }
            else
            {
                drawTexturedModalRect(xPos + x, yPos + 27, 178, 27 - y, PART_WIDTH, Math.min(PART_HEIGHT, ySize - y - 9));
            }
        }

        drawRect(xPos + SCROLLBAR_X_START_POS, scrollBarYPos, xPos + SCROLLBAR_X_START_POS + SCROLLBAR_WIDTH, scrollBarYPos + scrollBarLength, 0xFFAAAAAA);

        super.drawScreen(mouseX, mouseY, partialTick);
        textField.drawTextBox();

        for (int i = 0; i < 10; i++)
        {
            if ((i + startIndex) >= listLength)
                break;

            IProject project = projectList.get(startIndex + i);

            int x = 10 + (i % 2 == 1 ? 74 : 0);
            int y = 28 + ((i / 2) * 31) - scrollAmount;

            if (y >= 27)
            {
                drawObject(project.getIconObject(), xPos + x + 5, yPos + y + 5);
            }
            else
            {
                drawObject(project.getIconObject(), xPos + x + 5, yPos + y + 5);
            }
        }
    }

    private void checkMouseMovement(int yPos, int mouseY)
    {
        if (mouseClickY != -1 && mouseY != mouseClickY)
        {
            scrollBarYPos += mouseY - mouseClickY;
            if (scrollBarYPos < yPos + SCROLLBAR_Y_START_POS)
                scrollBarYPos = yPos + SCROLLBAR_Y_START_POS;
            else if (scrollBarYPos > yPos + SCROLLBAR_Y_START_POS + SCROLLBAR_MAX_HEIGHT - scrollBarLength)
                scrollBarYPos = yPos + SCROLLBAR_Y_START_POS + SCROLLBAR_MAX_HEIGHT - scrollBarLength;

            int listHeight = ((listLength/2 + listLength%2)*31);
            scroll = -((yPos + SCROLLBAR_Y_START_POS - scrollBarYPos) * listHeight) / LIST_SCREEN_HEIGHT;
            mouseClickY = mouseY;
        }
    }

    private void recalculateScrollBar()
    {
        if (listLength < 8)
        {
            scrollBarLength = SCROLLBAR_MAX_HEIGHT;
            return;
        }
        int listHeight = ((listLength/2 + listLength%2)*31);
        scrollBarLength = (SCROLLBAR_MAX_HEIGHT * LIST_SCREEN_HEIGHT) / listHeight;
    }

    private void drawObject(Object object, int x, int y)
    {
        if (object instanceof IIcon)
        {
            drawTexturedModelRectFromIcon(x, y, (IIcon) object, 16, 16);
        }
        else
        {
            ItemStack stack;
            if (object instanceof ItemStack)
                stack = (ItemStack) object;
            else if (object instanceof Item)
                stack = new ItemStack((Item) object);
            else if (object instanceof Block)
                stack = new ItemStack((Block) object);
            else
                return;

            this.zLevel = 100.0F;
            itemRender.zLevel = 100.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x, y);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.zLevel = 0.0F;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int type)
    {
        super.mouseClicked(mouseX, mouseY, type);
        if (mouseX >= scrollBarXPos && mouseX <= scrollBarXPos + SCROLLBAR_WIDTH && mouseY >= scrollBarYPos && mouseY <= scrollBarYPos + scrollBarLength)
        {
            mouseClickY = mouseY;
        }
        textField.mouseClicked(mouseX, mouseY, type);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int type)
    {
        super.mouseMovedOrUp(mouseX, mouseY, type);
        if (type != -1 && mouseClickY != -1)
        {
            mouseClickY = -1;
        }
    }

    @Override
    protected void keyTyped(char character, int p_73869_2_)
    {
        super.keyTyped(character, p_73869_2_);

        if (textField.textboxKeyTyped(character, p_73869_2_))
        {
            CategoryHandler.INSTANCE.updateProjectListFromText(textField.getText());
        }
    }
}
