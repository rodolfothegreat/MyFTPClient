package com.rde.futura.barcodescanner;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.util.LinkedList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {
    public static final String SERVICE_DATA_DOWNLOADED = "com.rde.futura.barcodescanner.service.data.DOWNLOADED";
    public static final String SERVICE_DATA_RECEIVED = "com.rde.futura.barcodescanner.service.data.RECEIVED";
    private static final String ACTION_DOWNLOAD = "com.rde.futura.barcodescanner.action.DOWNLOAD";
    private static final String ACTION_BAZ = "com.rde.futura.barcodescanner.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM_USERCODE = "com.rde.futura.barcodescanner.extra.user.code";
    private static final String EXTRA_PARAM2 = "com.rde.futura.barcodescanner.extra.PARAM2";

    public DownloadService() {
        super("DownloadService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDOWNLOAD(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_PARAM_USERCODE, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
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
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(ACTION_BAZ);
        //i