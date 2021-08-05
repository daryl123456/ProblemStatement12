package sg.edu.rp.c346.id19042545.problemstatement12;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText etName, etDesc, etSec;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = findViewById(R.id.btnAdd2);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etSec = findViewById(R.id.etSec);

        DBHelper db = new DBHelper(AddActivity.this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(etName.getText());
                String desc = String.valueOf(etDesc.getText());
                long id = db.insertTask(name, desc);
                db.close();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.parseInt(etSec.getText().toString()));

                Intent intent = new Intent(AddActivity.this,
                        ScheduledNotificationReceiver.class);

                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                intent.putExtra("id",id);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}