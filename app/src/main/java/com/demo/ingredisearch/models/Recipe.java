package com.demo.ingredisearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Objects;

public class Recipe implements Parcelable {
    private String recipe_id;
    private String title;
    private String image_url;
    private String source_url;
    private String[] ingredients;
    private float social_rank;
    private boolean isFavorite;

    public Recipe(@NonNull String recipeId, @NonNull String title, String imageUrl, String sourceUrl,
                  String[] ingredients, float social_rank, boolean isFavorite) {
        this.recipe_id = recipeId;
        this.title = title;
        this.image_url = imageUrl;
        this.source_url = sourceUrl;
        this.ingredients = ingredients;
        this.social_rank = social_rank;
        this.isFavorite = isFavorite;
    }

    public Recipe() {
    }

    public Recipe(final Recipe other) {
        this.recipe_id = other.recipe_id;
        this.title = other.title;
        this.image_url = other.image_url;
        this.source_url = other.source_url;
        this.ingredients = other.ingredients != null ?
                Arrays.copyOf(other.ingredients, other.ingredients.length) : null;
        this.social_rank = other.social_rank;
        this.isFavorite = other.isFavorite;
    }

    protected Recipe(Parcel in) {
        recipe_id = Objects.requireNonNull(in.readString());
        title = Objects.requireNonNull(in.readString());
        image_url = in.readString();
        source_url = in.readString();
        ingredients = in.createStringArray();
        social_rank = in.readFloat();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeId() {
        return recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return image_url;
    }

    public String getSourceUrl() {
        return source_url;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isSameAs(Recipe recipe) {
        return this.recipe_id.equals(recipe.getRecipeId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Float.compare(recipe.social_rank, social_rank) == 0 &&
                isFavorite == recipe.isFavorite &&
                recipe_id.equals(recipe.recipe_id) &&
                title.equals(recipe.title) &&
                Objects.equals(image_url, recipe.image_url) &&
                Objects.equals(source_url, recipe.source_url) &&
                Arrays.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(recipe_id, title, image_url, source_url, social_rank, isFavorite);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id='" + recipe_id + '\'' +
                ", title='" + title + '\'' +
                ", image_url='" + image_url + '\'' +
                ", source_url='" + source_url + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", social_rank=" + social_rank +
                ", isFavorite=" + isFavorite +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipe_id);
        dest.writeString(title);
        dest.writeString(image_url);
        dest.writeString(source_url);
        dest.writeStringArray(ingredients);
        dest.writeFloat(social_rank);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    public void setFavorite(boolean status) {
        this.isFavorite = status;
    }
}
