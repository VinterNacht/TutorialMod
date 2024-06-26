package net.aetheriallabs.tutorialmod.item;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.block.ModBlocks;
import net.aetheriallabs.tutorialmod.item.custom.FuelItem;
import net.aetheriallabs.tutorialmod.item.custom.MetalDetectorItem;
import net.aetheriallabs.tutorialmod.item.custom.ModArmorItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID); //Create a list of Items from the mod.

    //Item Registry
    public static final RegistryObject<Item> SAPPHIRE =
            ITEMS.register( "sapphire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_SAPPHIRE =
            ITEMS.register("raw_sapphire", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> METAL_DETECTOR =
            ITEMS.register("metal_detector", () ->
                    new MetalDetectorItem(new MetalDetectorItem.Properties().durability(100)));
    public static final RegistryObject<Item> PINE_CONE =
            ITEMS.register("pine_cone", () -> new FuelItem(new Item.Properties(), 400));

    public static final RegistryObject<Item> STRAWBERRY =
            ITEMS.register( "strawberry", () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));

    public static final RegistryObject<Item> SAPPHIRE_STAFF =
            ITEMS.register( "sapphire_staff", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(ModToolTiers.SAPPHIRE, 4, 2, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SAPPHIRE, 1, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(ModToolTiers.SAPPHIRE, 7, 1, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel",
            () ->  new ShovelItem(ModToolTiers.SAPPHIRE, 0, 0, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(ModToolTiers.SAPPHIRE, 1, 1, new Item.Properties()));

    public static final RegistryObject<Item> SAPPHIRE_HELMET = ITEMS.register("sapphire_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_CHESTPLATE = ITEMS.register("sapphire_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_LEGGINGS = ITEMS.register("sapphire_leggings",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_BOOTS = ITEMS.register("sapphire_boots",
            () -> new ModArmorItem(ModArmorMaterials.SAPPHIRE, ArmorItem.Type.BOOTS, new Item.Properties()));
    //Food registers here as a regular item, as it's a type of Item, with additional properties. At the end, you send
    //the Food Object reference as an argument to the .food() method in Item.Properties() to set its properties.

    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));
    //ItemNameBlockItem is used because this represents a block. As an ItemNameBlockItem we're able to list the
    //strawberry seeds in the lang file as item.tutorialmod.strawberry_seeds as usual.  Otherwise it would have
    //to be block.tutorialmod.strawberry_crop, which would be confusing to say the least, and confound the two
    //when looking at them. Strawberry Crops would be "strawberry seeds" or Strawberry seeds would be "Strawberry Crops."
    //Obviously not ideal.

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        //register ITEMS using the eventBus. This registers the DeferredRegister to the modlist.
    }


}
