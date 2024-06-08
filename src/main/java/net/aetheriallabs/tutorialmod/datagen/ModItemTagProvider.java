package net.aetheriallabs.tutorialmod.datagen;

import net.aetheriallabs.tutorialmod.TutorialMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, TutorialMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //this.tag(Tags.Items.TOOLS).add(ModItems.METAL_DETECTOR.get());
        //The above isn't necessarily functional, but is based off of the Blocks and Ores process. based on what I can
        //tell it adds the METAL_DETECTOR to the Vanilla Tools tag.

    }
}
