package net.aetheriallabs.tutorialmod.item.custom.recipes;

import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import net.aetheriallabs.tutorialmod.util.CraftingSerializers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class SeasonedFoodRecipe extends CustomRecipe {

    TagKey<Item> SEASONABLE_FOOD = ModTags.Items.IS_SEASONABLE_FOOD;
    TagKey<Item> SEASONING = ModTags.Items.IS_SEASONING;
    ItemStack foodStack;

    public SeasonedFoodRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(@NotNull CraftingContainer pInv, Level pLevel) {
        if (pInv.getWidth() == 3 && pInv.getHeight() == 3) {
            for (int i = 0; i < pInv.getWidth(); ++i) {
                for (int j = 0; j < pInv.getHeight(); ++j) {
                    ItemStack itemstack = pInv.getItem(i + j * pInv.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(SEASONING)) {
                            return false;
                        }
                    } else if (!itemstack.is(SEASONABLE_FOOD)) {
                        return false;
                    } else if (itemstack.is(SEASONABLE_FOOD)) {
                        foodStack = foodStack;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public ItemStack assemble(@NotNull CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = pContainer.getItem(1 + pContainer.getWidth());
        if (!itemstack.is(SEASONING)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = foodStack;
            PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
            PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
            return itemstack1;
        }
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        ItemStack food = new ItemStack(Items.BREAD);

        return new ItemStack(Items.BREAD);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CraftingSerializers.SEASONED_FOOD_RECIPE_SERIALIZER.get();
    }

}