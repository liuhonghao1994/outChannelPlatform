package com.dxt.common;

public class LogHelper {
    // 当前文件名
    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getFileName();
    }

    // 当前方法名
    public static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName() + "-" + traceElement.getLineNumber() + "-";
    }
    
    // 当前方法名进入start
    public static String _FUNC_START_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName() + "-start...";
    }
    
    // 当前方法名进入start
    public static String _FUNC_EXCEPTION_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName() + "-" + traceElement.getLineNumber() + "-Exception:";
    }

    // 当前行号
    public static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }
}
