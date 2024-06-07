package net.aetheriallabs.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


public class SoundBlock extends Block {

    public SoundBlock(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide() && pPlayer.getItemInHand(pHand) == ItemStack.EMPTY) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.BLOCKS, 1f, 1f);
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);

        }
        return InteractionResult.sidedSuccess(false);
    }

}
