package net.aetheriallabs.tutorialmod.util;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.aetheriallabs.tutorialmod.TutorialMod;
import net.aetheriallabs.tutorialmod.item.custom.recipes.SeasonedFoodRecipe;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.advancements.critereon.MobEffectsPredicate.effects;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class SeasonedFoodEvents {


    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && event.getItem().is(ModTags.Items.IS_SEASONABLE_FOOD)) {
            if(!player.level().isClientSide) onEat(player, event.getItem());
        }
    }

    public void onEat(Player player, ItemStack item) {
        ArrayList<MobEffectInstance> effects = new ArrayList<MobEffectInstance>(PotionUtils.getCustomEffects(item));
        //TODO: Implement getSeasoningEffects Method
        //if (SeasonedFoodRecipe.getSeasoningEffects(item).isEmpty()) {
            for (int i = 0; i < effects.size(); i++) {
                player.addEffect(effects.get(i)
                );
            }
        //}
    }

    @SubscribeEvent
    public void onRenderItemToolTipEvent(RenderTooltipEvent.GatherComponents event) {
        event.getTooltipElements().add(Either.left(FormattedText.of("Seasoned with: ", Style.EMPTY.withColor(TextColor.parseColor("#008080")).withBold(false))));

    }
}