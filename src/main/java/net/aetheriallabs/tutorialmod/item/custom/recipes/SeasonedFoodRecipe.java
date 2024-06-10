package net.aetheriallabs.tutorialmod.item.custom.recipes;

import com.mojang.logging.LogUtils;
import net.aetheriallabs.tutorialmod.item.ModItems;
import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.level.Level;
import net.aetheriallabs.tutorialmod.util.CraftingSerializers;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class SeasonedFoodRecipe extends CustomRecipe {

    TagKey<Item> SEASONABLE_FOOD = ModTags.Items.IS_SEASONABLE_FOOD;
    TagKey<Item> SEASONING = ModTags.Items.IS_SEASONING;
    ItemStack foodStack;

    public SeasonedFoodRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {

        for (int i = 0; i < pContainer.getContainerSize(); i++) {

            LogUtils.getLogger().debug("time is: " + pLevel.getGameTime() + ",i is " + i + " And the container size is " + pContainer.getContainerSize());

            if ((i<4 || i>5) && pContainer.getItem(i).is(Items.AIR)) {
                continue;
            } else if (i == 4) {
                LogUtils.getLogger().debug("Container holds: " + pContainer.getItem(4).toString());
                LogUtils.getLogger().debug("Container holds seasoning: " + (pContainer.getItem(i).is(SEASONING)));
                if (!pContainer.getItem(i).is(SEASONING)) return false;

            } else if (i == 5) {
                LogUtils.getLogger().debug("Container holds: " + pContainer.getItem(i).toString());
                LogUtils.getLogger().debug("Container holds seasonable food: " + (pContainer.getItem(i).is(SEASONABLE_FOOD)));

                if (!pContainer.getItem(i).is(SEASONABLE_FOOD)) return false;
                foodStack = pContainer.getItem(i);
            } else {
                return false;
            }
            LogUtils.getLogger().debug("We are past the beef check");
        }
        return true;
    }


    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = pContainer.getItem(4);
        if (!itemstack.is(SEASONING)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(foodStack.getItem(),1);

            ArrayList<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>();

            for(int i = 0; i<itemstack.getFoodProperties(null).getEffects().size(); i++){
                effectList.add(itemstack.getFoodProperties(null).getEffects().get(i).getFirst());
            }

            PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
            PotionUtils.setCustomEffects(itemstack1, effectList);

            return itemstack1;
        }
    }

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