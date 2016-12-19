package cn.edu.bistu.cs.se.m10;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private static final String TAG = "myTag";

    private Button[] nums;
    private Button[] cmds;
    private String cmd;
    private WebView webView;
    private JsInterface jsInterface;
    private double number1, number2, result;
    private TextView editText;
    private TextView textView;
    private Button uConversion, dConversion;
    private int jz = 10;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化
        nums = new Button[13];
        cmds = new Button[9];
        cmd = null;
        editText = (TextView)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        uConversion = (Button)findViewById(R.id.buttonUConversion);
        dConversion = (Button)findViewById(R.id.buttonDConversion);

        //数字输入按钮的获取
        nums[0] = (Button)findViewById(R.id.button0);
        nums[1] = (Button)findViewById(R.id.button1);
        nums[2] = (Button)findViewById(R.id.button2);
        nums[3] = (Button)findViewById(R.id.button3);
        nums[4] = (Button)findViewById(R.id.button4);
        nums[5] = (Button)findViewById(R.id.button5);
        nums[6] = (Button)findViewById(R.id.button6);
        nums[7] = (Button)findViewById(R.id.button7);
        nums[8] = (Button)findViewById(R.id.button8);
        nums[9] = (Button)findViewById(R.id.button9);
        nums[10] = (Button)findViewById(R.id.buttonPoint);
        nums[11] = (Button)findViewById(R.id.left);
        nums[12] = (Button)findViewById(R.id.right);

        //为每个按钮添加监听器
        for (int i = 0; i < nums.length; i++){
            nums[i].setOnClickListener(new NumListener());
        }

        //获取操作的按钮
        cmds[0] = (Button)findViewById(R.id.buttonPlus);
        cmds[1] = (Button)findViewById(R.id.buttonMinus);
        cmds[2] = (Button)findViewById(R.id.buttonTime);
        cmds[3] = (Button)findViewById(R.id.buttonDivided);
        cmds[4] = (Button)findViewById(R.id.buttonIs);
        cmds[5] = (Button)findViewById(R.id.buttonCE);
        cmds[6] = (Button)findViewById(R.id.buttonSin);
        cmds[7] = (Button)findViewById(R.id.buttonCos);
        cmds[8] = (Button)findViewById(R.id.buttonN);

        //添加监听器
        for (int i=0; i<cmds.length ;i++){
            cmds[i].setOnClickListener(new CmdListener());
        }

        //进制转换的按钮监听，采用Dialog
        uConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"二进制","八进制","十进制"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("进制转换")
                        .setItems(items, new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = null;
                                input = editText.getText().toString();
                                int n = (int)Double.parseDouble(input);
                                if(input != null){
                                    switch (i){
                                        case 0:
                                            Log.i(TAG, "onClick0: " + jz);
                                            if(jz != 2){
                                                input = n + "";
                                                editText.setText(Integer.toBinaryString(Integer.parseInt(input, jz)));
                                                jz = 2;
                                            }
                                            break;
                                        case 1:
                                            Log.i(TAG, "onClick1: " + jz);
                                            if(jz != 8){
                                                input = n + "";
                                                editText.setText(Integer.toOctalString(Integer.parseInt(input, jz)));
                                                jz = 8;
                                            }
                                            break;
                                        case 2:
                                            Log.i(TAG, "onClick2: " + jz);
                                            if(jz != 10){
                                                input = n + "";
                                                editText.setText(Integer.valueOf(input, jz).toString());
                                                jz = 10;
                                            }
                                            break;
                                    }
                                }
                            }
                        });
                builder.show();
            }
        });

        //单位换算，采用Dialog，点击创建新的Activity，具体显示什么数据用Intent传过去
        dConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"长度","质量","面积"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("单位换算")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, DActivity.class);
                                switch (i){
                                    case 0:
                                        intent.putExtra("text1", "千米");
                                        intent.putExtra("text2", "米");
                                        intent.putExtra("text3", "毫米");
                                        intent.putExtra("text4", "微米");
                                        intent.putExtra("dw", 1000);
                                        break;
                                    case 1:
                                        intent.putExtra("text1", "吨");
                                        intent.putExtra("text2", "千克");
                                        intent.putExtra("text3", "克");
                                        intent.putExtra("text4", "毫克");
                                        intent.putExtra("dw", 1000);
                                        break;
                                    case 2:
                                        intent.putExtra("text1", "平方米");
                                        intent.putExtra("text2", "平方分米");
                                        intent.putExtra("text3", "平方厘米");
                                        intent.putExtra("text4", "平方毫米");
                                        intent.putExtra("dw", 100);
                                        break;
                                }
                                startActivity(intent);
                            }
                        });
                builder.show();
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        jsInterface = new JsInterface(webView, editText);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(jsInterface, "JSInterface");
        webView.setWebViewClient(new vwClient());
        webView.loadUrl("file:///android_asset/cal.html");

    }

    //数字按钮的监听器
    public class NumListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String str = editText.getText().toString();
            //判断当前是否为10进制
            if(jz != 10){
                editText.setText(Integer.valueOf(str, jz).toString());
                jz = 10;
            }
            str = editText.getText().toString();
            switch (view.getId()){
                case R.id.buttonPoint:
                    if(!str.equals("") && !str.contains(".")){
                        editText.setText(str + ".");
                    }
                    break;
                case R.id.button0:
                    if (str.equals("")) {
                        editText.setText("0");
                    } else if (str.charAt(0) == '0' && str.charAt(str.length() - 1) == '0') {
                        if (str.contains(".")) {
                            editText.setText(str + "0");

                        }
                    } else {
                        editText.setText(str + "0");
                    }
                    break;
                case R.id.left:
                    textView.setText(textView.getText() + "(");
                    break;
                case R.id.right:
                    textView.setText(textView.getText() + editText.getText().toString() +  ")");
                    editText.setText("");
                    break;
                default:
                    if(!str.equals("0")){
                        editText.setText(str + ((Button)view).getText().toString());
                    }
                    break;
            }
        }
    }

    //功能按钮的监听器
    public class CmdListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            double num = 0;
            if(view.getId() == R.id.buttonCE){
                textView.setText("");
                editText.setText("");
                number1 = 0;
                number2 = 0;
                result = 0;
                cmd = null;
                return ;
            }
            if(!editText.getText().toString().equals("")){
                String str = editText.getText().toString();
                if(jz != 10){
                    editText.setText(Integer.valueOf(str, jz).toString());
                    jz = 10;
                }
                switch (view.getId()){
                    case R.id.buttonPlus:
                        if(cmd == null){
                            //number1 = Double.parseDouble(editText.getText().toString());
                            textView.setText(editText.getText() + "+");
                        }else {
                            //number2 = Double.parseDouble(editText.getText().toString());
                            //cmd(number1, number2, cmd);
                            textView.setText(textView.getText() + editText.getText().toString() + "+");
                        }
                        editText.setText("");
                        cmd = "+";
                        break;
                    case R.id.buttonMinus:
                        if(cmd == null){
                            //number1 = Double.parseDouble(editText.getText().toString());
                            textView.setText(editText.getText() + "-");
                        }else {
                            //number2 = Double.parseDouble(editText.getText().toString());
                            //cmd(number1, number2, cmd);
                            textView.setText(textView.getText() + editText.getText().toString() + "-");
                        }
                        editText.setText("");
                        cmd = "-";
                        break;
                    case R.id.buttonTime:
                        if(cmd == null){
                            //number1 = Double.parseDouble(editText.getText().toString());
                            textView.setText(editText.getText() + "*");
                        }else {
                            //number2 = Double.parseDouble(editText.getText().toString());
                            //cmd(number1, number2, cmd);
                            textView.setText(textView.getText() + editText.getText().toString() + "*");
                        }
                        editText.setText("");
                        cmd = "*";
                        break;
                    case R.id.buttonDivided:
                        if(cmd == null){
                            //number1 = Double.parseDouble(editText.getText().toString());
                            textView.setText(editText.getText() + "/");
                        }else {
                            //number2 = Double.parseDouble(editText.getText().toString());
                            //if(number2 != 0){
                                //cmd(number1, number2, cmd);
                                textView.setText(textView.getText() + editText.getText().toString() + "/");
                            //}else {
                              //  Toast.makeText(MainActivity.this, "除数不能为0", Toast.LENGTH_SHORT).show();
                            //}
                        }
                        editText.setText("");
                        cmd = "/";
                        break;
//                    case R.id.buttonIs:
//                        if(!editText.getText().equals("")){
//                            textView.setText(textView.getText() + editText.getText().toString());
//                            String operation = textView.getText().toString();
//                        }
//                        break;
                    case R.id.buttonCE:
                        textView.setText("");
                        editText.setText("");
                        number1 = 0;
                        number2 = 0;
                        result = 0;
                        cmd = null;
                        Log.i(TAG, "onClick: " + textView.getText());
                        break;
                    case R.id.buttonSin:
                        num = Double.parseDouble(editText.getText().toString());
                        num = Math.toRadians(num);
                        editText.setText(Math.sin(num) + "");
                        break;
                    case R.id.buttonCos:
                        num = Double.parseDouble(editText.getText().toString());
                        num = Math.toRadians(num);
                        editText.setText(Math.cos(num) + "");
                        break;
                    case R.id.buttonN:
                        num = Double.parseDouble(editText.getText().toString());
                        num = num * num;
                        editText.setText(num + "");
                        break;
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(MainActivity.this,v);
        popup.setOnMenuItemClickListener(MainActivity.this);
        popup.inflate(R.menu.menu_popup);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.buttonHelp:
            Toast.makeText(MainActivity.this,"This is help",Toast.LENGTH_LONG).show();
            break;
            case R.id.buttonExit:
                finish();
                break;

        }
        return false;
    }

    //运算操作的方法
    public void cmd(double num1, double num2, String cmd){
        switch (cmd){
            case "+":
                number1 = num1 + num2;
                number2 = 0;

                break;
            case "-":
                number1 = num1 - num2;
                number2 = 0;
                break;
            case "*":
                number1 = num1 * num2;
                number2 = 0;
                break;
            case "/":
                if(number2 != 0){
                    number1 = num1 / num2;
                    number2 = 0;
                }else {
                    return;
                }
                break;
        }
        result = number1;
    }

    class vwClient extends WebViewClient {
        //@Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            cmds[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String operation;
                    if(!editText.getText().equals("")){
                        textView.setText(textView.getText() + editText.getText().toString());
                    }
                    operation = textView.getText().toString();
                    jsInterface.javaCallJsFunction(operation);
                }
            });
        }
    }

}
