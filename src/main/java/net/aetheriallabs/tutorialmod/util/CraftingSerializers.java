package net.aetheriallabs.tutorialmod.util;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.item.custom.recipes.SeasonedFoodRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CraftingSerializers {
    public static final DeferredRegister<RecipeSerializer> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryName(), TutorialMod.MOD_ID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer> SEASONED_FOOD_RECIPE_SERIALIZER =
            SERIALIZERS.register("seasoned_recipe_serializer", () ->
                    new SimpleCraftingRecipeSerializer<>(SeasonedFoodRecipe::new));

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}