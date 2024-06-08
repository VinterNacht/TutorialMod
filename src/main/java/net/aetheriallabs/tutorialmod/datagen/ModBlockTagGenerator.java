package net.aetheriallabs.tutorialmod.datagen;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.block.ModBlocks;
import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //Detectable By Metal Detector
        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
                .add(ModBlocks.SAPPHIRE_ORE.get(),
                    ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                    ModBlocks.END_STONE_SAPPHIRE_ORE.get()
                ).addTag(Tags.Blocks.ORES);

        //Needs Stone Tools
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.NETHER_SAPPHIRE_ORE.get());
        
        //Needs Iron Tools
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SAPPHIRE_ORE.get(),
                    ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                    ModBlocks.SAPPHIRE_BLOCK.get(),
                    ModBlocks.RAW_SAPPHIRE_BLOCK.get(),
                    ModBlocks.SAPPHIRE_BUTTON.get(),
                    ModBlocks.SAPPHIRE_DOOR.get(),
                    ModBlocks.SAPPHIRE_FENCE.get(),
                    ModBlocks.SAPPHIRE_FENCE_GATE.get(),
                    ModBlocks.SAPPHIRE_STAIRS.get(),
                    ModBlocks.SAPPHIRE_SLAB.get(),
                    ModBlocks.SAPPHIRE_TRAPDOOR.get(),
                    ModBlocks.SAPPHIRE_WALL.get());
        
        //Needs Diamond Tools
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get());
        
        //Needs Pickaxe
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SAPPHIRE_BLOCK.get(),
                    ModBlocks.NETHER_SAPPHIRE_ORE.get(),
                    ModBlocks.SAPPHIRE_ORE.get(),
                    ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
                    //ModBlocks.SAPPHIRE_BLOCK.get(),
                    ModBlocks.RAW_SAPPHIRE_BLOCK.get(),
                    ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
                    ModBlocks.SOUND_BLOCK.get());

        this.tag(ModTags.Blocks.NEEDS_SAPPHIRE_TOOL)
                .add(ModBlocks.SOUND_BLOCK.get(),
                    ModBlocks.SAPPHIRE_BLOCK.get());

        //Is a Fence, Wall, or Fence Gate. This tag ensures they'll connect with each other
        this.tag(BlockTags.FENCES)
                .add(ModBlocks.SAPPHIRE_FENCE.get());
        
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.SAPPHIRE_FENCE_GATE.get());
        
        this.tag(BlockTags.WALLS)
                .add(ModBlocks.SAPPHIRE_WALL.get());
    }
}
