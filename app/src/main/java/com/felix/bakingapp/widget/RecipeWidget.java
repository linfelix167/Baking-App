package com.felix.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.felix.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.ingredients);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.saved_file_name), Context.MODE_PRIVATE);
        String ingredientsListText = sharedPreferences.getString(context.getResources().getString(R.string.key_string),
                context.getResources().getString(R.string.ingredients_list_is_empty));
        views.setTextViewText(R.id.appwidget_ingredients_list, ingredientsListText);

        String recipeNameText = sharedPreferences.getString(context.getResources().getString(R.string.recipe_key_string),
                context.getResources().getString(R.string.ingredients));
        views.setTextViewText(R.id.appwidget_text, recipeNameText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

