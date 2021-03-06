package com.diceyas.usagestats.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.diceyas.usagestats.R;

import com.diceyas.usagestats.lib.login.User;
import com.diceyas.usagestats.lib.login.Utils;


/**
 * Created by dfy1995dfy on 2016/5/24.
 */
public class LoginActivity extends Activity implements OnClickListener, OnItemClickListener, OnDismissListener {
            protected static final String TAG = "LoginActivity";
            private LinearLayout mLoginLinearLayout; // ��¼���ݵ�����
            private LinearLayout mUserIdLinearLayout; // ���������������ڴ������·���ʾ
            private Animation mTranslate; // λ�ƶ���
            private Dialog mLoginingDlg; // ��ʾ���ڵ�¼��Dialog
            private EditText mIdEditText; // ��¼ID�༭��
            private EditText mPwdEditText; // ��¼����༭��
            private ImageView mMoreUser; // ����ͼ��
            private Button mLoginButton; // ��¼��ť
            private Button mSigninButton; // ��¼��ť
            private ImageView mLoginMoreUserView; // ���������������İ�ť
            private String mIdString;
            private String mPwdString;
            private ArrayList<User> mUsers; // �û��б�
            private ListView mUserIdListView; // ������������ʾ��ListView����
            private MyAapter mAdapter; // ListView�ļ�����
            private PopupWindow mPop; // ����������
            private TextView forgetPassword ;

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                SharedPreferences sharedPreferences= getSharedPreferences("test",
                        Activity.MODE_PRIVATE);
                String name =sharedPreferences.getString("userName", "");
                if(name!=""){
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                setContentView(R.layout.activity_login);
                initView();
                setListener();
                mLoginLinearLayout.startAnimation(mTranslate); // Y��ˮƽ�ƶ�

		        mUsers = Utils.getUserList(LoginActivity.this);

                if (mUsers.size() > 0) {
			/* ���б��еĵ�һ��user��ʾ�ڱ༭�� */
                    mIdEditText.setText(mUsers.get(0).getId());
                    mPwdEditText.setText(mUsers.get(0).getPwd());
                }

                LinearLayout parent = (LinearLayout) getLayoutInflater().inflate(
                        R.layout.userifo_listview, null);
                mUserIdListView = (ListView) parent.findViewById(android.R.id.list);
                parent.removeView(mUserIdListView); // �������븸�ӹ�ϵ,��Ȼ�ᱨ��
                mUserIdListView.setOnItemClickListener(this); // ���õ����
                mAdapter = new MyAapter(mUsers);
                mUserIdListView.setAdapter(mAdapter);

            }

            /* ListView�������� */
            class MyAapter extends ArrayAdapter<User> {

                public MyAapter(ArrayList<User> users) {
                    super(LoginActivity.this, 0, users);
                }

                public View getView(final int position, View convertView,
                                    ViewGroup parent) {
                    if (convertView == null) {
                        convertView = getLayoutInflater().inflate(
                                R.layout.listview_item, null);
                    }

                    TextView userIdText = (TextView) convertView
                            .findViewById(R.id.listview_userid);
                    userIdText.setText(getItem(position).getId());

                    ImageView deleteUser = (ImageView) convertView
                            .findViewById(R.id.login_delete_user);
                    deleteUser.setOnClickListener(new OnClickListener() {
                        // ���ɾ��deleteUserʱ,��mUsers��ɾ��ѡ�е�Ԫ��
                        @Override
                        public void onClick(View v) {

                            if (getItem(position).getId().equals(mIdString)) {
                                // ���Ҫɾ�����û�Id��Id�༭��ǰֵ��ȣ������
                                mIdString = "";
                                mPwdString = "";
                                mIdEditText.setText(mIdString);
                                mPwdEditText.setText(mPwdString);
                            }
                            mUsers.remove(getItem(position));
                            mAdapter.notifyDataSetChanged(); // ����ListView
                        }
                    });
                    return convertView;
                }

            }

            private void setListener() {
                mIdEditText.addTextChangedListener(new TextWatcher() {

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        mIdString = s.toString();
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    public void afterTextChanged(Editable s) {
                    }
                });
                mPwdEditText.addTextChangedListener(new TextWatcher() {

                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        mPwdString = s.toString();
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                    }

                    public void afterTextChanged(Editable s) {
                    }
                });
                mLoginButton.setOnClickListener(this);
                mSigninButton.setOnClickListener(this); // ��¼��ť
                mLoginMoreUserView.setOnClickListener(this);
                forgetPassword.setOnClickListener(this);
            }

            private void initView() {
                mIdEditText = (EditText) findViewById(R.id.login_edtId);
                mPwdEditText = (EditText) findViewById(R.id.login_edtPwd);
                mMoreUser = (ImageView) findViewById(R.id.login_more_user);
                mLoginButton = (Button) findViewById(R.id.login_btnLogin);
                mSigninButton = (Button) findViewById(R.id.login_btnSignin);
                mLoginMoreUserView = (ImageView) findViewById(R.id.login_more_user);
                mLoginLinearLayout = (LinearLayout) findViewById(R.id.login_linearLayout);
                mUserIdLinearLayout = (LinearLayout) findViewById(R.id.userId_LinearLayout);
                forgetPassword=(TextView)findViewById(R.id.login_txtForgotPwd);
                mTranslate = AnimationUtils.loadAnimation(this, R.anim.my_translate); // ��ʼ����������
                initLoginingDlg();
            }

            public void initPop() {
                int width = mUserIdLinearLayout.getWidth() - 4;
                int height = LayoutParams.WRAP_CONTENT;
                mPop = new PopupWindow(mUserIdListView, width, height, true);
                mPop.setOnDismissListener(this);// ���õ���������ʧʱ������

                // ע��Ҫ�������룬�������������������Ż��ô�����ʧ
                mPop.setBackgroundDrawable(new ColorDrawable(0xffffffff));

            }

            /* ��ʼ�����ڵ�¼�Ի��� */
            private void initLoginingDlg() {

                mLoginingDlg = new Dialog(this, R.style.loginingDlg);
                mLoginingDlg.setContentView(R.layout.logining_dlg);
                Window window = mLoginingDlg.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                int cxScreen = dm.widthPixels;
                int cyScreen = dm.heightPixels;

                int height = (int) getResources().getDimension(
                        R.dimen.loginingdlg_height);// ��42dp
                int lrMargin = (int) getResources().getDimension(
                        R.dimen.loginingdlg_lr_margin); // ���ұ���10dp
                int topMargin = (int) getResources().getDimension(
                        R.dimen.loginingdlg_top_margin); // ����20dp

                params.y = (-(cyScreen - height) / 2) + topMargin; // -199
		/* �Ի���Ĭ��λ������Ļ����,����x,y��ʾ�˿ؼ���"��Ļ����"��ƫ���� */

                params.width = cxScreen;
                params.height = height;
                // width,height��ʾmLoginingDlg��ʵ�ʴ�С

                mLoginingDlg.setCanceledOnTouchOutside(true); // ���õ��Dialog�ⲿ��������ر�Dialog
            }


            private void showLoginingDlg() {
                if (mLoginingDlg != null)
                    mLoginingDlg.show();
            }

            /* �ر����ڵ�¼�Ի��� */
            private void closeLoginingDlg() {
                if (mLoginingDlg != null && mLoginingDlg.isShowing())
                    mLoginingDlg.dismiss();
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login_btnLogin:
                            showLoginingDlg();
                            Log.i(TAG, mIdString + "  " + mPwdString);
                            if (mIdString == null || mIdString.equals("")) {
                                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
                                        .show();
                                closeLoginingDlg();// �رնԻ���
                            } else if (mPwdString == null || mPwdString.equals("")) {
                                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                                        .show();
                                closeLoginingDlg();// �رնԻ���
                            } else {
                                boolean mIsSave = true;
                                try {
                                    Log.i(TAG, "保存用户列表");
                                    for (User user : mUsers) { // �жϱ����ĵ��Ƿ��д�ID�û�
                                        if (user.getId().equals(mIdString)) {
                                            mIsSave = false;
                                            break;
                                        }
                                    }
                                    if (mIsSave) { // �����û�����users
                                        User user = new User(mIdString, mPwdString);
                                        mUsers.add(user);
                                    }
                                    String url = "http://139.129.47.180:8004/php/logIn.php";
                                    String data = "username=" + mIdString + "&password=" + mPwdString;
                                    SendAndReceive sar = new SendAndReceive();
                                    String receive = sar.postAndGetString(url, data);
                                    if (receive.equals("0")) {
                                        SharedPreferences mySharedPreferences = getSharedPreferences("test",
                                                Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        editor.putString("userName", mIdString);
                                        editor.putString("password", mPwdString);
                                        editor.commit();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                        closeLoginingDlg();// �رնԻ���
                                        finish();
                                        break;
                                    } else if (receive.equals("1") || receive.equals("3")) {
                                        Toast.makeText(this, "抱歉，服务器出问题了", Toast.LENGTH_SHORT).show();
                                        closeLoginingDlg();// �رնԻ���
                                        break;
                                    } else if (receive.equals("4")) {
                                        Toast.makeText(this, "抱歉，您的账号或者密码错误", Toast.LENGTH_SHORT).show();
                                        closeLoginingDlg();// �رնԻ���
                                        break;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    case R.id.login_btnSignin:
                            showLoginingDlg();
                            Log.i(TAG, mIdString + "  " + mPwdString);
                            if (mIdString == null || mIdString.equals("")) {
                                Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                                closeLoginingDlg();// �رնԻ���
                            } else if (mPwdString == null || mPwdString.equals("")) {
                                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                                closeLoginingDlg();// �رնԻ���
                            } else {
                                boolean mIsSave = true;
                                try {
                                    Log.i(TAG, "保存用户列表");
                                    for (User user : mUsers) { // �жϱ����ĵ��Ƿ��д�ID�û�
                                        if (user.getId().equals(mIdString)) {
                                            mIsSave = false;
                                            break;
                                        }
                                    }
                                    if (mIsSave) { // �����û�����users
                                        User user = new User(mIdString, mPwdString);
                                        mUsers.add(user);
                                    }
                                    String url = "http://139.129.47.180:8004/php/signIn.php";
                                    String data = "username=" + mIdString + "&password=" + mPwdString;
                                    SendAndReceive sar = new SendAndReceive();
                                    String receive = sar.postAndGetString(url, data);
                                    if (receive.equals("0")) {
                                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                                        SharedPreferences mySharedPreferences = getSharedPreferences("test",
                                                Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                                        editor.putString("userName", mIdString);
                                        editor.putString("password", mPwdString);
                                        editor.commit();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                        closeLoginingDlg();// �رնԻ���
                                        finish();
                                        break;
                                    } else if (receive.equals("1") || receive.equals("3")) {
                                        Toast.makeText(this, "抱歉，数据库出问题", Toast.LENGTH_SHORT).show();
                                        closeLoginingDlg();// �رնԻ���
                                        break;
                                    } else if (receive.equals("2")) {
                                        Toast.makeText(this, "您的账号已注册", Toast.LENGTH_SHORT).show();
                                        closeLoginingDlg();// �رնԻ���
                                        break;
                                    }else{
                                        Toast.makeText(this, "抱歉，服务器出问题", Toast.LENGTH_SHORT).show();
                                        closeLoginingDlg();// �رնԻ���
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    case R.id.login_more_user: // �����������
                        if (mPop == null) {
                            initPop();
                        }
                        if (!mPop.isShowing() && mUsers.size() > 0) {
                            // Log.i(TAG, "�л�Ϊ������ͼ��");
                            mMoreUser.setImageResource(R.drawable.login_more_down); // �л�ͼ��
                            mPop.showAsDropDown(mUserIdLinearLayout, 2, 1); // ��ʾ��������
                        }
                        break;
                    case R.id.login_txtForgotPwd:
                        Intent i = new Intent(LoginActivity.this, SetPassword.class);
                        startActivity(i);
                        closeLoginingDlg();// �رնԻ���
                        finish();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                mIdEditText.setText(mUsers.get(position).getId());
                mPwdEditText.setText(mUsers.get(position).getPwd());
                mPop.dismiss();
            }

            /* PopupWindow����dismissʱ���¼� */
            @Override
            public void onDismiss() {
                // Log.i(TAG, "�л�Ϊ������ͼ��");
                mMoreUser.setImageResource(R.drawable.login_more_up);
            }

            /* �˳���Activityʱ����users */
            @Override
            public void onPause() {
                super.onPause();
                try {
                    Utils.saveUserList(LoginActivity.this, mUsers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

