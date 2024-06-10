package net.aetheriallabs.tutorialmod.datagen;

import net.aetheriallabs.tutorialmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static net.aetheriallabs.tutorialmod.TutorialMod.MOD_ID;

public class ModBlockStateProvider extends BlockStateProvider {
    //ModBlockStateProvider typically includes a third parameter of String name, in the context of our mod
    //We delete that parameter from the method, as we know our MOD_ID and will be sending it to the super class.
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper){
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.SAPPHIRE_BLOCK);
        blockWithItem(ModBlocks.RAW_SAPPHIRE_BLOCK);
        blockWithItem(ModBlocks.SAPPHIRE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE);
        blockWithItem(ModBlocks.NETHER_SAPPHIRE_ORE);
        blockWithItem(ModBlocks.END_STONE_SAPPHIRE_ORE);
        blockWithItem(ModBlocks.SOUND_BLOCK);

        stairsBlock(((StairBlock) ModBlocks.SAPPHIRE_STAIRS.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        slabBlock(((SlabBlock) ModBlocks.SAPPHIRE_SLAB.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        buttonBlock(((ButtonBlock) ModBlocks.SAPPHIRE_BUTTON.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.SAPPHIRE_PRESSURE_PLATE.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        fenceBlock(((FenceBlock) ModBlocks.SAPPHIRE_FENCE.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.SAPPHIRE_FENCE_GATE.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));
        doorBlockWithRenderType(((DoorBlock) ModBlocks.SAPPHIRE_DOOR.get()), modLoc("block/sapphire_door_bottom"), modLoc("block/sapphire_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.SAPPHIRE_TRAPDOOR.get()), new ResourceLocation(MOD_ID, "block/sapphire_trapdoor"), true, "cutout");
        wallBlock(((WallBlock) ModBlocks.SAPPHIRE_WALL.get()), blockTexture(ModBlocks.SAPPHIRE_BLOCK.get()));

    }

    public void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
