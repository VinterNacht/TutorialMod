package net.aetheriallabs.tutorialmod.item.custom.recipes;

import com.mojang.logging.LogUtils;
import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.aetheriallabs.tutorialmod.util.CraftingSerializers;

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
        ItemStack seasoningStack = pContainer.getItem(4);
        ItemStack canBeSeasonedStack = pContainer.getItem(3);
        ArrayList<Integer> filled = new ArrayList<Integer>();
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack curStack = pContainer.getItem(i);
            if ((i < 3 || i > 4)) {
                if(curStack.is(Items.AIR)) continue;
                return false;
            } else if (i == 4) {
                LogUtils.getLogger().debug("Container holds: " + seasoningStack.toString());
                LogUtils.getLogger().debug("Container holds seasoning: " + (curStack.is(SEASONING)));
                if (!curStack.is(SEASONING)) return false;

            } else if (i == 3) {
//                LogUtils.getLogger().debug("Container holds: " + curStack.toString());
//                LogUtils.getLogger().debug("Cur Slot " + i + " holds seasonable food: " + (curStack.is(SEASONABLE_FOOD)));
//                LogUtils.getLogger().debug("Slot 4 holds seasoning: " + (pContainer.getItem(4).is(SEASONING)));
                if (!curStack.is(SEASONABLE_FOOD) || !seasoningStack.is(SEASONING)
                        || willBeOverseasoned(curStack, pContainer.getItem(4))) return false;

                //TODO: add list of seasonings that can be referenced
                CompoundTag seasonedTag = canBeSeasonedStack.getTagElement("seasoned_food_data");
                //if(seasonedTag.size()!=0){
                    if(hasUsedSeasoning(seasoningStack, canBeSeasonedStack)) return false;
                //}
                foodStack = pContainer.getItem(i);
            } else {
                return false;
            }
            LogUtils.getLogger().debug("matches() CheckPoint Alpha");
        }
        return true;
    }

    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack seasoningStack = pContainer.getItem(4);
        if (!seasoningStack.is(SEASONING)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack outputStack = foodStack.copyWithCount(1);
            CompoundTag seasonedTag = outputStack.getTagElement("seasoned_food_data");
            outputStack.setHoverName(Component.literal(Component.translatable("seasoned").getString() + " " + Component.translatable(foodStack.getDescriptionId()).getString()));
            seasonedTag.putInt("max_seasonings", 2);
            //TODO: Add scale based on nutrition and/or satiation.
            seasonedTag.putInt("times_seasoned", seasonedTag.getInt("times_seasoned") + 1);
            seasonedTag.putBoolean("seasoned", true);

            //TODO: create ListTag of MobEffectInstance
            ArrayList<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>();
            for (int i = 0; i < seasoningStack.getFoodProperties(null).getEffects().size(); i++) {
                //effectList.add(itemstack.getFoodProperties(null).getEffects().get(i).getFirst());
            }

            //TODO: Learn about adding and removing tags
            setSeasoningEffects(outputStack, effectList);

            return outputStack;
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


    public boolean willBeOverseasoned(ItemStack foodStack, ItemStack seasoningStack) {

        CompoundTag foodTag = foodStack.getOrCreateTagElement("seasoned_food_data");
        ListTag isSeasonedWith = foodTag.getList("seasoned_with", 9);

        int maxSeasonings = foodTag.getInt("max_seasonings");
        int timesSeasoned = foodTag.getInt("times_seasoned");

        if (!(maxSeasonings == 0) && (maxSeasonings < timesSeasoned)) {
            LogUtils.getLogger().debug("willBeOverseasoned() checkpoint Beta");
            LogUtils.getLogger().debug("Max Seasonings Currently " + maxSeasonings);
            return true;
        }

        return false;
    }

    public boolean hasUsedSeasoning(ItemStack seasoningStack, ItemStack seasonedStack){
        CompoundTag compoundSeasoned = seasonedStack.getTag();
        ListTag seasonings;
        LogUtils.getLogger().debug("CompoundSeasoned Contains seasoned_with " + compoundSeasoned.contains("seasoned_with", 8));
        if (compoundSeasoned != null || compoundSeasoned.contains("seasoned_with", 8)) {
            seasonings = compoundSeasoned.getList("seasoned_with", 8);

            for (Tag tag : seasonings) {
                String stackName = seasoningStack.getDescriptionId().toString();
                if (tag.toString() == stackName) {
                    LogUtils.getLogger().debug("hasUsedSeasoning() checkpoint Alpha");
                    return true;
                }
            }
        }

        CompoundTag seasoning = new CompoundTag();
        seasoning.putString(Component.translatable(seasoningStack.getDescriptionId()).getString(), seasoningStack.getDescriptionId().toString());
        compoundSeasoned.put("seasoned_with", seasoning);
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CraftingSerializers.SEASONED_FOOD_RECIPE_SERIALIZER.get();
    }
}
