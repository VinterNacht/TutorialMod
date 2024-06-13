package net.aetheriallabs.tutorialmod.item.custom.recipes;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.aetheriallabs.tutorialmod.util.CraftingSerializers;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.items.ItemStackHandler;
import org.openjdk.nashorn.internal.runtime.logging.DebugLogger;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.text.html.HTML;
import javax.tools.Tool;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SeasonedFoodRecipe extends CustomRecipe {

    TagKey<Item> SEASONABLE_FOOD = ModTags.Items.IS_SEASONABLE_FOOD;
    TagKey<Item> SEASONING = ModTags.Items.IS_SEASONING;
    ItemStack foodStack;
    Level level;
    int hasSeasoningSlot;

    public SeasonedFoodRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        level = pLevel;
        int ingredients;
        boolean hasSeasoning = false;
        boolean hasSeasonableFood = false;
        ArrayList<Integer> filled = new ArrayList<Integer>();
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack curStack = pContainer.getItem(i);
            if ((i < 3 || i > 4) && curStack.is(Items.AIR)) {
                continue;
            } else if (i == 4) {
//                LogUtils.getLogger().debug("Container holds: " + pContainer.getItem(4).toString());
//                LogUtils.getLogger().debug("Container holds seasoning: " + (curStack.is(SEASONING)));
                if (!curStack.is(SEASONING)) return false;

            } else if (i == 3) {
//                LogUtils.getLogger().debug("Container holds: " + curStack.toString());
//                LogUtils.getLogger().debug("Cur Slot " + i + " holds seasonable food: " + (curStack.is(SEASONABLE_FOOD)));
//                LogUtils.getLogger().debug("Slot 4 holds seasoning: " + (pContainer.getItem(4).is(SEASONING)));
                if (!curStack.is(SEASONABLE_FOOD) || !pContainer.getItem(4).is(SEASONING)
                        || willBeOverseasoned(curStack, pContainer.getItem(4))) return false;
                foodStack = pContainer.getItem(i);
            } else {
                return false;
            }
            LogUtils.getLogger().debug("matches() CheckPoint Alpha");
        }
        return true;
    }

    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = pContainer.getItem(4);
        if (!itemstack.is(SEASONING)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = foodStack.copyWithCount(1);
            CompoundTag seasonedTag = itemstack1.getTagElement("seasoned_food_data");
            itemstack1.setHoverName(Component.literal(Component.translatable("seasoned").getString() + " " + Component.translatable(foodStack.getDescriptionId()).getString()));
            seasonedTag.putInt("max_seasonings", 2);
            // Add scale based on nutrition and/or satiation.
            seasonedTag.putInt("times_seasoned", seasonedTag.getInt("times_seasoned") + 1);
            seasonedTag.putBoolean("seasoned", true);

//            TODO: add list of seasonings that can be referenced
            ListTag seasonedWithList =  seasonedTag.getList("seasoned_with", 9);
            if(seasonedWithList == null) {
                seasonedTag.put("seasoned_with", new ListTag());
            }



            ArrayList<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>();
            for (int i = 0; i < itemstack.getFoodProperties(null).getEffects().size(); i++) {
                //effectList.add(itemstack.getFoodProperties(null).getEffects().get(i).getFirst());
            }

            //TODO: Learn about adding and removing tags
              setSeasoningEffects(itemstack1, effectList);

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

    public static ItemStack setSeasoningEffects(ItemStack pStack, Collection<MobEffectInstance> pEffects) {
        if (pEffects.isEmpty()) {
            return pStack;
        } else {
            CompoundTag compoundtag = pStack.getOrCreateTagElement("seasoned_food_data");
            ListTag listtag = compoundtag.getList("CustomSeasoningEffects", 9);

            for(MobEffectInstance mobeffectinstance : pEffects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            pStack.getTagElement("seasoned_food_data").put("CustomSeasoningEffects", listtag);
            return pStack;
        }
    }


    public boolean willBeOverseasoned(ItemStack foodStack, ItemStack seasoningStack){

        CompoundTag foodTag = foodStack.getOrCreateTagElement("seasoned_food_data");
        ListTag isSeasonedWith = foodTag.getList("seasoned_with", 9);

        //Find out if this seasoning has already been used on this food
        if(foodTag != null) {
           if (isSeasonedWith != null) {
                for (Tag tag : isSeasonedWith) {
                    String stackName = Component.translatable(seasoningStack.getDescriptionId()).toString();
                    if (tag.toString() == stackName) {
                        LogUtils.getLogger().debug("willBeOverseasoned() checkpoint Alpha");
                        return false;
                    }
                }
            }
           if(foodTag.getInt("max_seasonings") <= foodTag.getInt("times_seasoned")){
                LogUtils.getLogger().debug("willBeOverseasoned() checkpoint Beta");
                return true;
           }
        };
            return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CraftingSerializers.SEASONED_FOOD_RECIPE_SERIALIZER.get();
    }
}
