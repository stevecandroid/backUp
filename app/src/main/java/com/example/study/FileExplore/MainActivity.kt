package com.example.study.FileExplore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.study.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fileAdapter : FileAdapter by lazy { FileAdapter() }
    val headerAdapter : FileHeadAdapter by lazy { FileHeadAdapter(fileAdapter.pathStack)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileAdapter.onSelectFileChangedListener = object : FileAdapter.SelectFileChangedListener {
            override fun onSelectFileChanged(selectedFile: MutableList<String>) {
                SelectedCountTextView.text="已选择文件:${selectedFile.size}个"
            }
        }
        fileAdapter.emptyViewLayoutId = R.layout.empty_view


        recyclerView.adapter=fileAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val headLayoutManager = LinearLayoutManager(this)
        headLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        header.layoutManager=headLayoutManager

        headerAdapter.setUpWithRecycleView(recyclerView)
        headerAdapter.setOnPagerChangeListener {
            header.smoothScrollToPosition(fileAdapter.pathStack.size)
        }
        header.adapter = headerAdapter
    }

    override fun onBackPressed() {
        if(fileAdapter.isBottom) {
            super.onBackPressed()
        }else{
            fileAdapter.onBackPressed()
        }
    }
}
