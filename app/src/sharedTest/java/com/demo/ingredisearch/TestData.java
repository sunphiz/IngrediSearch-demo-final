package com.demo.ingredisearch;

import com.demo.ingredisearch.models.Recipe;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final Recipe recipe1 = new Recipe(
            "1af01c",
            "Cakespy: Cadbury Creme Deviled Eggs",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/20110321142792deviledcreme26d51.jpg",
            "http://www.seriouseats.com/recipes/2011/03/cakespy-cadbury-creme-deviled-eggs-easter.html",
            new String[]{},
            99.9999F,
            false
    );
    public static final Recipe recipe2 = new Recipe(
            "1cea66",
            "Poached Eggs in Tomato Sauce with Chickpeas and Feta",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/3689636539.jpg",
            "http://www.epicurious.com/recipes/food/views/Poached-Eggs-in-Tomato-Sauce-with-Chickpeas-and-Feta-368963",
            new String[]{},
            99.9999F,
            false
    );
    public static final Recipe recipe3 = new Recipe(
            "10aae5",
            "Cakespy: Cadbury Creme Eggs Benedict",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/20100301cremebenedicthow968f.jpg",
            "http://www.seriouseats.com/recipes/2010/03/cakespy-cadbury-creme-eggs-benedict-dessert-breakfast-recipe.html",
            new String[]{},
            99.9999F,
            false
    );
    public static final Recipe recipe4 = new Recipe(
            "35424",
            "Mexican Baked Eggs",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/Mexican2BBaked2BEggs2B5002B685320aa856d.jpg",
            "http://www.closetcooking.com/2011/09/mexican-baked-eggs.html",
            new String[]{},
            99.9999F,
            false
    );

    public static final Recipe recipe1_favored = new Recipe(recipe1);
    public static final Recipe recipe2_favored = new Recipe(recipe2);
    static {
        recipe1_favored.setFavorite(true);
        recipe2_favored.setFavorite(true);
    }

    public static List<Recipe> mRecipes = Arrays.asList(recipe1, recipe2, recipe3, recipe4);
    public static List<Recipe> mFavorites = Arrays.asList(recipe1_favored, recipe2_favored);
    public static List<Recipe> mRecipesWithNoFavorites = Arrays.asList(recipe3, recipe4);
    public static List<Recipe> mRecipesWithFavorites = Arrays.asList(recipe1, recipe3, recipe4);

    public static final Recipe recipeDetails01 = new Recipe(
            "1af01c",
            "Cakespy: Cadbury Creme Deviled Eggs",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/20110321142792deviledcreme26d51.jpg",
            "http://www.seriouseats.com/recipes/2011/03/cakespy-cadbury-creme-deviled-eggs-easter.html",
            new String[]{"4 Cadbury Creme Eggs, chilled for 1 hour",
                    "1/2 cup vanilla buttercream, colored yellow with food coloring",
                    "red sprinkles, to garnish"},
            99.9999F,
            false);

    public static final Recipe recipeDetails02 = new Recipe(
            "1cea66",
            "Poached Eggs in Tomato Sauce with Chickpeas and Feta",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/3689636539.jpg",
            "http://www.epicurious.com/recipes/food/views/Poached-Eggs-in-Tomato-Sauce-with-Chickpeas-and-Feta-368963",
            new String[]{
                    "1/4 cup olive oil",
                    "1 medium onion, finely chopped",
                    "4 garlic cloves, coarsely chopped",
                    "2 jalapeos, seeded, finely chopped",
                    "1 15-ounce can chickpeas, drained",
                    "2 teaspoons Hungarian sweet paprika",
                    "1 teaspoon ground cumin",
                    "1 28-ounce can whole peeled tomatoes, crushed by hand, juices reserved",
                    "Kosher salt and freshly ground black pepper",
                    "1 cup coarsely crumbled feta",
                    "8 large eggs",
                    "1 tablespoon chopped flat-leaf parsley",
                    "1 tablespoon chopped fresh cilantro",
                    "Warm pita bread"
            },
            99.9999F,
            false
    );

    public static final Recipe recipeDetails03 = new Recipe(
            "35424",
            "Mexican Baked Eggs",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/Mexican2BBaked2BEggs2B5002B685320aa856d.jpg",
            "http://www.closetcooking.com/2011/09/mexican-baked-eggs.html",
            new String[]{
                    "1 tablespoon corn oil",
                    "1 small onion, diced",
                    "2 cloves garlic, chopped",
                    "1 teaspoon cumin, toasted and ground",
                    "1 teaspoon chipotle chili powder",
                    "2 jalapeno peppers, diced",
                    "1 (28 ounce) can diced tomatoes or 4 cups diced fresh tomatoes",
                    "1 (19 ounce) can black beans, rinsed and drained",
                    "1 teaspoon oregano",
                    "salt and pepper to taste",
                    "1 handful cilantro, chopped",
                    "4 eggs",
                    "1/2 cup grated cheese such as jack and cheddar\n"
            },
            99.9999F,
            false
    );

    public static final Recipe recipeDetails04 = new Recipe(
            "2226",
            "Bacon Cheddar Deviled Eggs",
            "https://res.cloudinary.com/dk4ocuiwa/image/upload/v1575163942/RecipesApi/9998886566.jpg",
            "http://allrecipes.com/Recipe/Bacon-Cheddar-Deviled-Eggs/Detail.aspx",
            new String[]{
                    "12 eggs",
                    "1/2 cup mayonnaise",
                    "4 slices bacon",
                    "2 tablespoons finely shredded Cheddar cheese",
                    "1 tablespoon mustard"
            },
            99.9999F,
            false
    );
}
