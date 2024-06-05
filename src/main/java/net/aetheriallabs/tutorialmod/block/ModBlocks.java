package net.aetheriallabs.tutorialmod.block;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID); //Create a list of Items from the mod.

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    } //This method is used to actually register the Block and calls the registerBlockItem method we wrote below.

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    //Explanation of the above: <T Extends Block> This generic declares that, whatever is sent to this method must be some kind of block.
    //What type of block is irrelevant, so long as it extends the base class of Block, or is of the class Block. We're sending it the name
    //of the block and the block itself as arguments. It will then register a BlockItem for that block.  Because minecraft can't handle just
    //having a block in your inventory... It's weak like that.

    //Variables creating Blocks To Register.
    public static final RegistryObject<Block> SAPPHIRE_BLOCK =
            registerBlock("sapphire_block", () ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK =
            registerBlock("raw_sapphire_block", () ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).sound(SoundType.CORAL_BLOCK)));


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus); //register ITEMS using the eventBus. This registers the DeferredRegister to the modlist.
    }
}
