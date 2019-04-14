package me.codeminions.icontxtview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class IconTxtView extends FrameLayout {

    View root;
    TextView textView;
    ImageView imageView;

    // 文字的属性
    String text;
    float textSize;
    int textColor;
    float textToP;

    // 图片属性
    Drawable icon;
    int iconTint;
    float iconS;

    // 共同属性 + 位置属性
    int tint;
    Drawable back;
    int currentLay;

    @TargetApi(Build.VERSION_CODES.O)
    public IconTxtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        root = LayoutInflater.from(context).inflate(R.layout.layout_icontext, this);
        initWidget();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconTxtView);
        currentLay = ta.getInt(R.styleable.IconTxtView_iconLocation, 1);

        text = ta.getString(R.styleable.IconTxtView_text);
        textSize = ta.getDimension(R.styleable.IconTxtView_textSize, 10);
        textColor = ta.getColor(R.styleable.IconTxtView_textColor, Color.parseColor("#70000000"));
        textToP = ta.getDimension(R.styleable.IconTxtView_textToP, 10);

        icon = ta.getDrawable(R.styleable.IconTxtView_icon);
        iconTint = ta.getColor(R.styleable.IconTxtView_iconTint, Color.parseColor("#70000000"));
        if (Objects.equals(ta.getString(R.styleable.IconTxtView_iconS), "wrap_content")) iconS = -2;
        else iconS = ta.getDimension(R.styleable.IconTxtView_iconS, 30);

        tint = ta.getColor(R.styleable.IconTxtView_tint, -1);
        back = ta.getDrawable(R.styleable.IconTxtView_back);
        ta.recycle();

        initData();
    }

    void initData() {
        LayoutParams lp1 = (LayoutParams) textView.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) imageView.getLayoutParams();

        Log.i("currentLay", Integer.toString(currentLay));

        switch (currentLay) {
            case 1:
                lp1.gravity = Gravity.CENTER | Gravity.BOTTOM;
                lp2.gravity = Gravity.CENTER | Gravity.TOP;
                lp1.setMargins(0, (int) textToP, 0, (int) textToP);
                break;
            case 2:
                lp1.gravity = Gravity.CENTER | Gravity.TOP;
                lp2.gravity = Gravity.CENTER | Gravity.BOTTOM;
                lp1.setMargins(0, (int) textToP, 0, (int) textToP);
                break;
            case 3:
                lp1.gravity = Gravity.CENTER | Gravity.END;
                lp2.gravity = Gravity.CENTER | Gravity.START;
                lp1.setMargins((int) textToP, 0, 0, 0);
                break;
            case 4:
                lp1.gravity = Gravity.CENTER | Gravity.START;
                lp2.gravity = Gravity.CENTER | Gravity.END;
                lp1.setMargins(0, 0, (int) textToP, 0);
                break;
        }

        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setMaxLines(1);
        textView.setLayoutParams(lp1);


        imageView.setImageDrawable(icon);
        if (iconS == -2) {
            lp2.height = LayoutParams.WRAP_CONTENT;
        } else {
            lp2.height = (int) iconS;
        }
        imageView.setLayoutParams(lp2);


        if (tint != -1) {
            imageView.setColorFilter(tint);
            textView.setTextColor(tint);
        } else {
            imageView.setColorFilter(iconTint);
            textView.setTextColor(textColor);
        }
        this.setBackground(back);
    }

    void initWidget() {
        textView = findViewById(R.id.icontxt_t);
        imageView = findViewById(R.id.icontxt_i);
    }
}
