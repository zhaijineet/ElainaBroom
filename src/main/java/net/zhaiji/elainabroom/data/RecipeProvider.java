package net.zhaiji.elainabroom.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.zhaiji.elainabroom.init.InitItem;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {


    public RecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, InitItem.ELAINA_BROOM.get())
                .pattern(" AB")
                .pattern(" CD")
                .pattern("E  ")
                .define('A', Items.GHAST_TEAR)
                .define('B', Blocks.HAY_BLOCK)
                .define('C', Items.PURPLE_WOOL)
                .define('D', Items.PHANTOM_MEMBRANE)
                .define('E', Items.STICK)
                .unlockedBy("has_phantom_membrane", has(Items.PHANTOM_MEMBRANE))
                .save(consumer);
    }
}
