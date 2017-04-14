package com.example.zxl.keyboarddemo.test4;

import android.app.Instrumentation;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.example.zxl.keyboarddemo.R;

import java.io.IOException;
import java.util.List;


/**
 * @Description:
 * @Author: zxl
 * @Date: 2017/4/11 14:48
 */

public class Test4KeyBoardUtils {
    private Keyboard numberKeyBoard;// 数字键盘
    private Keyboard qwertyKeyBoard;// 字母键盘
    private KeyboardView mKeyboardView;// 键盘容器
    public boolean isNum = false;// 是否数据键盘
    public boolean isUpper = false;// 是否大写

    public Test4KeyBoardUtils(Context context, KeyboardView keyboardView) {
        this.mKeyboardView = keyboardView;
        numberKeyBoard = new Keyboard(context, R.xml.test1_keyboard_num);
        qwertyKeyBoard = new Keyboard(context, R.xml.test1_keyboard_qwerty);
        mKeyboardView.setKeyboard(numberKeyBoard);
        isNum = true;
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);// 是否上浮信息
        mKeyboardView.setOnKeyboardActionListener(listener);
    }

    public void keyDownUpSync(final int primaryCode) {
        Log.e("TAG", "keyDownUpSync==" + primaryCode);
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE://删除
                if (mWebView != null) {
                    sendWebViewKey(KeyEvent.KEYCODE_DEL);
                    return;
                }
                sendKeyToTargetView(KeyEvent.KEYCODE_DEL);
                break;
            case Keyboard.KEYCODE_SHIFT: //大小写切换
                changeKey();
                mKeyboardView.setKeyboard(qwertyKeyBoard);
                break;
            case Keyboard.KEYCODE_MODE_CHANGE://键盘转换
                changeKeyTONum();
                break;
            case Keyboard.KEYCODE_CANCEL://取消，键盘关闭
                mKeyboardView.setFocusable(true);
                mKeyboardView.setFocusableInTouchMode(true);
                mKeyboardView.requestFocus();
                hideKeyboard();
                break;
            default:
                if (mWebView != null) {
                    sendWebViewStr(Character.toString((char) primaryCode) + "");
                    return;
                }
//                sendStringToTargetView(Character.toString((char) primaryCode) + "");
                //sendKeyToTargetView(convertAscii2Key(primaryCode));
                //setKeyPress(code);
                break;
        }
    }

    private WebView mWebView;

    public void setBindWebView(WebView webView) {
        this.mWebView = webView;
    }


    /**
     * 将软件盘中的键值 转成KeyEvent中的key值
     */
    public int convertAscii2Key(int primaryCode) {
        int code = 0;
        if (primaryCode >= 48 && primaryCode <= 57) {
            //是数字  keyevent 0-9 的键值为7-16    ASCII 48-57
            code = primaryCode - 41;
        } else if (primaryCode >= 65 && primaryCode < 90) {
            //大写字母   keyevent A-Z 的键值为29-54    ASCII 65-90
            code = primaryCode - 36;
        } else if (primaryCode >= 97 && primaryCode <= 122) {
            //小写字母   keyevent a-z 的键值为29-54    ASCII 97-122
            code = primaryCode - 68;
        } else if (primaryCode == 32) {
            // 空格 keyevent 空格 的键值为62    ASCII 32
            code = KeyEvent.KEYCODE_SPACE;
        } else if (primaryCode == 44) {
            // 逗号 keyevent , 的键值为159    ASCII 44
            code = KeyEvent.KEYCODE_NUMPAD_COMMA;
        } else if (primaryCode == 46) {
            // 点  keyevent . 的键值为158    ASCII 46
            code = KeyEvent.KEYCODE_PERIOD;
        }
        return code;
    }

    /**
     * 模拟键盘点击事件  键值
     */
    public void sendKeyToTargetView(final int code) {
        Log.e("TAG", "sendKeyToTargetView==" + code);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(code);
            }
        }).start();
    }

    /**
     * 模拟键盘点击事件 字符串
     */
    public void sendStringToTargetView(final String str) {
        Log.e("TAG", "sendStringToTargetView==" + str);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Instrumentation inst = new Instrumentation();
                inst.sendStringSync(str);
            }
        }).start();
    }

    // 模拟键盘按键，Keycode对应Android键盘按键的的keycode
    public void setKeyPress(int keycode) {
        try {
            String keyCommand = "input keyevent " + keycode;
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(keyCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWebViewKey(int keyCode) {
        mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
        mWebView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
    }

    public void sendWebViewStr(String text) {
        KeyCharacterMap keyCharacterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);
        KeyEvent[] events = keyCharacterMap.getEvents(text.toCharArray());
        if (events != null) {
            Log.e("TAG", "sendWebViewStr==(" + text + "," + events.length + ")");
            for (int i = 0; i < events.length; i++) {
                KeyEvent keyEvent = KeyEvent.changeTimeRepeat(events[i], SystemClock.uptimeMillis(), 0);
                mWebView.dispatchKeyEvent(keyEvent);
            }
        }
    }

    /**
     * 切换键盘大小写字母
     * 按照ascii码表可知，小写字母 = 大写字母+32;
     */
    private void changeKey() {
        List<Keyboard.Key> keyList = qwertyKeyBoard.getKeys();
        if (isUpper) {//如果为真表示当前为大写，需切换为小写
            isUpper = false;
            for (Keyboard.Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {//如果为假表示当前为小写，需切换为大写
            isUpper = true;
            for (Keyboard.Key key : keyList) {
                if (key.label != null && isWord(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
        if (isUpper) {
            sendKeyToTargetView(KeyEvent.KEYCODE_CAPS_LOCK);//大写锁定键
        } else {
            sendKeyToTargetView(KeyEvent.KEYCODE_NUM_LOCK);//小写锁定键
        }
    }


    /**
     * 数字键盘切换
     */
    private void changeKeyTONum() {
        if (isNum) { //当前为数字键盘,切换为全字母键盘
            isNum = false;
            mKeyboardView.setKeyboard(qwertyKeyBoard);
        } else {//当前为全字母键盘，切换为数字键盘
            isNum = true;
            mKeyboardView.setKeyboard(numberKeyBoard);
        }
    }

    /**
     * 判断是否为字母
     *
     * @param str 需判断的字符串
     */
    private boolean isWord(String str) {
        String wordStr = "abcdefghijklmnopqrstuvwxyz";
        if (wordStr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }

    /**
     * 展示自定义软键盘
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏自定义软键盘
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    // 键盘动作监听
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(final int primaryCode, int[] keyCodes) {
            keyDownUpSync(primaryCode);
        }

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
}
