package net.aetheriallabs.tutorialmod.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

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
        Block[] valuableblockarray = {Blocks.IRON_ORE,Blocks.GOLD_ORE,Blocks.DIAMOND_ORE,Blocks.COPPER_ORE,Blocks.REDSTONE_ORE,Blocks.LAPIS_ORE};

        for (Block block : valuableblockarray) {
            if (state.is(block)) {
                return true;
            }
        }

        //THe above is an enhanced for loop, is the equivalent of below
        //I read the above as "For each block in variableblockarray" Where Block block both declares the variable
        //name and type, and defines the statement. ":" is "for each in".  Definitely a cleaner implementation than
        //below.
        // for(int i=0; i<valuableblockarray.length; i++)
        //        {
        //            if(state.is(valuableblockarray[i])){
        //                return true;
        //            }
        //        }

        return false;

        //return state.is(Blocks.IRON_ORE);
    }
}
