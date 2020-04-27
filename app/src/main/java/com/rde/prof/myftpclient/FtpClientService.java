package com.rde.prof.myftpclient;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FtpClientService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DIR = "com.rde.prof.myftpclient.action.DIR";
    private static final String ACTION_BAZ = "com.rde.prof.myftpclient.action.BAZ";

    public static final String SERVICE_DIR = "com.rde.prof.myftpclient.service.DIR";
    public static final String SERVICE_DIR_DATA = "com.rde.prof.myftpclient.service.DIR.DATA";



    // TODO: Rename parameters
    private static final String EXTRA_PARAM_URL = "com.rde.prof.myftpclient.extra.URL";
    private static final String EXTRA_PARAM_UID = "com.rde.prof.myftpclient.extra.UID";
    private static final String EXTRA_PARAM_PASSWORD = "com.rde.prof.myftpclient.extra.PASSWORD";
    private static final String EXTRA_PARAM_DIR = "com.rde.prof.myftpclient.extra.DIR";
    private static final String EXTRA_PARAM_TLS = "com.rde.prof.myftpclient.extra.TLS";

    public FtpClientService() {
        super("FtpClientService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDir(Context context, String url, String userId, String password, String remoteDir, boolean usesTls) {
        Intent intent = new Intent(context, FtpClientService.class);
        intent.setAction(ACTION_DIR);
        intent.putExtra(EXTRA_PARAM_URL, url);
        intent.putExtra(EXTRA_PARAM_UID, userId);
        intent.putExtra(EXTRA_PARAM_PASSWORD, password);
        intent.putExtra(EXTRA_PARAM_DIR, remoteDir);
        intent.putExtra(EXTRA_PARAM_TLS, usesTls);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FtpClientService.class);
        intent.setAction(ACTION_BAZ);
       // intent.putExtra(EXTRA_PARAM1, param1);
      //  intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DIR.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_PARAM_URL);
               // String userId, String password, String remoteDir, boolean usesTls
                final String userId = intent.getStringExtra(EXTRA_PARAM_UID);
                final String password = intent.getStringExtra(EXTRA_PARAM_PASSWORD);
                final String remoteDir = intent.getStringExtra(EXTRA_PARAM_DIR);
                final boolean usesTls = intent.getBooleanExtra(EXTRA_PARAM_TLS, false);
                handleActionDir(url, userId, password, remoteDir, usesTls);
            } else if (ACTION_BAZ.equals(action)) {
               // final String param1 = intent.getStringExtra(EXTRA_PARAM1);
               // final String param2 = intent.getStringExtra(EXTRA_PARAM2);
               // handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDir(String url, String userId, String password, String remoteDir, boolean usesTls) {
        // TODO: Handle action Foo
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(SERVICE_DIR);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        String theFiles = "";
        FTPSClient ftpClient = new FTPSClient();
        try {
            ftpClient.connect(InetAddress.getByName(url));


//            ftpClient.connect(InetAddress.getByName(url));
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(userId, password);
            ftpClient.changeWorkingDirectory(remoteDir);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            FTPFile[]  lstFiles = ftpClient.listFiles(".");
            for(int i = 0; i < lstFiles.length; i++)
            {
             //   if(lstFiles[i].isDirectory())
               //     continue;

                //if(lstFiles[i].isFile())
                //{
                    if(i == 0)
                        theFiles = lstFiles[i].getName();
                    else
                        theFiles = theFiles + ",  " + lstFiles[i].getName();
                //}
            }

            ftpClient.logout();
            ftpClient.disconnect();

        }
        catch(Exception e)
        {
             theFiles = e.getMessage();
        }



        broadcastIntent.putExtra(SERVICE_DIR_DATA, theFiles );
        sendBroadcast(broadcastIntent);

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
