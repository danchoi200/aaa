package com.example.quangn;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nguyenvanquan7826.com.Balan;

public class mainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean dem = true;
    TextView tvMath, tvResult;

    //tao ra cac mang chua cac so 1..9, cac nut xu li
    private int[] idBtn = {
            R.id.btn_zezo, R.id.btn_one, R.id.btn_two, R.id.btn_three,
            R.id.btn_four, R.id.btn_five, R.id.btn_six,
            R.id.btn_seven, R.id.btn_eight, R.id.btn_nine, R.id.btn_cham};

    private int[] btn = {
            R.id.btn_cong, R.id.btn_tru, R.id.btn_nhan, R.id.btn_chia, R.id.btn_congtru, R.id.btn_bang};

    private int[] btnXuli = {
            R.id.btn_CE, R.id.btn_C, R.id.btn_BS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        setupUI();

        Typeface typeface=Typeface.createFromAsset(getAssets(),"DS-DIGIT.TTF");
        tvMath.setTypeface(typeface);
        tvResult.setTypeface(typeface);
        tvMath.setVisibility(View.VISIBLE);
    }

    private void setupUI() {

        //xet su anh xa va su kien click voi tat ca cac nut
        tvMath = findViewById(R.id.txtMath);
        tvResult = findViewById(R.id.txtResult);
        for (int i : idBtn) {
            findViewById(i).setOnClickListener(this);
        }
        for (int i : btn) {
            findViewById(i).setOnClickListener(this);
        }
        for (int k : btnXuli) {
            findViewById(k).setOnClickListener(this);
        }
        tvMath.setText("|");
        tvResult.setText("0");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //chech xem btn co phai la cac so hay khong
        for (int i = 0; i < idBtn.length; i++) {
            if (id == idBtn[i]) {
                String text = ((Button) findViewById(id)).getText().toString();

                //append cac so vao tvMath
                if (tvMath.getText().toString().trim().equals("|")) {
                    tvMath.setText("");
                }
                tvMath.append(text);

                //khi nhap du cac phep toan ket qua se mo hien ra
                try {
                    int a = Integer.parseInt(tvMath.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    //goi ham tinh ket qua
                    cal();
                    tvResult.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                return;
            }
        }

        //chech xem btn co phai la cac dau cong, tru nhan chia hay khong
        for (int i = 0; i < btn.length - 2; i++) {
            if (id == btn[i]) {
                String text = ((Button) findViewById(id)).getText().toString();
                if (tvMath.getText().toString().trim().equals("|")) {
                    tvMath.setText("");
                }
                tvMath.append(text);
                return;
            }
        }

        // su kien khi click vao nut C se xoa toan bo
        if (id == R.id.btn_C) {
            Toast.makeText(this, "xoa", Toast.LENGTH_SHORT).show();
            tvMath.setText("|");
            tvResult.setText("0");
            return;
        }

        //khi click vao bang goi den ham tinh ket qua
        if (id == R.id.btn_bang) {
            cal();
            tvMath.setText(tvResult.getText());
            tvResult.setText("");
        }

        //khi click vao nut CE
        if (id == R.id.btn_CE) {
            String text = tvMath.getText().toString();
            int l = text.length() - 1;
            //xoa ki tu la so
            while (text.charAt(l) <= '9' && text.charAt(l) >= '0') {
                l--;
                // neu ki tu la - va lien truoc no la cac dau cua phep toan thi l=l-1
                if (text.charAt(l) == '-'&&(text.charAt(l-1)=='*'||text.charAt(l-1)=='/'||text.charAt(l-1)=='+'||text.charAt(l-1)=='-')) {
                    l--;
                }
            }
            tvMath.setText(text.substring(0, l + 1));
        }

        //su kien click vao BS xoa hang don vi
        if (id == R.id.btn_BS) {
            String text = tvMath.getText().toString();
            if (tvMath.getText().length() == 1) {
                tvMath.setText("|");
            } else {
                tvMath.setText(text.substring(0, text.length() - 1));
            }
        }

        // su kien click vao nut dao dau
        if (id == R.id.btn_congtru) {
            Toast.makeText(this, "" + tvMath.getText().toString(), Toast.LENGTH_SHORT).show();
            if (tvMath.getText().toString() == "|") {
                tvMath.setText("");
            }else{
                if (tvResult.getText().toString() == "") {
                    tvMath.setText("-"+tvMath.getText().toString());
                }else{
                    tvMath.append("-");
                }
            }

        }
    }

    // ham tinh ket qua
    private void cal() {
        String math = tvMath.getText().toString().trim();
        if (math.length() > 0) {
            Balan balan = new Balan();
            String result = balan.valueMath(math) + "";
            String error = balan.getError();

            //check error
            if (error != null) {
                tvResult.setText(error);
            } else {
                tvResult.setText(result);
                tvResult.setTextColor(Color.GRAY);
            }
        }
    }

}

