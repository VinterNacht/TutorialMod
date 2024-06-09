package net.aetheriallabs.tutorialmod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;


public class FuelItem extends Item {
    private int burnTime = 0;
    public FuelItem(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }

    //In order to be usable as a fuel item, it's necessary for objects like furnaces/etc to have a way
    //to grab the items burnTime. The value of the fuel item is set during registration in ModItems, rather than
    //above to allow this class to be used to add as many fuel items as I want. This is, in essence, a custom class.
    //Interestingly, the vanilla items don't seem to follow this process when they are defining the items, and coal
    //is just another item. Will look at the furnace and the item class to see how its implemented there.

}
