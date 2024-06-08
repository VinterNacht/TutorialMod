package net.aetheriallabs.tutorialmod.block;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.block.custom.SoundBlock;
import net.aetheriallabs.tutorialmod.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID); //Create a list of Items from the mod.


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
                    new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_ORE =
            registerBlock("sapphire_ore", () ->
                    new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).
                            strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3,6)));
        //strength indicates how long a block takes to break. Default stone is at 1.5f, UnifformInt.of(min,Max)
    // is the minimum and maximum XP the block will drop.

    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE =
            registerBlock("deepslate_sapphire_ore", () ->
                    new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).
                            strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3,6)));
    public static final RegistryObject<Block> NETHER_SAPPHIRE_ORE =
            registerBlock("nether_sapphire_ore", () ->
                    new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).
                            strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3,6)));
    public static final RegistryObject<Block> END_STONE_SAPPHIRE_ORE =
            registerBlock("end_stone_sapphire_ore", () ->
                    new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).
                            strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3,6)));

    //Custom Blocks Registered Below
    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block", () ->
            new SoundBlock(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> SAPPHIRE_STAIRS =
            registerBlock("sapphire_stairs", () ->
                    new StairBlock(() -> ModBlocks.SAPPHIRE_BLOCK.get().defaultBlockState(),
                            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SAPPHIRE_SLAB =
            registerBlock("sapphire_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_BUTTON =
            registerBlock("sapphire_button", () ->
                    new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST),
                            BlockSetType.IRON, 10, true ));
    public static final RegistryObject<Block> SAPPHIRE_PRESSURE_PLATE =
            registerBlock("sapphire_pressure_plate", () ->
                    new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST), BlockSetType.IRON));

    public static final RegistryObject<Block> SAPPHIRE_FENCE =
            registerBlock("sapphire_fence", () ->
                    new FenceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SAPPHIRE_FENCE_GATE =
            registerBlock("sapphire_fence_gate", () ->
                    new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST),
                            SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));
    public static final RegistryObject<Block> SAPPHIRE_WALL =
            registerBlock("sapphire_wall", () ->
                    new WallBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> SAPPHIRE_DOOR =
            registerBlock("sapphire_door", () ->
                    new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noOcclusion(), BlockSetType.IRON));
    public static final RegistryObject<Block> SAPPHIRE_TRAPDOOR =
            registerBlock("sapphire_trapdoor", () ->
                    new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noOcclusion(),BlockSetType.IRON));
    //The noOcclusion property included above indicates that it will be possible to see through this block if there are empty spaces in it's texture,
    //Think windows, some doors, some trapdoors, etc. In this case, both the door and trapdoor had to use the <block>WithRenderType version of the
    //methods used to ??register?? them.

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    } //This method is used to actually register the Block and calls the registerBlockItem method we wrote below.

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus); //register ITEMS using the eventBus. This registers the DeferredRegister to the modlist.
    }
}
