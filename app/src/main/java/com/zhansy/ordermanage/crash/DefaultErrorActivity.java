/*
 * Copyright 2015 Eduard Ereza Martínez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhansy.ordermanage.crash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.zhansy.ordermanage.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 程序崩溃跳转的页面
 */
public final class DefaultErrorActivity extends Activity {
    @InjectView(R.id.btn_restart_app)
    Button restartButton;
    @InjectView(R.id.btn_clear_data)
    Button clearDataButton;
    @InjectView(R.id.tv_crash)
    TextView tv_crash;

    private String errorData;//程序错误的错误信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customactivityoncrash_default_error_activity);
        ButterKnife.inject(this);

        final Class<? extends Activity> restartActivityClass = CustomActivityOnCrash.getRestartActivityClassFromIntent(getIntent());
        final CustomActivityOnCrash.EventListener eventListener = CustomActivityOnCrash.getEventListenerFromIntent(getIntent());

        if (restartActivityClass != null) {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DefaultErrorActivity.this, restartActivityClass);
                    CustomActivityOnCrash.restartApplicationWithIntent(DefaultErrorActivity.this, intent, eventListener);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(DefaultErrorActivity.this, eventListener);
                }
            });
        }
        //点击清除数据时，跳转到 应用程序管理界面
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + getPackageName()));
                startActivity(i);
            }
        });

        if (CustomActivityOnCrash.isShowErrorDetailsFromIntent(getIntent())){
            errorData = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this,getIntent());//错误信息内容
            tv_crash.setText(errorData);

        }

        int defaultErrorActivityDrawableId = CustomActivityOnCrash.getDefaultErrorActivityDrawableIdFromIntent(getIntent());
        ImageView errorImageView = ((ImageView) findViewById(R.id.customactivityoncrash_error_activity_image));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId, getTheme()));
        } else {
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId));
        }
    }
}
