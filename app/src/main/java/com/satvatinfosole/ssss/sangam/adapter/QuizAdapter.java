package com.satvatinfosole.ssss.sangam.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.satvatinfosole.ssss.sangam.R;
import com.satvatinfosole.ssss.sangam.model.requestModel.AnswerQuizRequestModel;
import com.satvatinfosole.ssss.sangam.model.responseModel.QuizGetQuestionResponseModel;

import java.util.ArrayList;

/**
 * Created by SATHISH on 11/13/2018.
 */
public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder>  {
        public QuizAdapter(ArrayList<QuizGetQuestionResponseModel.Quiz_Questions_List> quizList, Context context) {
        this.quizList = quizList;
        this.context = context;
            answerIdList=new ArrayList<>();
            questionIdList=new ArrayList<>();
    }

    public  ArrayList<QuizGetQuestionResponseModel.Quiz_Questions_List> quizList;
    public  Context context;
    public ArrayList<String> answerIdList;
    public ArrayList<String> questionIdList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quiz_list,viewGroup,false);
       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final QuizGetQuestionResponseModel.Quiz_Questions_List quiz_questions_model =quizList.get(position);
        viewHolder.ll_but_view.setVisibility(View.GONE);
        viewHolder.txt_quize_name.setText(quiz_questions_model.getCh_id()+". "+quiz_questions_model.getCh_question());

        viewHolder.edt_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        viewHolder.edt_answer.addTextChangedListener(this);

              viewHolder.edt_answer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    Toast.makeText(context.getApplicationContext(),viewHolder.edt_answer.getText().toString(),Toast.LENGTH_SHORT).show();
                    answerIdList.add(position,viewHolder.edt_answer.getText().toString());
                    questionIdList.add(position,quiz_questions_model.getCh_id());
                viewHolder.edt_answer.requestFocus();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_quize_name;
        public  EditText edt_answer;
        LinearLayout ll_but_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_quize_name = itemView.findViewById(R.id.txt_quize_name);
            edt_answer = itemView.findViewById(R.id.edt_quize_answer);
            ll_but_view = itemView.findViewById(R.id.ll_but_view);
        }
    }


}
