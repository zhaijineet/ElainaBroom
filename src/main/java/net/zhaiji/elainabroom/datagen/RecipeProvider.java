package net.zhaiji.elainabroom.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.zhaiji.elainabroom.init.InitItem;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    protected RecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, InitItem.ELAINA_BROOM)
                .pattern(" AB")
                .pattern(" CD")
                .pattern("E  ")
                .define('A', Items.GHAST_TEAR)
                .define('B', Blocks.HAY_BLOCK)
                .define('C', Items.PURPLE_WOOL)
                .define('D', Items.PHANTOM_MEMBRANE)
                .define('E', Items.STICK)
                .unlockedBy("has_phantom_membrane", has(Items.PHANTOM_MEMBRANE))
                .save(this.output);
    }

    public static class Runner extends net.minecraft.data.recipes.RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        public String getName() {
            return "ElainaBroom Recipes";
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RecipeProvider(registries, output);
        }
    }
}
