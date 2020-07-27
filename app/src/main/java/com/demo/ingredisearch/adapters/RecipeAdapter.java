package com.demo.ingredisearch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.ingredisearch.R;
import com.demo.ingredisearch.models.Recipe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: optimize with DiffUtil
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private static final String TAG = "RecipeApp";

    public interface Interaction {
        void onClickItem(Recipe item);
        void onAddFavorite(Recipe item);
        void onRemoveFavorite(Recipe item);
    }

    private List<Recipe> mRecipes;
    private Interaction interaction;

    public RecipeAdapter(Interaction interaction) {
        this.interaction = interaction;
        mRecipes = new ArrayList<>();
    }

    @NotNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_recipe, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        holder.bind(mRecipes.get(position), interaction);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ImageView favButton;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            favButton = itemView.findViewById(R.id.favButton);
        }

        private void bind(Recipe item, Interaction interaction) {
            Glide.with(imageView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_search_24dp)
                    .into(imageView);
            title.setText(item.getTitle());

            if (item.isFavorite()) {
                favButton.setImageResource(R.drawable.ic_favorite_24dp);
                favButton.setTag(R.drawable.ic_favorite_24dp);
            } else {
                favButton.setImageResource(R.drawable.ic_favorite_border_24dp);
                favButton.setTag(R.drawable.ic_favorite_border_24dp);
            }

            itemView.setOnClickListener(view ->
                    interaction.onClickItem(item)
            );

            favButton.setOnClickListener(view -> {
                if (item.isFavorite()) {
                    interaction.onRemoveFavorite(item);
                } else {
                    interaction.onAddFavorite(item);
                }
            });
        }
    }
}
