package com.example.motiondetectorapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void updateDB(View v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView tv = findViewById(R.id.movementCounter);

        Toast.makeText(this, "Updating..", Toast.LENGTH_SHORT).show();

        db.collection("MotionDetector")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Liiketunnistin!", document.getId() + " => " + document.getData());
                                parseData(document.getData());

                                tv.setText(parseData(document.getData()));
                            }
                            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.w("Liiketunnistin!", "Error getting documents.", task.getException());
                        }
                    }

                });


    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public String parseData(Map map){
            int counter = 0;
            for(Object value: map.values()) {
                if (counter == 0 ){
                    //System.out.println(Integer.valueOf(value.toString()));
                    counter++;
                    return value.toString();
                }
            }
            return "Notfound..";
        }

}