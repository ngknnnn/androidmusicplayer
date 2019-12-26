package vn.edu.usth.musicplayer.searchBar;

import android.util.Log;

public class LogHelper {
    public static final boolean mIsDebugMode = true;
    private static final String TAG = "JJSearchView";

    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()_%s";

    public static void trace(String str) {
        if (mIsDebugMode) {
            StackTraceElement traceElement = Thread.currentThread()
                    .getStackTrace()[3];
            String className = traceElement.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            String logText = String.format(CLASS_METHOD_LINE_FORMAT,
                    className,
                    traceElement.getMethodName(),
                    String.valueOf(traceElement.getLineNumber()));
            Log.i(TAG, logText + "->" + str);
        }
    }

    public static void printStackTrace(Throwable throwable) {
        if (mIsDebugMode) {
            Log.w(TAG, "", throwable);
        }
    }
}
