package net.aetheriallabs.tutorialmod.item.custom;

import net.aetheriallabs.tutorialmod.util.ModTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MetalDetectorItem extends Item {

    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        //This is happening only on the serverside, so make sure you're not checking the client.
        if(!pContext.getLevel().isClientSide()){
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            //The world now goes to -64, which is why the +64 to the Y position of the positionClicked.
            for(int i=0; i<= positionClicked.getY() + 64; i++){
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                if(isValuableBlock(state)){
                    assert player != null;
                    outputValuableCoordinates(positionClicked.below(i), player, state.getBlock());
                    foundBlock = true;
                    break;
                }
            }

            if(!foundBlock){
                assert player != null;
                player.sendSystemMessage(Component.literal("No Ore Found"));
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, Objects.requireNonNull(pContext.getPlayer()),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal(
                "Found " + I18n.get(block.getDescriptionId()) + " at " + "(" + blockPos.getX()
                        + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState state) {
    return state.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES);
    }


        //    private boolean isValuableBlock(BlockState state) {
        //        Block[] valuableblockarray = {Blocks.IRON_ORE,Blocks.GOLD_ORE,Blocks.DIAMOND_ORE,Blocks.COPPER_ORE,Blocks.REDSTONE_ORE,Blocks.LAPIS_ORE};
        //
        //        for (Block block : valuableblockarray) {
        //            if (state.is(block)) {
        //                return true;
        //            }
        //        }
        // TWO EXAMPLES OF FOR LOOPS, THE ABOVE IS AN 'ENHANCED FOR LOOP' AND IS QUITE CLEAN AND USEFUL.
        // for(int i=0; i<valuableblockarray.length; i++)
        //        {
        //            if(state.is(valuableblockarray[i])){
        //                return true;
        //            }
        //        }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.tutorialmod.metal_detector"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
