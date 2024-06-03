package com.dicoding.picodiploma.loginwithanimation.view.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem

class StoryRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var stories = listOf<ListStoryItem>()
    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate() {
        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
        stories = StoryWidget.getStories()
        Log.d("StoryWidget", "Stories in factory: $stories")
    }

    override fun onDataSetChanged() {
        Log.d("StoryWidget", "onDataSetChanged called")
        stories = StoryWidget.getStories()
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view)
        Log.d("StoryWidget", "Stories in factory after data set changed: $stories")
    }


    override fun onDestroy() {}

    override fun getCount(): Int {
        return stories.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.item_widget_story)
        val story = stories[position]
        views.setTextViewText(R.id.widget_item_story_title, story.name)
        views.setTextViewText(R.id.widget_item_story_description, story.description)

        val fillInIntent = Intent()
        views.setOnClickFillInIntent(R.id.widget_item_container, fillInIntent)

        return views
    }
    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}