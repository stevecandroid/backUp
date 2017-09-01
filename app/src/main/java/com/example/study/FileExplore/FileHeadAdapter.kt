package com.example.study.FileExplore

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.study.R
import kotlinx.android.synthetic.main.file_head_item.*
import org.jetbrains.anko.find
import java.io.File
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by 铖哥 on 2017/8/10.
 */
class FileHeadAdapter(val data : MutableList<File>) : RecyclerView.Adapter<FileHeadAdapter.FileHeadViewHolder>() {

    var usedata : MutableList<File> = mutableListOf()
    lateinit var recyclerView : RecyclerView

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FileHeadViewHolder, position: Int) {
        usedata.clear()
        for(i in data){
            usedata.add(i)
        }
        holder.textView.text=" ${usedata[position].name} / "
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileHeadViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_head_item,parent,false)
        val holder = FileHeadViewHolder(view)
        holder.textView.setOnClickListener {
            if(recyclerView==null) throw NullPointerException("Please SetUpRecycleView First")
            (recyclerView.adapter as FileAdapter).chooseBack(usedata[holder.adapterPosition])
        }
        return holder
    }

    fun setUpWithRecycleView(view : RecyclerView){
        recyclerView = view

        (recyclerView.adapter as FileAdapter).onPageChange = object : FileAdapter.PageChangeListener {
            override fun onPagechange() {
                notifyDataSetChanged()
                if(pargerChangeListener!=null){
                   pargerChangeListener.invoke()
                }
            }
        }
    }

    private lateinit var pargerChangeListener : ()->Unit?

    fun setOnPagerChangeListener( listener: ()->Unit):Unit{
        pargerChangeListener = listener
    }

    class FileHeadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView : TextView = itemView.find(R.id.fh_tv)
    }

}