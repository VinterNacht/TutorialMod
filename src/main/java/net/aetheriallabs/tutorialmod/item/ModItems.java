package net.aetheriallabs.tutorialmod.item;

import com.google.common.util.concurrent.ClosingFuture;
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
    public static final RegistryObject<MetalDetectorItem> METAL_DETECTOR =
            ITEMS.register("metal_detector", () -> new MetalDetectorItem(new MetalDetectorItem.Properties().durability(100)));

    //BlockItem Registry ??


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus); //register ITEMS using the eventBus. This registers the DeferredRegister to the modlist.
    }


}
