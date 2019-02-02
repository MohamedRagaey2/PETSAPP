package m.ragaey.mohamed.petsapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import m.ragaey.mohamed.petsapp.Data.PetBdHelper;
import m.ragaey.mohamed.petsapp.Data.PetsContract.PetsEntry;

public class ADDNewPet extends AppCompatActivity {

     EditText name,breed,weight;
    Spinner spinner;

    PetBdHelper petBdHelper;
    SQLiteDatabase sqLiteDatabase;

    private int gender = PetsEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_pet);

        name =  findViewById(R.id.edit_pet_name);
        breed = findViewById(R.id.edit_pet_breed);
        weight= findViewById(R.id.edit_pet_weight);
        spinner = findViewById(R.id.spinner_gender);

        petBdHelper  = new PetBdHelper(getApplicationContext());
        sqLiteDatabase  = petBdHelper.getWritableDatabase();

        setupSpinner();

    }

    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(genderSpinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        gender = PetsEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        gender = PetsEntry.GENDER_FEMALE;
                    } else {
                        gender = PetsEntry.GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = PetsEntry.GENDER_UNKNOWN;
            }
        });
    }

    private void insertPet()
    {
        String nameString = name.getText().toString().trim();
        String breedString = breed.getText().toString().trim();
        String weightString = weight.getText().toString().trim();
        int weight = Integer.parseInt(weightString);

        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME, nameString);
        values.put(PetsEntry.COLUMN_PET_BREED, breedString);
        values.put(PetsEntry.COLUMN_PET_GENDER, gender);
        values.put(PetsEntry.COLUMN_PET_WEIGHT, weight);

        long newRowId = sqLiteDatabase.insert(PetsEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertPet();
                // Exit activity
                finish();
                return true;

            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
