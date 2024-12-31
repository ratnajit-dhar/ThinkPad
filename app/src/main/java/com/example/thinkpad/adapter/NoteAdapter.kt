package com.example.thinkpad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thinkpad.data.entity.Note
import com.example.thinkpad.databinding.ItemNotesBinding
import java.text.SimpleDateFormat

class NoteAdapter(private var mNotes: List<Note>, private val listener: OnNoteClickListener): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    interface OnNoteClickListener{
        fun onNoteClick(note: Note)
        fun onNoteLongClick(note: Note)
        abstract fun onDeleteClick(note: Note)
    }


    inner class ViewHolder(private val binding: ItemNotesBinding): RecyclerView.ViewHolder(binding.root){

        init{
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val note = mNotes[position]
                        listener.onNoteClick(note)
                    }
                }

                root.setOnLongClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val note = mNotes[position]
                        listener.onNoteLongClick(note)
                    }
                    true
                }

                // Show delete button on long click
                deleteBtn.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = mNotes[position]
                        listener.onDeleteClick(note) // Handle delete
                    }
                }

            }
        }

        fun bind(note: Note){
            binding.apply {
                titleNote.text = note.title
                contentNote.text = note.content
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                dateNote.text = formatter.format(note.date)

                //Hide delete button initially
                binding.deleteBtn.visibility = View.GONE
            }
        }
    }



    fun updateNotes(newNotes: List<Note>) {
        mNotes = newNotes
        notifyDataSetChanged() // Notify adapter of changes
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        with(mNotes[position]){
            holder.bind(this)
        }
    }
    override fun getItemCount(): Int {
        return mNotes.size
    }
}