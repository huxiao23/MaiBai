package com.maibai.user.base;import android.app.Activity;import android.content.BroadcastReceiver;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.support.v7.app.AppCompatActivity;import android.view.KeyEvent;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.LinearLayout.LayoutParams;import com.maibai.user.R;public abstract class BaseActivity extends AppCompatActivity {    protected Context mContext;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView();        setContentView(R.layout.activity_base_layout);        initParent();        findViews();        setListensers();        MyApplication myApplication = (MyApplication) getApplication();        myApplication.addTempActivityInBackStack(this);    }    @Override    protected void onActivityResult(int arg0, int arg1, Intent arg2) {        // TODO Auto-generated method stub        super.onActivityResult(arg0, arg1, arg2);    }    @Override    protected void onDestroy() {        super.onDestroy();        unregisterReceiver(mBroadcastReceiver);    }    protected BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {        @Override        public void onReceive(Context context, Intent intent) {            finish();        }    };    private void initParent() {        mContext = this;        LinearLayout subCententView = (LinearLayout) this.findViewById(R.id.base_sub_activty_layout);        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,                ViewGroup.LayoutParams.MATCH_PARENT);        View centerView = View.inflate(mContext, setContentView(), null);        subCententView.addView(centerView, layoutParams);    }    protected boolean isShowNoNetworksPrompt() {        return true;    }    /**     * 跳转到某个Activity     */    protected void gotoActivity(Context mContext, Class<?> toActivityClass, Bundle bundle) {        Intent intent = new Intent(mContext, toActivityClass);        if (bundle != null) {            intent.putExtras(bundle);        }        mContext.startActivity(intent);        ((Activity) mContext).overridePendingTransition(R.anim.push_right_in, R.anim.not_exit_push_left_out);    }    /**     * 退出到某个Activity     */    protected void backActivity() {        finish();        overridePendingTransition(R.anim.not_exit_push_left_in, R.anim.push_right_out);    }    @Override    public boolean onKeyDown(int keyCode, KeyEvent event) {        // 所有需要统一处理的onKeyDown写在这个if里面        if (isOnKeyDown()) {            if (keyCode == KeyEvent.KEYCODE_BACK) {                backActivity();            }        }        return super.onKeyDown(keyCode, event);    }    protected boolean isOnKeyDown() {        return true;    }    /**     * 加载子类布局     */    protected abstract int setContentView();    /**     * 加载控件     */    protected abstract void findViews();    /**     * 设置监听     */    protected abstract void setListensers();    /**     * 请求网络之前     */    protected void getDataFromServerOnStart(String url) {    }    /**     * 访问服务器时无网络     */    protected void getDataFromServerNoNetworksFound(String url) {    }    /**     * 请求网络成功返回正确结果     */    protected void getDataFromServerOnSucess(String result, String url) {    }    /**     * 请求网络成功返回错误结果     */    protected void getDataFromServerOnError(String result, String url, int errCode) {    }    /**     * 请求网络失败     */    protected void getDataFromServerOnFailure(String url) {    }    @Override    protected void onResume() {        super.onResume();//		MobclickAgent.onResume(this);//		JPushInterface.onResume(this);        MyApplication myApplication = (MyApplication) getApplication();        myApplication.setResumeContext(this);    }    @Override    protected void onPause() {        super.onPause();//		MobclickAgent.onPause(this);//		JPushInterface.onPause(this);    }}