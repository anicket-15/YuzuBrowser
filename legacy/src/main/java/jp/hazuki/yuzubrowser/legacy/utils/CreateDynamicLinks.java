package jp.hazuki.yuzubrowser.legacy.utils;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;


public class CreateDynamicLinks {


    private static final String domainUrlPrefix = "https://aniketbrowser.page.link";
    private static final String appPackageName = "jp.hazuki.yuzubrowser.browser";
    private static final Uri playStoreLink = Uri.parse("https://play.google.com/store/apps/details?id=jp.hazuki.yuzubrowser");

    private DynamicLink dynamicLink;

    public String getDynamicLink(String URL, String TITLE)
    {

        dynamicLink = FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse(URL))
            .setDomainUriPrefix(domainUrlPrefix)
            .setAndroidParameters(new DynamicLink.AndroidParameters
                .Builder(appPackageName)
                .setFallbackUrl(playStoreLink)
                .build())
            .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle(TITLE).build())
            .buildDynamicLink();

        return dynamicLink.getUri().toString();
    }


}
