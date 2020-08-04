package com.example.myquiz.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquiz.R;
import com.example.myquiz.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtNumberQuestion, txtQuestion, txtCountDown;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private List<Question> questionList;
    private int questNumber;
    private CountDownTimer countDown;
    private int score;
    private int setNo;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        AnhXa();
        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);

        loadingDialog = new Dialog(QuestionActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        firestore = FirebaseFirestore.getInstance();
        setNo = getIntent().getIntExtra("SETNO", 1);
        getQuestionList();
        score = 0;
    }

    private void getQuestionList() {
        questionList = new ArrayList<>();
        firestore.collection("QUIZ").document("CAT" + SetsActivity.catId).collection("SETS" + setNo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot questions = task.getResult();
                    for (QueryDocumentSnapshot doc : questions) {
                        questionList.add(new Question(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.parseInt(doc.getString("ANSWER"))));
                    }
                    setQuestion();
                } else {
                    Toast.makeText(QuestionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.cancel();

            }
        });


    }

    private void setQuestion() {
        txtCountDown.setText(String.valueOf(10));
        txtQuestion.setText(questionList.get(0).getQuestion());
        btnOption1.setText(questionList.get(0).getOption1());
        btnOption2.setText(questionList.get(0).getOption2());
        btnOption3.setText(questionList.get(0).getOption3());
        btnOption4.setText(questionList.get(0).getOption4());
        txtNumberQuestion.setText(String.valueOf(1) + " / " + questionList.size());
        startTimer();
        questNumber = 0;
    }

    private void startTimer() {
        countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 10000)
                    txtCountDown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }

    private void changeQuestion() {
        if (questNumber < questionList.size() - 1) {
            questNumber++;
            playAnim(txtQuestion, 0, 0);
            playAnim(btnOption1, 0, 1);
            playAnim(btnOption2, 0, 2);
            playAnim(btnOption3, 0, 3);
            playAnim(btnOption4, 0, 4);
            txtNumberQuestion.setText(String.valueOf(questNumber + 1) + " / " + questionList.size());
            txtCountDown.setText(String.valueOf(10));
            startTimer();
        } else {
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + " / " + questionList.size());
            intent.putExtra("CAT",String.valueOf(SetsActivity.catId));
            intent.putExtra("SET",String.valueOf(setNo));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //QuestionActivity.this.finish();
        }
    }

    private void playAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleX(value).
                setDuration(500).setStartDelay(100).
                setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    switch (viewNum) {
                        case 0:
                            ((TextView) view).setText(questionList.get(questNumber).getQuestion());
                            break;
                        case 1:
                            ((Button) view).setText(questionList.get(questNumber).getOption1());
                            break;
                        case 2:
                            ((Button) view).setText(questionList.get(questNumber).getOption2());
                            break;
                        case 3:
                            ((Button) view).setText(questionList.get(questNumber).getOption3());
                            break;
                        case 4:
                            ((Button) view).setText(questionList.get(questNumber).getOption4());
                            break;
                    }
                    if (viewNum != 0) {
                        ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
                    }
                    playAnim(view, 1, viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void AnhXa() {
        txtNumberQuestion = (TextView) findViewById(R.id.textViewNumberQuestion);
        txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
        txtCountDown = (TextView) findViewById(R.id.textViewCountDown);
        btnOption1 = (Button) findViewById(R.id.buttonOpTion1);
        btnOption2 = (Button) findViewById(R.id.buttonOpTion2);
        btnOption3 = (Button) findViewById(R.id.buttonOpTion3);
        btnOption4 = (Button) findViewById(R.id.buttonOpTion4);

    }

    @Override
    public void onClick(View v) {
        int selectedOption = 0;
        switch (v.getId()) {
            case R.id.buttonOpTion1:
                selectedOption = 1;
                break;
            case R.id.buttonOpTion2:
                selectedOption = 2;
                break;
            case R.id.buttonOpTion3:
                selectedOption = 3;
                break;
            case R.id.buttonOpTion4:
                selectedOption = 4;
                break;
            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption, v);
    }

    private void checkAnswer(int selectedOption, View view) {
        if (selectedOption == questionList.get(questNumber).getCorrectAns()) {
            score++;
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            switch (questionList.get(questNumber).getCorrectAns()) {
                case 1:
                    btnOption1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    btnOption2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    btnOption3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    btnOption4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();
    }
}
