package com.example.kalkulator_sederhana;


import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttonC,button_multiple,button_div,button_mod,button_equals,button_plus,button_minus,button_pow;
    private TextView text;
    private static double sum;
    private static String result="";
    private static String resplit="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        buttonCalculate();
    }

    //All Button Set
    public void buttonCalculate(){
        text = findViewById(R.id.ANGKA);
        buttonC = (Button) findViewById(R.id.buttonC);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button_equals = (Button) findViewById(R.id.button_equals);
        button_multiple = (Button) findViewById(R.id.button_multiple);
        button_div = (Button) findViewById(R.id.button_div);
        button_plus = (Button) findViewById(R.id.button_plus);
        button_minus = (Button) findViewById(R.id.button_minus);
        button_pow = (Button) findViewById(R.id.button_pow);
        button_mod = (Button) findViewById(R.id.button_mod);

        buttonNum(button0,"0");
        buttonNum(button1,"1");
        buttonNum(button2,"2");
        buttonNum(button3,"3");
        buttonNum(button4,"4");
        buttonNum(button5,"5");
        buttonNum(button6,"6");
        buttonNum(button7,"7");
        buttonNum(button8,"8");
        buttonNum(button9,"9");

        buttonSym(button_plus,"+");
        buttonSym(button_minus,"-");
        buttonSym(button_multiple,"x");
        buttonSym(button_div,"/");
        buttonSym(button_pow,"^");
        buttonSym(button_mod,"%");

        button_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resplit!=""){
                    String[] resplit_spre = splitStr(resplit);

                    int jum_resplit = splitStr(resplit).length;
                    if (jum_resplit==2){
                        text.setText(resplit_spre[0]);
                    }
                    else if(jum_resplit==3){
                        text.setText(String.valueOf(simpleSum(resplit_spre)));
                    }else if(jum_resplit>4){
                        if(validasi(resplit_spre)){
                            String[] new_resplit = deleteArrBack(resplit_spre);

                            if(validasiNonPluMin(new_resplit)){
                                text.setText(sumAll(calcu(eksklusifPow(new_resplit))));
                            }else{
                                text.setText(sumAll(new_resplit));
                            }
                        }else{
                            if(validasiNonPluMin(resplit_spre)){
                                text.setText(sumAll(calcu(eksklusifPow(resplit_spre))));
                            }else{
                                text.setText(sumAll(resplit_spre));
                            }
                        }
                    }
                }else{
                    text.setText("0");
                }
                result = "";
                resplit= "";
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result="";
                resplit="";
                text.setText("0");
            }
        });
    }


    //String tools
    public String[] splitStr(String any){
        String[] anys = any.split(",");
        return anys;
    }

    public String[] deleteArrBack(String[] arr){
        String[] new_arr = new String[arr.length-1];
        for(int i = 0;i< new_arr.length;i++){
            new_arr[i]=arr[i];
        }
        return new_arr;
    }


    //Validation tools
    public boolean validasiNonPluMin(String[] val_noplumin){
        boolean valid = false;
        String[] sym = {"x","/","^","%"};

        for (int i = 0;i<(val_noplumin.length);i++){
            for(int j = 0;j< sym.length;j++){
                if(val_noplumin[i].equals(sym[j])){
                    valid = true;
                    break;
                }else if(j==(sym.length-1)&&!val_noplumin[i].equals(sym[j])){
                    valid = false;
                }
            }
            if(valid){break;}
        }

        return valid;
    }

    public boolean validasi(String[] val){
        boolean valid = false;
        String[] val_sym ={"+","-","/","x","^","%"};
        int jum_val = val.length-1;
        int jum_val_sym = val_sym.length;
        for(int i = 0;i<jum_val_sym;i++){
            if(val[jum_val].equals(val_sym[i])){
                valid =  true;
                break;
            }else if(i==(jum_val_sym-1)&&!val[jum_val].equals(val_sym[i])){
                valid =  false;
            }
        }
        return valid;
    }

    public boolean validasiPow(String[] valid_pow){
        boolean bol = false;
        for(int i=0;i<valid_pow.length;i++){
            if(valid_pow[i].equals("^")){
                bol = true;
                break;
            }else{
                bol=false;
            }
        }
        return bol;
    }


    //Calculate tools
    public String[] eksklusifPow(String[] ekspow){
        if(validasiPow(ekspow)){
            for(int i=0;i< ekspow.length;i++){
                if(ekspow[i].equals("^")){
                    ekspow[i-1]=String.valueOf(Math.pow(Double.parseDouble(ekspow[i-1]),Double.parseDouble(ekspow[i+1])));
                    ekspow[i+1]="1";
                    ekspow[i]="x";
                }
            }
        }
        return ekspow;
    }

    public String[] calcu(String[] nums){
        for(int i = 0;i<nums.length;i++){
            if(nums[i].equals("x")){
                nums[i+1]=String.valueOf(Double.parseDouble(nums[i-1])*Double.parseDouble(nums[i+1]));
                nums[i-1]="0";
                nums[i]="+";
            }else if(nums[i].equals("/")){
                nums[i+1]=String.valueOf(Double.parseDouble(nums[i-1])/Double.parseDouble(nums[i+1]));
                nums[i-1]="0";
                nums[i]="+";
            }else if(nums[i].equals("%")){
                nums[i+1]=String.valueOf(Double.parseDouble(nums[i-1])%Double.parseDouble(nums[i+1]));
                nums[i-1]="0";
                nums[i]="+";
            }
        }
        return nums;
    }

    public String sumAll(String[] numsum){
        sum = Double.parseDouble(numsum[0]);
        for(int i=0;i<numsum.length;i++){
            if(numsum[i].equals("+")){
                sum+=Double.parseDouble(numsum[i+1]);
            }else if(numsum[i].equals("-")){
                sum-=Double.parseDouble(numsum[i+1]);
            }
        }
        return String.valueOf(sum);
    }

    public double simpleSum(String[] num_spr){
        double sum =0;
        if(num_spr[1].equals("+")){
            sum = Double.parseDouble(num_spr[0])+Double.parseDouble(num_spr[2]);
        }else if(num_spr[1].equals("-")){
            sum = Double.parseDouble(num_spr[0])-Double.parseDouble(num_spr[2]);
        }else if(num_spr[1].equals("x")){
            sum = Double.parseDouble(num_spr[0])*Double.parseDouble(num_spr[2]);
        }else if(num_spr[1].equals("/")){
            sum = Double.parseDouble(num_spr[0])/Double.parseDouble(num_spr[2]);
        }else if(num_spr[1].equals("^")){
            sum = Math.pow(Double.parseDouble(num_spr[0]),Double.parseDouble(num_spr[2]));
        }else if(num_spr[1].equals("%")){
            sum = Double.parseDouble(num_spr[0])%Double.parseDouble(num_spr[2]);
        }
        return sum;
    }


    //Button Tools
    public void buttonSym(Button button_sym,String sym){
        button_sym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result.equals("")){
                    if(sym.equals("+")){
                        result+=sym;
                        resplit+=sym;
                    }else if(sym.equals("-")){
                        result+=sym;
                        resplit+=sym;
                    }
                }else{
                    if(!validasi(splitStr(resplit))){
                        result+=sym;
                        resplit+=","+sym+",";
                    }
                }
                text.setText(result);
            }
        });
    }

    public void buttonNum(Button button_num, String num){
        button_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result+=num;
                resplit+=num;
                text.setText(result);
            }
        });
    }
}