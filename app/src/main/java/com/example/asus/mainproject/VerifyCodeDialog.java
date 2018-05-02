package com.example.asus.mainproject;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Asus on 5/1/2018.
 */

public class VerifyCodeDialog extends Dialog {

    public VerifyCodeDialog(@NonNull Context context) {
        super(context);

        setContentView(R.layout.verify_code_dialog);


    }
}
