package com.jiangdg.keepappalive.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志打印的工具类
 */
@SuppressLint("SimpleDateFormat")
public final class LogHelper {

	public static boolean mIsDebugMode = true;

	public static boolean mIsSaveLog = true;

	private static String serialNo = getSerialNo();

	private static String TAG = "aaa";

	private enum Level {
		TRACE, DEBUG, INFO, WARN, ERROR, NOSAVE
	}

	private static void log(Level level, String str) {
		if (mIsDebugMode) {
			switch (level) {
			case TRACE:
				Log.v(TAG, str);
				break;
			case DEBUG:
				Log.d(TAG, str);
				break;
			case INFO:
				Log.i(TAG, str);
				break;
			case WARN:
				Log.w(TAG, str);
				break;
			case ERROR:
				Log.e(TAG, str);
				break;
			case NOSAVE:
				Log.d(TAG, str);
				break;
			}
			if (mIsSaveLog) {
				if (level.equals(Level.TRACE)) {
					writeLogToFile("[TRACE] " + str);
				} else if (level.equals(Level.DEBUG)) {
					writeLogToFile("[DEBUG] " + str);
				} else if (level.equals(Level.INFO)) {
					writeLogToFile("[INFO] " + str);
				} else if (level.equals(Level.WARN)) {
					writeLogToFile("[WARN] " + str);
				} else if (level.equals(Level.ERROR)) {
					writeLogToFile("[ERROR] " + str);
				} else if (level.equals(Level.NOSAVE)) {
				}
			}
		}
	}

	public static void trace(String str) {
		log(Level.TRACE, str);
	}

	public static void debug(String str) {
		log(Level.DEBUG, str);
	}

	public static void info(String str) {
		log(Level.INFO, str);
	}

	public static void warn(String str) {
		log(Level.WARN, str);
	}

	public static void warn(String str, Throwable t) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);
		log(Level.WARN, str + " | " + result.toString());
	}

	public static void error(String str) {
		log(Level.ERROR, str);
	}

	public static void nosave(String str) {
		log(Level.NOSAVE, str);
	}

	public static void error(String str, Throwable t) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);
		log(Level.ERROR, str + " | " + result.toString());
	}

	public static void printStackTrace(Throwable throwable) {
		if (mIsDebugMode) {
			Log.w(TAG, "", throwable);
		}
	}

	/**
	 * The path of log file in sdcard, the path of sdk is represented by
	 * Environment.getExternalStorageDirectory().getPath(). Default value is
	 * Environment.getExternalStorageDirectory().getPath() +
	 * "/Android/data/com.suntek.mway.rcs/logs".
	 */
	public static String APP_LOG_ROOT_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/aaa";

	private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

	/**
	 * Write or Append logs to file. It the file is not exists, then creates and
	 * write logs in it, or appends logs to a exist file.
	 * 
	 * @param filename
	 *            the absolute path of log file
	 * @param tag
	 *            the tag of log
	 * @param text
	 *            log text
	 */
	public synchronized static void writeLogToFile(String text) {
		if (text == null || "".equals(text.trim())) {
			return;
		}

		File filePath = new File(APP_LOG_ROOT_PATH);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date(System.currentTimeMillis()));
		Date nowtime = new Date();
		String fileName = "app-" + date + "-" + serialNo + ".log";
		String needWriteMessage = "\n" + myLogSdf.format(nowtime) + ": " + text;
		File file = new File(APP_LOG_ROOT_PATH, fileName);
		FileWriter filerWriter = null;
		BufferedWriter bufWriter = null;
		try {
			filerWriter = new FileWriter(file, true);
			bufWriter = new BufferedWriter(filerWriter);
			bufWriter.write(needWriteMessage);
			bufWriter.flush();
		} catch (IOException e) {
			// Nothing to do
		} finally {
			try {
				if (filerWriter != null) {
					filerWriter.close();
				}
				if (bufWriter != null) {
					bufWriter.close();
				}
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 获取设备SN串号
	 * 
	 * @return
	 */
	public static String getSerialNo() {
		String str = "";
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			str = (String) get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
