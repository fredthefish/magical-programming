package net.meep.magicprogramming.gui;


import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class WandScreen extends BaseOwoHandledScreen<FlowLayout, WandScreenHandler> {
    String spell = "";

    public WandScreen(WandScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        try {
            spell = handler.buf.readString();
        } catch (Exception ignored) { }
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.surface(Surface.VANILLA_TRANSLUCENT);
        rootComponent.horizontalAlignment(HorizontalAlignment.CENTER);
        rootComponent.verticalAlignment(VerticalAlignment.CENTER);
        TextAreaComponent textField = Components.textArea(Sizing.fill(60), Sizing.fill(60));
        textField.setMaxLength(32767); //Maximum allowed.
        rootComponent.child(textField);
        textField.setText(spell);

        textField.onChanged().subscribe(handler::changeText);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_E) {
            return false;
        } else return super.keyPressed(keyCode, scanCode, modifiers);
    }
}