package com.example.personajedialogo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.content.Context;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setNavigationBarColor(Color.rgb(235, 240, 248));

        setContentView(new DialogueView(this));
    }
}

class DialogueView extends View {

    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final List<String> frases = Arrays.asList(
            "Hola",
            "Solo quiero que sepas que sos mi mejor amigo.",
            "Me alegra que estés acá."
    );

    private int indice = 0;

    private final Drawable personaje;

    DialogueView(Context context) {
        super(context);

        personaje = getResources().getDrawable(
                R.drawable.personaje,
                context.getTheme()
        );

        p.setTypeface(
                Typeface.create("sans", Typeface.NORMAL)
        );

        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas c) {

        super.onDraw(c);

        int w = getWidth();
        int h = getHeight();

        // Fondo
        c.drawColor(
                Color.rgb(238, 242, 248)
        );

        // Sombra debajo del personaje
        p.setColor(
                Color.argb(45, 0, 0, 0)
        );

        c.drawOval(
                w * .30f,
                h * .88f,
                w * .70f,
                h * .93f,
                p
        );

        // PERSONAJE
        int left = (int)(w * .27f);
        int top = (int)(h * .24f);
        int right = (int)(w * .73f);
        int bottom = (int)(h * .92f);

        personaje.setBounds(
                left,
                top,
                right,
                bottom
        );

        personaje.draw(c);

        // -------------------------
        // GLOBO DE DIÁLOGO
        // -------------------------

        float bx = w * .08f;
        float by = h * .06f;
        float bw = w * .84f;
        float bh = h * .25f;

        // Fondo blanco del globo
        p.setColor(Color.WHITE);

        c.drawRoundRect(
                bx,
                by,
                bx + bw,
                by + bh,
                28,
                28,
                p
        );

        // Colita del globo
        Path cola = new Path();

        cola.moveTo(
                w * .43f,
                by + bh
        );

        cola.lineTo(
                w * .49f,
                by + bh
        );

        cola.lineTo(
                w * .46f,
                by + bh + h * .08f
        );

        cola.close();

        c.drawPath(
                cola,
                p
        );

        // Borde del globo
        p.setStyle(Paint.Style.STROKE);

        p.setStrokeWidth(4);

        p.setColor(
                Color.rgb(45, 50, 60)
        );

        c.drawRoundRect(
                bx,
                by,
                bx + bw,
                by + bh,
                28,
                28,
                p
        );

        c.drawPath(
                cola,
                p
        );

        p.setStyle(Paint.Style.FILL);

        // -------------------------
        // TEXTO
        // -------------------------

        p.setColor(
                Color.rgb(35, 38, 45)
        );

        p.setTextAlign(
                Paint.Align.CENTER
        );

        p.setTextSize(
                Math.min(
                        h * .055f,
                        w * .055f
                )
        );

        drawWrappedText(
                c,
                frases.get(indice),
                w / 2f,
                by + bh * .48f,
                bw * .82f
        );

        // Texto inferior
        p.setTextSize(
                h * .026f
        );

        p.setColor(
                Color.rgb(100, 105, 115)
        );

        c.drawText(
                "Tocá la pantalla",
                w / 2f,
                h * .97f,
                p
        );
    }

    private void drawWrappedText(
            Canvas c,
            String text,
            float cx,
            float startY,
            float maxWidth
    ) {

        String[] words = text.split(" ");

        String line = "";

        float lineHeight =
                p.getTextSize() * 1.25f;

        float y = startY;

        for (String word : words) {

            String test =
                    line.isEmpty()
                            ? word
                            : line + " " + word;

            if (
                    p.measureText(test) > maxWidth
                            && !line.isEmpty()
            ) {

                c.drawText(
                        line,
                        cx,
                        y,
                        p
                );

                y += lineHeight;

                line = word;

            } else {

                line = test;
            }
        }

        if (!line.isEmpty()) {

            c.drawText(
                    line,
                    cx,
                    y,
                    p
            );
        }
    }

    // -------------------------
    // TOCAR LA PANTALLA
    // -------------------------

    @Override
    public boolean onTouchEvent(
            MotionEvent event
    ) {

        if (
                event.getAction()
                        == MotionEvent.ACTION_UP
        ) {

            indice++;

            if (
                    indice >= frases.size()
            ) {

                indice = 0;
            }

            invalidate();

            performClick();

            return true;
        }

        return true;
    }

    @Override
    public boolean performClick() {

        super.performClick();

        return true;
    }
}
