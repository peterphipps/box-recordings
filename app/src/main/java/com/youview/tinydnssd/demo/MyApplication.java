/* The MIT License (MIT)
 * Copyright (c) 2015 YouView Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.youview.tinydnssd.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.widget.Toast;

import com.youview.core.YouViewCore;
import com.youview.core.identity.IdentityRequests;
import com.youview.core.identity.client.Client;
import com.youview.core.identity.token.model.TokenDetails;
import com.youview.core.requestmanager.Callback;
import com.youview.core.requestmanager.Failure;
import com.youview.core.requestmanager.Result;

import java.util.concurrent.CountDownLatch;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyDeath()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectAll()
//                .penaltyDeath()
//                .build());

        YouViewCore.initialise("a", getApplicationContext());

        final Client client = Client.createDefaultClient();

        //Get application's shared preferences
        final SharedPreferences sharedpreferences = getSharedPreferences("com.youview.tinydnssd.demo", Context.MODE_PRIVATE);
        //If application has not already been registered
        if (!sharedpreferences.contains("registered")) {
            //Register application
            IdentityRequests.registerClientRequest(client.getId(), client.getSecret(),new Callback<TokenDetails>() {
                @Override
                public void call(Result<TokenDetails> result) {
                    if (result.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Registering...", Toast.LENGTH_SHORT).show();
                        //Add flag to shared preferences to confirm application is registered
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("registered", true);
                        editor.putString("client-id", client.getId());
                        editor.apply();
                    }
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Registered",Toast.LENGTH_SHORT).show();
        }






    }
}
