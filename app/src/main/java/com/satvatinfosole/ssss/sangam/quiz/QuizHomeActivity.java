package com.satvatinfosole.ssss.sangam.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.dashboard.DashBoardActivity;

public class QuizHomeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_back_press;
    LinearLayout ll_start_quiz, ll_view_score, ll_quiz_toper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_note);
        initUI();
        onClickListner();
    }

    private void onClickListner() {
        img_back_press.setOnClickListener(this);
        ll_start_quiz.setOnClickListener(this);
        ll_view_score.setOnClickListener(this);
        ll_quiz_toper.setOnClickListener(this);
    }

    private void initUI() {
        img_back_press = (ImageView) findViewById(R.id.img_back_press);
        ll_start_quiz = (LinearLayout) findViewById(R.id.ll_start_quiz);
        ll_view_score = (LinearLayout) findViewById(R.id.ll_view_score);
        ll_quiz_toper = (LinearLayout) findViewById(R.id.ll_quiz_toper);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_press:
                goBack();
                break;
            case R.id.ll_start_quiz:
                startQuiz();
                break;
            case R.id.ll_view_score:
                viewScore();
                break;
            case R.id.ll_quiz_toper:
                top5Runner();
                break;
        }
    }

    private void top5Runner() {

    }

    private void viewScore() {
        startActivity(new Intent(getApplicationContext(), QuizReportViewActivity.class));
    }

    private void startQuiz() {
        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), DashBoardActivity.class));
        finish();
    }
}
