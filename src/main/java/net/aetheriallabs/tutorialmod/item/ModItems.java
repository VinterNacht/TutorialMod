package net.aetheriallabs.tutorialmod.item;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.item.custom.MetalDetectorItem;
import net.minecraft.world.item.Item;
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


    //Food registers here as a regular item, as it's a type of Item, with additional properties. At the end, you send
    //the Food Object reference as an argument to the .food() method in Item.Properties() to set its properties.

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus); //register ITEMS using the eventBus. This registers the DeferredRegister to the modlist.
    }


}
