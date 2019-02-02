package m.ragaey.mohamed.petsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import m.ragaey.mohamed.petsapp.Data.PetBdHelper;
import m.ragaey.mohamed.petsapp.Data.PetsContract;
import m.ragaey.mohamed.petsapp.Data.PetsContract.PetsEntry;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter petsadapter;
    RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    SQLiteDatabase sqLiteDatabase;
    PetBdHelper petBdHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        fab = findViewById(R.id.FAB);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration= new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        petBdHelper = new PetBdHelper(getApplicationContext());
        sqLiteDatabase = petBdHelper.getReadableDatabase();

        cursor = getAllPets();

        petsadapter = new Adapter(getApplicationContext(),cursor);

        recyclerView.setAdapter(petsadapter);


        // Setup FAB to open Add New Pet
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ADDNewPet.class);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,4)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                long id = (long)viewHolder.itemView.getTag();
                removePets(id);
                petsadapter.SwapCursor(getAllPets());
            }
        }).attachToRecyclerView(recyclerView);
    }

    public Cursor getAllPets ()
    {
        Cursor cursor;

        cursor = sqLiteDatabase.query(
                PetsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
    public int removePets (Long id)
    {
        int count ;
        count =sqLiteDatabase.delete(
                PetsEntry.TABLE_NAME,
                "_id=?",
                new String[]{Long.toString(id)});
        return count;
    }
}
