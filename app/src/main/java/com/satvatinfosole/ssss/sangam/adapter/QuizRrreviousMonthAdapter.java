package com.satvatinfosole.ssss.sangam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.model.responseModel.PreviousQuizResponseModel;

import java.util.ArrayList;

/**
 * Created by SATHISH on 12/21/2018.
 */
public class QuizRrreviousMonthAdapter extends RecyclerView.Adapter<QuizRrreviousMonthAdapter.ViewHolder> {
    public ArrayList<PreviousQuizResponseModel.Previous_questions> quizList;
    public Context context;

    public QuizRrreviousMonthAdapter(ArrayList<PreviousQuizResponseModel.Previous_questions> quizList, Context context) {
        this.quizList = quizList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quiz_ans, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PreviousQuizResponseModel.Previous_questions previous_questions = quizList.get(i);
viewHolder.txt_sno.setText(previous_questions.getCh_sno());
viewHolder.txt_quiz.setText(previous_questions.getCh_question());
viewHolder.txt_quiz_ans.setText(previous_questions.getCh_answer());
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_sno, txt_quiz, txt_quiz_ans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_sno = itemView.findViewById(R.id.txt_sno);
            txt_quiz = itemView.findViewById(R.id.txt_quiz);
            txt_quiz_ans = itemView.findViewById(R.id.txt_quiz_ans);
        }
    }
}
