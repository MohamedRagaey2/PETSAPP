package m.ragaey.mohamed.petsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import m.ragaey.mohamed.petsapp.Adapter.PetsViewHolder;
import m.ragaey.mohamed.petsapp.Data.PetsContract.PetsEntry;

public class Adapter extends RecyclerView.Adapter<PetsViewHolder>
{
    Context context;
    Cursor cursor;
    WhenItemClicked whenItemClicked;

    public Adapter(Context applicationContext, Cursor cursor) {

    }


    @NonNull
    @Override
    public PetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new PetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsViewHolder holder, int position)
    {
        if (!cursor.moveToPosition(position))
        {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME));
        String Breed= cursor.getString(cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED));
        Long id =  cursor.getLong(cursor.getColumnIndex(PetsEntry._ID));

        holder.name.setText(name);
        holder.Breed.setText(Breed);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }

    public void SwapCursor (Cursor cursor1)
    {
        if (cursor != null)
        {
            cursor.close();
        }
        cursor = cursor1;

        if (cursor1 != null)
        {
            notifyDataSetChanged();
        }
    }

    public class PetsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,Breed;

        public PetsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pets_name);
            Breed = itemView.findViewById(R.id.pets_Breed);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (whenItemClicked != null)
            {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION)
                {
                    whenItemClicked.onClick(position);
                }
                {

                }
            }
        }
    }
    public interface WhenItemClicked
    {
        void onClick (int position);
    }

    public void lamaTdos3laItem (WhenItemClicked whenItemClicked)
    {
        this.whenItemClicked = whenItemClicked;
    }
}