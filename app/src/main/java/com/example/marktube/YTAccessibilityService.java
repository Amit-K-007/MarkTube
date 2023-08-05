package com.example.marktube;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import java.util.List;

public class YTAccessibilityService extends AccessibilityService {

    private static final String TAG = "YTAccessibilityService";
    static CharSequence timestamp="00:00";
    static CharSequence timestamp2="00:00";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

        ///////Need to click on subscriptions before fetching timestamp FIX IT
        ///////CAN AUTO CLICK ON HOME BUTTON LEFT BOTTOM

        Log.e(TAG, "onAccessibilityEvent: " );
        AccessibilityNodeInfo nodeInfo2 = accessibilityEvent.getSource();
        if(nodeInfo2!=null  && nodeInfo2.getContentDescription()!=null) {
            CharSequence contentDescriptiond = nodeInfo2.getContentDescription();
            contentDescriptiond = contentDescriptiond.toString();
            Log.e(TAG, "1am8it: "+contentDescriptiond );
            if (contentDescriptiond.equals("Play video")) {
                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                if (nodeInfo != null) {
                    List<AccessibilityNodeInfo> target = nodeInfo.findAccessibilityNodeInfosByViewId("com.google.android.youtube:id/time_bar_current_time");
                    if (!target.isEmpty()) {
                        AccessibilityNodeInfo targetview = target.get(0);
                        if (targetview != null) {
                            timestamp = targetview.getText();
                            if (timestamp != null) {
                                Toast.makeText(this, timestamp, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if(timestamp.toString().charAt(0)=='-') {
                        List<AccessibilityNodeInfo> target2 = nodeInfo.findAccessibilityNodeInfosByViewId("com.google.android.youtube:id/time_bar_total_time");
                        if (!target2.isEmpty()) {
                            AccessibilityNodeInfo targetview2 = target2.get(0);
                            if (targetview2 != null) {
                                timestamp2 = targetview2.getText();
                                if (timestamp2 != null) {
                                    Toast.makeText(this, timestamp2, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt: Something went wrong" );
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED;

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        info.notificationTimeout = 100;

        this.setServiceInfo(info);
        Log.e(TAG, "onServiceConnected: " );
    }
}