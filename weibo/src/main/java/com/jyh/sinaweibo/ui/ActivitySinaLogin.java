package com.jyh.sinaweibo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.util.AccessTokenKeeper;
import com.jyh.sinaweibo.util.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class ActivitySinaLogin extends AppCompatActivity {

    private AuthInfo mAuthInfo;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sina_login);
        loginWeibo();
    }

    private void loginWeibo() {
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorizeWeb(new WeiboListener());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class WeiboListener implements WeiboAuthListener {

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            Toast.makeText(ActivitySinaLogin.this,
                    getResources().getString(R.string.sinasso_cancle),
                    Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onComplete(Bundle arg0) {
            // TODO Auto-generated method stub
            Oauth2AccessToken accessToken = Oauth2AccessToken
                    .parseAccessToken(arg0);
            if (accessToken.isSessionValid()) {

                //将令牌保存到SharedPreferences
                AccessTokenKeeper.writeAccessToken(ActivitySinaLogin.this,
                        accessToken);
                Toast.makeText(ActivitySinaLogin.this,
                        getResources().getString(R.string.sinasso_success),
                        Toast.LENGTH_LONG).show();
                //登录成功进入首页
                startActivity(new Intent(ActivitySinaLogin.this,
                        MainActivity.class));
            } else {
                String code = arg0.getString("code");
                String message = getString(R.string.sinasso_feild);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;

                }
                Toast.makeText(ActivitySinaLogin.this, message,
                        Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onWeiboException(WeiboException arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(ActivitySinaLogin.this,
                    getResources().getString(R.string.sinasso_feild),
                    Toast.LENGTH_LONG).show();
            Log.d("xfc", "onWeiboException: " + arg0.toString());
            finish();
        }
    }
}
