package com.example.study.ContentReader

import android.app.Activity
import android.app.LoaderManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.CursorLoader
import android.support.v7.app.AppCompatActivity

/**
 * Created by 铖哥 on 2017/8/10.
 */
class ContentReader(act : AppCompatActivity, callbacks: LoaderManager.LoaderCallbacks<Cursor>? , id : Int){

      init {
          act.loaderManager.initLoader(id, null, callbacks)
      }

}

class ContentLoader(context: Context?, uri: Uri?, projection: Array<out String>?, selection: String?,
                    selectionArgs: Array<out String>?, sortOrder: String?)
    : CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder) {

    override fun loadInBackground(): Cursor {

        return super.loadInBackground()
    }

    override fun onContentChanged() {
        super.onContentChanged()
    }

    interface onContentChangedListener{

    }
}


