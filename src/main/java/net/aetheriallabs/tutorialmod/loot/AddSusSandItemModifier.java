package net.aetheriallabs.tutorialmod.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;
//From video https://www.youtube.com/watch?v=qVljyY9rXmc&list=PLKGarocXCE1H9Y21-pxjt5Pt8bW14twa-&index=21
public class AddSusSandItemModifier extends LootModifier {
    public static final Supplier<Codec<AddSusSandItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, AddSusSandItemModifier::new)));

    private final Item item;


    public AddSusSandItemModifier(LootItemCondition[] conditionsIn, Item item){
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
        }

        //Only the first item generated in loot for suspicious sand will generate when harvested, the rest will be lost.
        if(context.getRandom().nextFloat() <0.5) {
            //This number should generally be under 17%, 50% WAY TOO HIGH
            //Best way to find % is to got to Gradle: net.minecraft.client:extra:1.20.1/data/minecraft/loot_tables/archaeology
            //Find the structure you want to add it to, divide 100 by the number of entries (+yours), and that's the % you want, roughly.
            generatedLoot.clear(); //Clears the rest of the drop table on % pass
            generatedLoot.add(new ItemStack(this.item)); //Generates our loot

        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
