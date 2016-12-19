package cn.edu.bistu.cs.se.m10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DActivity extends AppCompatActivity {

    private TextView[] textViews;
    private EditText[] editTexts;
    private Button btnDo,btnClean;
    private int dw = 0;

    private static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);

        final Intent intent = getIntent();
        textViews = new TextView[4];
        editTexts = new EditText[4];
        btnDo = (Button)findViewById(R.id.buttonDo);
        btnClean = (Button)findViewById(R.id.buttonClean);

        textViews[0] = (TextView)findViewById(R.id.text1);
        textViews[0].setText(intent.getStringExtra("text1"));
        textViews[1] = (TextView)findViewById(R.id.text2);
        textViews[1].setText(intent.getStringExtra("text2"));
        textViews[2] = (TextView)findViewById(R.id.text3);
        textViews[2].setText(intent.getStringExtra("text3"));
        textViews[3] = (TextView)findViewById(R.id.text4);
        textViews[3].setText(intent.getStringExtra("text4"));

        editTexts[0] = (EditText)findViewById(R.id.edit1);
        editTexts[1] = (EditText)findViewById(R.id.edit2);
        editTexts[2] = (EditText)findViewById(R.id.edit3);
        editTexts[3] = (EditText)findViewById(R.id.edit4);

        dw = intent.getIntExtra("dw", 0);

        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = -1;
                for(int i=0; i<editTexts.length ; i++){
                    Log.i(TAG, "onClick: " + i + ":" + editTexts[i].getText());
                    if(!editTexts[i].getText().toString().equals("")){
                        num = i;
                        break;
                    }
                }
                Log.i(TAG, "onClick: " + num);

                String input = editTexts[num].getText().toString();
                double d = Double.parseDouble(input);
                if(num != -1 && dw != 0){

                    switch (num){
                        case 0:
                            editTexts[1].setText((d * dw) + "");
                            editTexts[2].setText((d * dw * dw) + "");
                            editTexts[3].setText((d * dw * dw * dw) + "");
                            break;
                        case 1:
                            editTexts[0].setText((d / dw) + "");
                            editTexts[2].setText((d * dw ) + "");
                            editTexts[3].setText((d * dw * dw) + "");
                            break;
                        case 2:
                            editTexts[0].setText((d / (dw * dw)) + "");
                            editTexts[1].setText((d / dw ) + "");
                            editTexts[3].setText((d * dw ) + "");
                            break;
                        case 3:
                            editTexts[0].setText((d / (dw * dw * dw)) + "");
                            editTexts[1].setText((d / (dw * dw)) + "");
                            editTexts[2].setText((d / dw ) + "");
                            break;
                    }
                }
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<editTexts.length; i++){
                    editTexts[i].setText("");
                }
            }
        });


    }
}
