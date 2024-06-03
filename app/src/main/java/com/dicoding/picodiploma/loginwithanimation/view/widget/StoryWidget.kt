package com.dicoding.picodiploma.loginwithanimation.view.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem

@Suppress("DEPRECATION")
class StoryWidget : AppWidgetProvider() {
    companion object {
        const val ACTION_UPDATE_STORIES = "com.dicoding.picodiploma.loginwithanimation.ACTION_UPDATE_STORIES"
        const val EXTRA_STORIES = "extra_stories"
        private const val ACTION_REFRESH = "com.dicoding.picodiploma.loginwithanimation.ACTION_REFRESH"
        private var stories: List<ListStoryItem> = emptyList()

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StoryWidgetService::class.java)
            val views = RemoteViews(context.packageName, R.layout.widget_story)

            views.setRemoteAdapter(R.id.widget_list_view, intent)
            views.setRemoteAdapter(appWidgetId, R.id.widget_list_view, intent)

            views.setEmptyView(R.id.widget_list_view, R.id.widget_empty_view)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view)

            val refreshIntent = Intent(context, StoryWidget::class.java).apply {
                action = ACTION_REFRESH
            }
            val pendingIntent = PendingIntent.getBroadcast(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            views.setOnClickPendingIntent(R.id.widget_refresh_button, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun setStories(data: List<ListStoryItem>) {
            stories = data
        }

        fun getStories(): List<ListStoryItem> {
            return stories
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_UPDATE_STORIES) {
            val stories = intent.getParcelableArrayListExtra<ListStoryItem>(EXTRA_STORIES)
            if (stories != null) {
                setStories(stories)
                Log.d("StoryWidget", "Received stories: $stories")
            } else {
                Log.d("StoryWidget", "No stories received")
            }
            Log.d("StoryWidget", "Intent received: ${intent.action}")
        }
    }

}