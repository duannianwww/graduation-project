package com.biyesheji.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.adapter.ExecisesDetailAdapter;
import com.biyesheji.bean.shiti;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener, RadioGroup.OnCheckedChangeListener, Chronometer.OnChronometerTickListener {
    private Integer id;
    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private String title;
    private String shijuanname;
    private ExecisesDetailAdapter adapter;
    //    定义控件id
    private RadioButton onlinetest_radioA;
    private RadioButton onlinetest_radioB;
    private RadioButton onlinetest_radioC;
    private RadioButton onlinetest_radioD;
    private RadioButton onlinetest_radioE;
    private RadioButton onlinetest_radioF;
    private RadioButton onlinetest_radioG;
    private RadioButton onlinetest_radioH;
    //    考试题目显示
    private TextView onlinetest_show_question;
    // 做题选择
    private RadioGroup onlinetest_radioGroup;
    private TextView activity_onlinetest_their_papers;
    private ViewPager viewpager;
    private static ArrayList<View> viewpagelist;
    //    对错多少题
    private Integer errorCount = 0;

    private Integer rightCount = 0;
    private Integer scoreCount = 0;
    //   指数
    private int index;
    //    做题的页数，做完滑动的
    private Integer viewpagerIndex = 0;
    //  答题选项
    public String answer;
    //  标记
    public int flag;
    private MySQL mySQL;
    private List<shiti> shitiList;
    private String questiontype;//试题类型
    private RadioGroup onlinetest_panduan_radioGroup;
    private RadioButton onlinetest_yes;
    private RadioButton onlinetest_no;
    private LinearLayout onlinetest_tiankong_ln;
    private LinearLayout answer1_ln;
    private LinearLayout answer2_ln;
    private LinearLayout answer3_ln;
    private LinearLayout answer4_ln;
    private LinearLayout answer5_ln;
    private LinearLayout answer6_ln;
    private EditText et_wenda;
    private TextView tv_back_two;
    private CheckBox onlinetest_checkbokA;
    private LinearLayout onlinetest_duoxuan_ln;
    private CheckBox onlinetest_checkbokB;
    private CheckBox onlinetest_checkbokC;
    private CheckBox onlinetest_checkbokE;
    private CheckBox onlinetest_checkbokD;
    private CheckBox onlinetest_checkbokF;
    private CheckBox onlinetest_checkbokG;
    private CheckBox onlinetest_checkbokH;
    private String answerduoxuan="";
    private List<String> user_answers;//选择，判断，多选，问答题的用户答案
    private List<String> user_tiankong_answers;//填空的用户答案
    private ArrayList<String> duoxuan_answers;
    private EditText tiankong_answer1;
    private EditText tiankong_answer3;
    private EditText tiankong_answer2;
    private EditText tiankong_answer4;
    private EditText tiankong_answer5;
    private EditText tiankong_answer6;
    private boolean tiankong1;
    private boolean tiankong2;
    private boolean tiankong3;
    private boolean tiankong4;
    private boolean tiankong5;
    private boolean tiankong6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        title = getIntent().getStringExtra("title");
        shijuanname = getIntent().getStringExtra("shijuanname");
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExercisesDetailActivity.this.finish();
            }
        });
        viewpager = (ViewPager) findViewById(R.id.exercises_viewPager);
        viewpager.setOnPageChangeListener(this);
        //        实例化自己定义的ExecisesDetailAdapter
        adapter = new ExecisesDetailAdapter();
        adapter.setData(viewpagelist);
        viewpager.setAdapter(adapter);
        //初始化交卷

        activity_onlinetest_their_papers = (TextView) findViewById(R.id.activity_onlinetest_their_papers);
//        点击事件
        activity_onlinetest_their_papers.setOnClickListener(this);
        //        设置页码
        viewpager.setCurrentItem(viewpagerIndex);
    }

    private void initData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        mySQL = new MySQL();
        shitiList = new ArrayList<shiti>();
        shitiList = mySQL.seaquestioncourse(title, shijuanname);
        LayoutInflater inflter = LayoutInflater.from(this);
        String questionId;
        user_answers=new ArrayList<String>();
        duoxuan_answers =new ArrayList<String>();
        viewpagelist = new ArrayList<View>();
        for (int i = 0; i < shitiList.size(); i++) {
            shiti q = shitiList.get(i);
            questionId = (i + 1) + "";
            View view = inflter.inflate(R.layout.viewpager_item, null);
            onlinetest_show_question = (TextView) view.findViewById(R.id.onlinetest_show_question);
            onlinetest_show_question.setText(questionId + "." + q.getQuestion());
            questiontype = q.getQuestiontype();
            if (questiontype.equals("选择题")) {
                onlinetest_radioA = (RadioButton) view.findViewById(R.id.onlinetest_radioA);
                /**
                 * 获得a选项内容
                 * 判断一下题型，判断不需要CD选项，填空与问答不需要选项
                 */
                if (q.getA().equals("null")) {
                    onlinetest_radioA.setVisibility(View.GONE);
                } else {
                    onlinetest_radioA.setText("A." + q.getA());
                    onlinetest_radioA.setVisibility(View.VISIBLE);
                }
                onlinetest_radioB = (RadioButton) view.findViewById(R.id.onlinetest_radioB);
                /**
                 * 获得b选项内容
                 */
                if (q.getB().equals("null")) {
                    onlinetest_radioB.setVisibility(View.GONE);
                } else {
                    onlinetest_radioB.setText("B." + q.getB());
                    onlinetest_radioB.setVisibility(View.VISIBLE);
                }

                onlinetest_radioC = (RadioButton) view.findViewById(R.id.onlinetest_radioC);

                /**
                 * 获得c选项内容
                 */
                if (q.getC().equals("null")) {
                    onlinetest_radioC.setVisibility(View.GONE);
                } else {
                    onlinetest_radioC.setText("C." + q.getC());
                    onlinetest_radioC.setVisibility(View.VISIBLE);
                }
                onlinetest_radioD = (RadioButton) view.findViewById(R.id.onlinetest_radioD);

                /**
                 * 获得d选项内容
                 */
                if (q.getD().equals("null")) {
                    onlinetest_radioD.setVisibility(View.GONE);
                } else {
                    onlinetest_radioD.setText("D." + q.getD());
                    onlinetest_radioD.setVisibility(View.VISIBLE);
                }
                onlinetest_radioE = (RadioButton) view.findViewById(R.id.onlinetest_radioE);

                /**
                 * 获得e选项内容
                 */
                if (q.getE().equals("null")) {
                    onlinetest_radioE.setVisibility(View.GONE);
                } else {
                    onlinetest_radioE.setText("E." + q.getE());
                    onlinetest_radioE.setVisibility(View.VISIBLE);
                }
                onlinetest_radioF = (RadioButton) view.findViewById(R.id.onlinetest_radioF);

                /**
                 * 获得f选项内容
                 */
                if (q.getF().equals("null")) {
                    onlinetest_radioF.setVisibility(View.GONE);
                } else {
                    onlinetest_radioF.setText("F." + q.getF());
                    onlinetest_radioF.setVisibility(View.VISIBLE);
                }
                onlinetest_radioG = (RadioButton) view.findViewById(R.id.onlinetest_radioG);

                /**
                 * 获得f选项内容
                 */
                if (q.getG().equals("null")) {
                    onlinetest_radioG.setVisibility(View.GONE);
                } else {
                    onlinetest_radioG.setText("G." + q.getG());
                    onlinetest_radioG.setVisibility(View.VISIBLE);
                }
                onlinetest_radioH = (RadioButton) view.findViewById(R.id.onlinetest_radioH);

                /**
                 * 获得f选项内容
                 */
                if (q.getH().equals("null")) {
                    onlinetest_radioH.setVisibility(View.GONE);
                } else {
                    onlinetest_radioH.setText("H." + q.getH());
                    onlinetest_radioH.setVisibility(View.VISIBLE);
                }
                onlinetest_radioGroup = (RadioGroup) view.findViewById(R.id.onlinetest_xuanzhe_radioGroup);
                onlinetest_radioGroup.setVisibility(View.VISIBLE);
                onlinetest_radioGroup.setOnCheckedChangeListener(this);
                user_answers.add("");
                duoxuan_answers.add("");
                viewpagelist.add(view);
            }
            if (questiontype.equals("多选题")) {

                onlinetest_checkbokA = (CheckBox) view.findViewById(R.id.onlinetest_checkbokA);
                /**
                 * 获得a选项内容
                 * 判断一下题型
                 */
                if (q.getA().equals("null")) {
                    onlinetest_checkbokA.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokA.setText("A." + q.getA());
                    onlinetest_checkbokA.setVisibility(View.VISIBLE);
                    onlinetest_checkbokA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"A");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("A", "");
                            }
                        }
                    });
                }
                onlinetest_checkbokB = (CheckBox) view.findViewById(R.id.onlinetest_checkbokB);
                /**
                 * 获得b选项内容
                 */
                if (q.getB().equals("null")) {
                    onlinetest_checkbokB.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokB.setText("B." + q.getB());
                    onlinetest_checkbokB.setVisibility(View.VISIBLE);
                    onlinetest_checkbokB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"B");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("B", "");
                            }
                        }
                    });
                }

                onlinetest_checkbokC = (CheckBox) view.findViewById(R.id.onlinetest_checkbokC);

                /**
                 * 获得c选项内容
                 */
                if (q.getC().equals("null")) {
                    onlinetest_checkbokC.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokC.setText("C." + q.getC());
                    onlinetest_checkbokC.setVisibility(View.VISIBLE);
                    onlinetest_checkbokC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"C");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("C", "");
                            }
                        }
                    });
                }
                onlinetest_checkbokD = (CheckBox) view.findViewById(R.id.onlinetest_checkbokD);

                /**
                 * 获得d选项内容
                 */
                if (q.getD().equals("null")) {
                    onlinetest_checkbokD.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokD.setText("D." + q.getD());
                    onlinetest_checkbokD.setVisibility(View.VISIBLE);
                    onlinetest_checkbokD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"D");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("D", "");
                            }
                        }
                    });
                }
                onlinetest_checkbokE = (CheckBox) view.findViewById(R.id.onlinetest_checkbokE);

                /**
                 * 获得e选项内容
                 */
                if (q.getE().equals("null")) {
                    onlinetest_checkbokE.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokE.setText("E." + q.getE());
                    onlinetest_checkbokE.setVisibility(View.VISIBLE);
                    onlinetest_checkbokE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"E");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("E", "");
                            }
                        }
                    });
                }
                onlinetest_checkbokF = (CheckBox) view.findViewById(R.id.onlinetest_checkbokF);

                /**
                 * 获得f选项内容
                 */
                if (q.getF().equals("null")) {
                    onlinetest_checkbokF.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokF.setText("F." + q.getF());
                    onlinetest_checkbokF.setVisibility(View.VISIBLE);
                    onlinetest_checkbokF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"F");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("F", "");
                            }
                        }
                    });
                }
                onlinetest_checkbokG = (CheckBox) view.findViewById(R.id.onlinetest_checkbokG);

                /**
                 * 获得G选项内容
                 */
                if (q.getG().equals("null")) {
                    onlinetest_checkbokG.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokG.setText("G." + q.getG());
                    onlinetest_checkbokG.setVisibility(View.VISIBLE);
                    onlinetest_checkbokG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"G");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("G", "");
                            }

                        }
                    });
                }
                onlinetest_checkbokH = (CheckBox) view.findViewById(R.id.onlinetest_checkbokH);

                /**
                 * 获得H选项内容
                 */
                if (q.getH().equals("null")) {
                    onlinetest_checkbokH.setVisibility(View.GONE);
                } else {
                    onlinetest_checkbokH.setText("H." + q.getH());
                    onlinetest_checkbokH.setVisibility(View.VISIBLE);
                    onlinetest_checkbokH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                duoxuan_answers.set(viewpager.getCurrentItem(),duoxuan_answers.get(viewpager.getCurrentItem())+"H");
                            } else {
                                duoxuan_answers.get(viewpager.getCurrentItem()).replace("H", "");
                            }
                        }
                    });
                }
                onlinetest_duoxuan_ln = (LinearLayout) view.findViewById(R.id.onlinetest_duoxuan_ln);
                onlinetest_duoxuan_ln.setVisibility(View.VISIBLE);
                user_answers.add("");
                duoxuan_answers.add("");
                viewpagelist.add(view);
            }
            if (questiontype.equals("判断题")) {
                onlinetest_yes = (RadioButton) view.findViewById(R.id.onlinetest_yes);
                onlinetest_yes.setText("对");
                onlinetest_no = (RadioButton) view.findViewById(R.id.onlinetest_no);
                onlinetest_no.setText("错");
                onlinetest_panduan_radioGroup = (RadioGroup) view.findViewById(R.id.onlinetest_panduan_radioGroup);
                onlinetest_panduan_radioGroup.setVisibility(View.VISIBLE);
                onlinetest_panduan_radioGroup.setOnCheckedChangeListener(this);
                user_answers.add("");
                duoxuan_answers.add("");
                viewpagelist.add(view);
            }
            if (questiontype.equals("填空题")) {
                tiankong1 = false;
                tiankong2 = false;
                tiankong3 = false;
                tiankong4 = false;
                tiankong5 = false;
                tiankong6 = false;
                answer1_ln = (LinearLayout) view.findViewById(R.id.answer1_ln);
                tiankong_answer1 =(EditText)view.findViewById(R.id.tiankong_answer1);
                /**
                 * 获得答案1内容
                 */

                if (q.getAnswer().equals("null")) {
                    answer1_ln.setVisibility(View.GONE);
                    tiankong1=true;
                } else {
                    answer1_ln.setVisibility(View.VISIBLE);
                    tiankong_answer1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            Log.i("填空1",tiankong_answer1.getText().toString().trim());
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer1.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer().trim()))
                                {
                                    tiankong1 =true;
                                }
                            }else
                            {
                                tiankong1 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                answer2_ln = (LinearLayout) view.findViewById(R.id.answer2_ln);
                tiankong_answer2 =(EditText)view.findViewById(R.id.tiankong_answer2);
                /**
                 * 获得答案2内容
                 */
                if (q.getAnswer2().equals("null")) {
                    answer2_ln.setVisibility(View.GONE);
                    tiankong2=true;
                } else {
                    answer2_ln.setVisibility(View.VISIBLE);
                    tiankong_answer2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            Log.i("填空2",tiankong_answer2.getText().toString().trim());
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer2.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer2().trim()))
                                {
                                    tiankong2 =true;
                                }
                            }else
                            {
                                tiankong2 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                answer3_ln = (LinearLayout) view.findViewById(R.id.answer3_ln);
                tiankong_answer3 =(EditText)view.findViewById(R.id.tiankong_answer3);
                /**
                 * 获得答案3内容
                 */
                if (q.getAnswer3().equals("null")) {
                    answer3_ln.setVisibility(View.GONE);
                    tiankong3=true;
                } else {
                    answer3_ln.setVisibility(View.VISIBLE);
                    tiankong_answer3.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer3.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer3().trim()))
                                {
                                    tiankong3 =true;
                                }
                            }else
                            {
                                tiankong3 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                answer4_ln = (LinearLayout) view.findViewById(R.id.answer4_ln);
                tiankong_answer4 =(EditText)view.findViewById(R.id.tiankong_answer4);
                /**
                 * 获得答案4内容
                 */
                if (q.getAnswer4().equals("null")) {
                    answer4_ln.setVisibility(View.GONE);
                    tiankong4=true;
                } else {
                    answer4_ln.setVisibility(View.VISIBLE);
                    tiankong_answer4.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer4.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer4().trim()))
                                {
                                    tiankong4 =true;
                                }
                            }else
                            {
                                tiankong4 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                answer5_ln = (LinearLayout) view.findViewById(R.id.answer5_ln);
                tiankong_answer5 =(EditText)view.findViewById(R.id.tiankong_answer5);
                /**
                 * 获得答案5内容
                 */
                if (q.getAnswer5().equals("null")) {
                    answer5_ln.setVisibility(View.GONE);
                    tiankong5=true;
                } else {
                    answer5_ln.setVisibility(View.VISIBLE);
                    tiankong_answer5.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer5.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer5().trim()))
                                {
                                    tiankong5 =true;
                                }
                            }else
                            {
                                tiankong5 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                answer6_ln = (LinearLayout) view.findViewById(R.id.answer6_ln);
                tiankong_answer6 =(EditText)view.findViewById(R.id.tiankong_answer6);
                /**
                 * 获得答案6内容
                 */
                if (q.getAnswer6().equals("null")) {
                    answer6_ln.setVisibility(View.GONE);
                    tiankong6 =true;
                } else {
                    answer6_ln.setVisibility(View.VISIBLE);
                    tiankong_answer6.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            initEditText();
                            if(!shitiList.get(viewpager.getCurrentItem()).getAnswer().equals("null"))
                            {
                                if(tiankong_answer6.getText().toString().trim().equals(shitiList.get(viewpager.getCurrentItem()).getAnswer6().trim()))
                                {
                                    tiankong6 =true;
                                }
                            }else
                            {
                                tiankong6 =true;
                            }
                            if(tiankong1&&tiankong2&&tiankong3&&tiankong4&&tiankong5&&tiankong6)
                                user_answers.set(viewpager.getCurrentItem(),shitiList.get(viewpager.getCurrentItem()).getAnswer().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                onlinetest_tiankong_ln = (LinearLayout) view.findViewById(R.id.onlinetest_tiankong_ln);
                onlinetest_tiankong_ln.setVisibility(View.VISIBLE);
                user_answers.add("");
                duoxuan_answers.add("");
                viewpagelist.add(view);
            }
            if (questiontype.equals("问答题")) {
                et_wenda = (EditText) view.findViewById(R.id.et_wenda);
                et_wenda.setVisibility(View.VISIBLE);
                et_wenda.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        initEditText();
                        Log.i("wenda",et_wenda.getText().toString().trim());
                        Log.i("answer",shitiList.get(viewpager.getCurrentItem()).getAnswer());
                        user_answers.set(viewpager.getCurrentItem(),et_wenda.getText().toString().trim());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                user_answers.add("");
                duoxuan_answers.add("");
                viewpagelist.add(view);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
//        交卷的点击事件
        switch (v.getId()) {
            case R.id.activity_onlinetest_their_papers:

                AlertDialog.Builder ad = new AlertDialog.Builder(ExercisesDetailActivity.this);

                ad.setTitle("结束练习？");
                ad.setMessage("练习还没结束，是否交卷？\r\n再检查一下看还有未做题吧");
                ad.setPositiveButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
//                        点击是结束当前Activity
                        for(int y = 0; y < shitiList.size(); y++)
                        {
                            if(!duoxuan_answers.get(y).equals(""))
                            {
                                user_answers.set(y,duoxuan_answers.get(y));
                            }
                            if(user_answers.get(y).equals(shitiList.get(y).getAnswer().trim())){
                                rightCount += 1;
                                if(rightCount!=shitiList.size())
                                    scoreCount = 100/shitiList.size()*rightCount;//满分100,刷新分数
                                else scoreCount=100;
                            }else
                            {
                                errorCount += 1;
                            }
                        }
                        Toast.makeText(ExercisesDetailActivity.this, "本次得分" + scoreCount + "\r\n对题" + rightCount
                                + "\r\n错题" + errorCount, Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
                ad.setNegativeButton("接着练习", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
//                        让对话框从屏幕上消失
                        errorCount=0;
                        rightCount=0;
                        scoreCount=0;
                        dialog.dismiss();

                    }
                });
//                创建对话框
                ad.create();
//                让用户必须选择对话框，解决弹出对话框点击空白对话框消失的问题
                ad.setCancelable(false);
//                显示对话框
                ad.show();
        }
    }

    public void initEditText()
    {
        tiankong_answer1 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer1);
        tiankong_answer2 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer2);
        tiankong_answer3 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer3);
        tiankong_answer4 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer4);
        tiankong_answer5 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer5);
        tiankong_answer6 = (EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.tiankong_answer6);
        et_wenda=(EditText) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.et_wenda);
    }

    /**
     * 禁止点击的方法
     */
    public void noClick() {
        initRadioButton();
//        onlinetest_radioA.setEnabled(false);
//        onlinetest_radioB.setEnabled(false);
//        onlinetest_radioC.setEnabled(false);
//        onlinetest_radioD.setEnabled(false);
//        onlinetest_radioE.setEnabled(false);
//        onlinetest_radioF.setEnabled(false);
//        onlinetest_radioG.setEnabled(false);
//        onlinetest_radioH.setEnabled(false);
//        onlinetest_yes.setEnabled(false);
//        onlinetest_no.setEnabled(false);
    }

    /**
     * 初始化radiobutton  这个方法决定点击有没有用
     */
    public void initRadioButton() {
        onlinetest_radioA = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioA);
        onlinetest_radioB = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioB);
        onlinetest_radioC = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioC);
        onlinetest_radioD = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioD);
        onlinetest_radioE = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioE);
        onlinetest_radioF = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioF);
        onlinetest_radioG = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioG);
        onlinetest_radioH = (RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_radioH);
        onlinetest_yes=(RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_yes);
        onlinetest_no=(RadioButton) viewpagelist.get(viewpager.getCurrentItem()).findViewById(R.id.onlinetest_no);
    }

    //    监听手机自带的按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        如果点击的返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder ad = new AlertDialog.Builder(ExercisesDetailActivity.this);

            ad.setTitle("确定离开？");
            ad.setMessage("练习还没结束，是否离开？\r\n再检查一下看还有未做题吧");
            ad.setPositiveButton("是", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int i) {
//                        点击是结束当前Activity
                    finish();

                }
            });
            ad.setNegativeButton("接着练习", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
//                        让对话框从屏幕上消失
                    dialog.dismiss();

                }
            });
//                创建对话框
            ad.create();
//                让用户必须选择对话框，解决弹出对话框点击空白对话框消失的问题
            ad.setCancelable(false);
//                显示对话框
            ad.show();
        }
        return false;

    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        boolean isRigth = false;
        boolean isOnChangge = false;
        switch (checkedId) {//            ABCD四个选项，判断你选择的答案对不对
            case R.id.onlinetest_radioA:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//加了有可能导致最后一页无法选中
                //禁止继续选择
                noClick();
                //  判断答案是否正确
                answer = "A";
                user_answers.set(viewpager.getCurrentItem(),answer);
//                自动滑到下一页
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;

            case R.id.onlinetest_radioB:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();
                noClick();
                answer = "B";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioC:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();
                noClick();
                answer = "C";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioD:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "D";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioE:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "E";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioF:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "F";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioG:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "G";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_radioH:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "H";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_yes:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "对";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
            case R.id.onlinetest_no:
                isOnChangge = true;
                //onlinetest_radioGroup.clearCheck();//2      1和2放在前面才有效果
                noClick();
                answer = "错";
                user_answers.set(viewpager.getCurrentItem(),answer);
                //viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
        }
    }
}
