/*
 * Copyright (C) 2017-2021 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.hazuki.yuzubrowser

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.BadParcelableException
import android.os.Bundle
import android.provider.Browser
import android.speech.RecognizerResultsIntent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import jp.hazuki.yuzubrowser.browser.BrowserActivity
import jp.hazuki.yuzubrowser.legacy.Constants.intent.EXTRA_OPEN_FROM_YUZU
import jp.hazuki.yuzubrowser.legacy.utils.WebUtils
import jp.hazuki.yuzubrowser.ui.settings.AppPrefs
import jp.hazuki.yuzubrowser.ui.utils.isUrl
import jp.hazuki.yuzubrowser.ui.utils.makeUrlFromQuery

class HandleIntentActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null && intent.action != null) {
            handleIntent(intent)
        }

        finish()
    }

    private fun checkFirebase(intent: Intent) : Boolean
    {
        var flag = false
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener (this){
                var deepLink : Uri?=null
                if(it!=null)
                {
                    deepLink = it.link
                    Log.d("checkURL",deepLink.toString())
                    flag = true
                }
            }
        return flag
    }

    private fun handleIntent(intent: Intent) {

        var url : String ?= null
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener (this){
                var deepLink : Uri?=null
                if(it!=null)
                {
                    deepLink = it.link
                    Log.d("checkURL",deepLink.toString())
                    url = deepLink.toString()
                    startBrowser(url!!, true, true)
                }
            }
    }

    private fun startBrowser(url: String, window: Boolean, openInNewTab: Boolean) {
        val send = Intent(this, BrowserActivity::class.java)
        send.action = Intent.ACTION_VIEW
        send.data = Uri.parse(url)
        send.putExtra(BrowserActivity.EXTRA_WINDOW_MODE, window)
        send.putExtra(BrowserActivity.EXTRA_SHOULD_OPEN_IN_NEW_TAB, openInNewTab)
        startActivity(send)
    }
}
