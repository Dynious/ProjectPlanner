package com.dynious.projectplanner.client.gui;

import com.dynious.projectplanner.lib.Resources;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

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

        int scrollAmount = scroll % (PART_HEIGHT + 1);
        int startIndex = 2*(scroll / (PART_HEIGHT + 1));

        for (int i = 0; i < 10; i++)
        {
            if ((i + startIndex) >= listLength)
                break;

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
        int listHeight = ((listLength/2 + listLength%2)*31);
        scrollBarLength = (SCROLLBAR_MAX_HEIGHT * LIST_SCREEN_HEIGHT) / listHeight;
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
            //TODO: search & update scrollbar
        }
    }
}
