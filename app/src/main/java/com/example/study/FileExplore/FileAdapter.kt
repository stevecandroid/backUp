package com.example.study.FileExplore

import android.animation.Animator
import android.opengl.Visibility
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.study.R
import org.jetbrains.anko.find
import java.io.File
import java.nio.file.Path
import java.util.*
import java.util.logging.Level
import kotlin.properties.Delegates


/**
 * Created by 铖哥 on 2017/8/10.
 */
class FileAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var emptyViewLayoutId : Int? = null
    val selectedFile = mutableListOf<String>()
    var isBottom: Boolean = true
    var isEmpty : Boolean = false
    val pathStack = mutableListOf<File>()

    private var curData: MutableList<File> = root.listFiles().toMutableList()


    private var cur: File by Delegates.observable(root) {
        _, oldValue, newValue ->
        if (newValue.isDirectory) {
            curData = newValue.listFiles().toMutableList()
            curData.sort()
        }

        pathStack.add(newValue.absoluteFile)

        if(onPageChange!=null) {
            onPageChange!!.onPagechange()
        }

        isBottom = cur == root
        isEmpty = curData.size == 0

//        notifyItemRangeRemoved(0, oldValue.list().size)
//        notifyItemRangeInserted(0, newValue.list().size)
        notifyDataSetChanged()
    }

    init {
        curData.sort()
        pathStack.add(root.absoluteFile)
    }

    companion object {
        val root = Environment.getExternalStorageDirectory()!!
    }

    override fun getItemCount(): Int {
        if (cur.isDirectory){
            if (isEmpty && emptyViewLayoutId!=null) return 1
            return curData.size
        }

        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(!isEmpty) {
            holder as FileViewHolder
            val temp = curData[holder.adapterPosition].name;
            val pos = temp.indexOf('.')
            val format: String by lazy {
                if (pos != -1) temp.substring(pos, temp.length)
                else ""
            }
            if (curData[holder.adapterPosition].isDirectory) {
                holder.ImageView.setImageResource(R.drawable.directory)
                holder.checkBox.visibility = View.GONE
            } else {
                holder.checkBox.visibility = View.VISIBLE
                holder.checkBox.isChecked = selectedFile.has(curData[holder.adapterPosition].absolutePath)
                holder.ImageView.setImageResourceByFormat(format)
            }
            holder.textView.text = temp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        if(viewType == TYPE_EMPTY && emptyViewLayoutId!= null){
            return EmptyFileViewHolder(LayoutInflater.from(parent.context).inflate(emptyViewLayoutId!!, parent, false))
        }else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.file_explore_item, parent, false)
            val holder = FileViewHolder(view)
            view.find<LinearLayout>(R.id.fe_ll).setOnClickListener {
                if (curData[holder.adapterPosition].isDirectory) {
                    cur = curData[holder.adapterPosition]
                } else {
                    val absPath = curData[holder.adapterPosition].absolutePath
                    if (selectedFile.has(absPath)) {
                        holder.checkBox.isChecked = false
                        selectedFile.remove(absPath)
                    } else {
                        holder.checkBox.isChecked = true
                        selectedFile.add(absPath)
                    }
                    notifyItemChanged(holder.adapterPosition)
                    if (onSelectFileChangedListener != null) onSelectFileChangedListener!!.onSelectFileChanged(selectedFile)
                }
            }
            return holder
        }
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var textView : TextView
        lateinit var ImageView :ImageView
        lateinit var checkBox :CheckBox

        init {
            if(!isEmpty){
                textView = itemView.find<TextView>(R.id.fe_tv)
                ImageView = itemView.find<ImageView>(R.id.fe_im)
                checkBox = itemView.find<CheckBox>(R.id.fe_cb)
            }
        }
    }

    open class EmptyFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    fun onBackPressed() {
        backToChose(cur.parentFile)
    }

    fun chooseBack(file: File) {
        backToChose(file)
    }

    fun backToChose(file: File){
        while(true){
            if(pathStack.removeAt(pathStack.size-1).absolutePath == file.absolutePath){
                cur = file
                break
            }

            if(pathStack.size == 0){
                cur = file
                break
            }
        }
    }

    val TYPE_NOTEMPTY = 1;
    val TYPE_EMPTY = 2;
    override fun getItemViewType(position: Int): Int {
        if(isEmpty) return TYPE_EMPTY
        else return TYPE_NOTEMPTY
    }

    var onSelectFileChangedListener: SelectFileChangedListener? = null

    interface SelectFileChangedListener {
        fun onSelectFileChanged(selectedFile: MutableList<String>)
    }

    var onPageChange: PageChangeListener? = null
    interface PageChangeListener{
        fun onPagechange()
    }

    fun MutableList<String>.has(str: String): Boolean {
        for (item in this) {
            if (item == str) {
                return true
            }
        }
        return false
    }

    fun MutableList<File>.sort() {
        Collections.sort(this) { o1, o2 -> o1.name.toUpperCase().compareTo(o2.name.toUpperCase()) }
        var i = this.size - 1
        while (i >= 0) {
            if (this[i].name.startsWith(".")) {
                this.removeAt(i)
            } else {
                if (this[i].isFile) {
                    this.add(this.removeAt(i))
                }
            }
            i--
        }
    }
}

fun ImageView.setImageResourceByFormat(format: String) {
    when (format.toLowerCase()) {
        ".jpg", ".png", ".gif" -> setImageResource(R.drawable.pic)
        ".pdf" -> setImageResource(R.drawable.pdf)
        ".mp3", ".flac" -> setImageResource(R.drawable.music)
        ".txt" -> setImageResource(R.drawable.txt)
        ".doc" -> setImageResource(R.drawable.word)
        ".ppt" -> setImageResource(R.drawable.powerpoint)
        ".ini" -> setImageResource(R.drawable.ini)
        ".dat" -> setImageResource(R.drawable.dat)
        else -> setImageResource(R.drawable.unknow)
    }
}





