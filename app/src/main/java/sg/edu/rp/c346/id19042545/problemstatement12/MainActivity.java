package sg.edu.rp.c346.id19042545.problemstatement12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView lv;
    ArrayAdapter<Tasks> aa;
    ArrayList<Tasks> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(MainActivity.this);
        al = new ArrayList<>();
        al = dbh.getItemsOfTasks();
        dbh.close();
        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(MainActivity.this);
        CharSequence reply = null;
        Intent intent = getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            reply = remoteInput.getCharSequence("status");

        }
        if(reply != null){
            if (reply.equals("completed")){
                long id = intent.getLongExtra("id",0);
                db.deleteTask(id);
            }

        }

        al.clear();
        al.addAll(db.getItemsOfTasks());
        db.close();
        aa.notifyDataSetChanged();
    }
}