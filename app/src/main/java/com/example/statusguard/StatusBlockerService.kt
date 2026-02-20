package com.example.statusguard

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import java.time.LocalTime

class StatusBlockerService : AccessibilityService() {

    private var lastToastTime: Long = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val packageName = event?.packageName?.toString() ?: return
        if (packageName != WHATSAPP_PACKAGE) return
        if (!isInsideBlockedWindow()) return

        val root = rootInActiveWindow ?: return
        if (!containsStatusKeywords(root)) return

        performGlobalAction(GLOBAL_ACTION_BACK)

        val now = System.currentTimeMillis()
        if (now - lastToastTime > TOAST_COOLDOWN_MS) {
            Toast.makeText(this, R.string.status_blocked_message, Toast.LENGTH_SHORT).show()
            lastToastTime = now
        }
    }

    override fun onInterrupt() = Unit

    private fun isInsideBlockedWindow(): Boolean {
        val now = LocalTime.now()
        val start = PreferencesManager.getStartTime(this)
        val end = PreferencesManager.getEndTime(this)

        return if (start <= end) {
            now >= start && now < end
        } else {
            now >= start || now < end
        }
    }

    private fun containsStatusKeywords(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false

        val text = (node.text?.toString() ?: "") + " " + (node.contentDescription?.toString() ?: "")
        val normalized = text.lowercase()

        if (normalized.contains("status") || normalized.contains("updates")) {
            return true
        }

        for (i in 0 until node.childCount) {
            if (containsStatusKeywords(node.getChild(i))) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val WHATSAPP_PACKAGE = "com.whatsapp"
        private const val TOAST_COOLDOWN_MS = 3000L
    }
}
