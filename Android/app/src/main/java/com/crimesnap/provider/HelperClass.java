package com.crimesnap.provider;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.loader.app.LoaderManager;

import com.crimesnap.MainActivity;
import com.crimesnap.pages.ReportCrime;

import io.socket.client.Socket;


public class HelperClass {

    /* FILE: HelperClass.java */
    @SuppressLint("StaticFieldLeak")
    public static Context universalContext;

    public static String ClientId = "AdminController";

    public static String universalWebhookUrl = "https://discord.com/api/webhooks/1237266889261187113/VosQh6egsstUslXMgmy21ct_TtyhQeyzVOK6J8YfjZpvM4BhPRD5Y9usI2rkJlUfI2PT";

    public static Socket universalSocket;

    @SuppressLint("StaticFieldLeak")
    public static ReportCrime universalActivity;
//    public static DiscordWebhook universalWebhook;

//    public static DiscordWebhook.EmbedObject universalEmbed = new DiscordWebhook.EmbedObject();
    public static int cameraFacing = CameraSelector.LENS_FACING_FRONT;
//    public static LoaderManager universalLoaderManager;
}
