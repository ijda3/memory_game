package br.edu.up.jogodamemoria.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import br.edu.up.jogodamemoria.R;
import br.edu.up.jogodamemoria.domain.Game;
import br.edu.up.jogodamemoria.domain.GameItem;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Game game;

    public ItemAdapter(Game game) {
        this.game = game;
        game.onMatchFailed(this::notifyDataSetChanged);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View layout = inflater.inflate(R.layout.view_match_game_item, parent, false);

        return new ItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        GameItem item = game.getItem(position);

        ImageView imageViewCard = holder.itemView.findViewById(R.id.image_view_card);
        ImageView imageViewCover = holder.itemView.findViewById(R.id.image_view_cover);

        Resources res = holder.itemView.getResources();

        int resourceId = res.getIdentifier(String.format("pokemon_%d", item.getId()), "drawable", holder.itemView.getContext().getPackageName());
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = res.getDrawable(resourceId, holder.itemView.getContext().getTheme());

        imageViewCard.setImageDrawable(drawable);

        holder.itemView.setTag(item);

        switch (item.getStatus()) {
            case "available":
                imageViewCard.setVisibility(View.INVISIBLE);
                imageViewCover.setVisibility(View.VISIBLE);
                break;
            case "matched":
            case "chosen":
                imageViewCard.setVisibility(View.VISIBLE);
                imageViewCover.setVisibility(View.INVISIBLE);
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            imageViewCard.setVisibility(View.VISIBLE);
            imageViewCover.setVisibility(View.GONE);

            game.matchItem(view);
        });
    }

    @Override
    public int getItemCount() {
        return game.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
