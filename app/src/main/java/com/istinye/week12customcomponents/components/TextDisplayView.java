package com.istinye.week12customcomponents.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.istinye.week12customcomponents.R;

import androidx.annotation.Nullable;

public class TextDisplayView extends LinearLayout {

    private TextView labelTextView;
    private TextView valueTextView;

    private String label;
    private String value;
    private float spotSize;
    private Paint spotColor;

    public TextDisplayView(Context context) {
        super(context);
        initialize(null, R.attr.baseTextDisplayView, R.style.TextDisplayView);
    }

    public TextDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs, R.attr.baseTextDisplayView, R.style.TextDisplayView);
    }

    public TextDisplayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs, defStyleAttr, R.style.TextDisplayView);
    }

    public TextDisplayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs, defStyleAttr, defStyleRes);
    }

    private void initialize(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        initViews();

        spotSize = 16f;
        spotColor = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs == null) return;

        String labelAttr = "", valueAttr = "";
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TextDisplayView, defStyleAttr, defStyleRes);
        try {
            if (a.hasValue(R.styleable.TextDisplayView_label)) {
                labelAttr = a.getString(R.styleable.TextDisplayView_label);
            }
            if (a.hasValue(R.styleable.TextDisplayView_value)){
                valueAttr = a.getString(R.styleable.TextDisplayView_value);
            }
            if (a.hasValue(R.styleable.TextDisplayView_spotSize)) {
                spotSize = a.getDimension(R.styleable.TextDisplayView_spotSize, 16f);
            }
            if (a.hasValue(R.styleable.TextDisplayView_spotColor)) {
                spotColor.setColor(a.getColor(R.styleable.TextDisplayView_spotColor, 0));
            }
        } finally {
            a.recycle();
        }

        setWillNotDraw(false); //For ViewGroup custom components

        setLabel(labelAttr);
        setValue(valueAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float spotRadius = spotSize/2;
        int maxSpotNumberInHorizontal = (int) (getWidth() / spotRadius);
        int maxSpotNumberInVertical = (int) (getHeight() / spotRadius);

        for (int i = 0; i < maxSpotNumberInHorizontal; i++) {
            for (int j = 0; j < maxSpotNumberInVertical; j++) {
                if (Math.random() > 0.95) {
                    float randomSpotRadius = (float) (spotRadius - Math.random() * spotRadius);
                    canvas.drawCircle(spotRadius  + i * spotRadius, spotRadius + j * spotRadius, randomSpotRadius, spotColor);
                }
            }
        }

    }

    private void initViews(){
        inflate(getContext(), R.layout.layout_text_display_view, this);

        labelTextView = findViewById(R.id.labelTextView);
        valueTextView = findViewById(R.id.valueTextView);
    }

    public void setLabel(String label) {
        this.label = label;
        labelTextView.setText(label);
        invalidate(); // These calls not trigger onDraw method in ViewGroups
        requestLayout();
    }

    public void setValue(String value) {
        this.value = value;
        valueTextView.setText(value);
        invalidate(); // These calls not trigger onDraw method in ViewGroups
        requestLayout();
    }
}
