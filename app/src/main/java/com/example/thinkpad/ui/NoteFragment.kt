package com.example.thinkpad.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thinkpad.R
import com.example.thinkpad.adapter.NoteAdapter
import com.example.thinkpad.data.entity.Note
import com.example.thinkpad.databinding.FragmentNotesBinding
import com.example.thinkpad.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController


@AndroidEntryPoint
class NoteFragment: Fragment(R.layout.fragment_notes), NoteAdapter.OnNoteClickListener {
    val viewModel by viewModels<NoteViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = FragmentNotesBinding.bind(requireView())

        binding.apply{
            recyclerViewNotes.layoutManager = GridLayoutManager(context, 2)
            recyclerViewNotes.setHasFixedSize(true)

            addBtn.setOnClickListener{
                val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(null)
                findNavController().navigate(action)
            }

            viewLifecycleOwner.lifecycleScope.launch{
                viewModel.notes.collect{notes ->
                    val adapter = NoteAdapter(notes, this@NoteFragment)
                    recyclerViewNotes.adapter = adapter
                }
            }


            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notesEvent.collect{event ->
                    if(event is NoteViewModel.NotesEvent.ShowUndoSnackBar){
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).setAction("UNDO"){
                            viewModel.insertNote(event.note)
                        }.show()
                    }
                }
            }
        }

    }

    override fun onNoteClick(note: Note) {
        val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onNoteLongClick(note: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteNote(note)
                Snackbar.make(requireView(), "Note deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }


    override fun onDeleteClick(note: Note) {
        // Confirm before deleting, if needed, and then delete
        viewModel.deleteNote(note)
    }
}
