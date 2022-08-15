package com.example.flowerygh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.flowerygh.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemDialog(private val itemPos: Int = -1) : BottomSheetDialogFragment() {
    private lateinit var binding: ItemDialogLayoutBinding

    private val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (itemPos >= 0) {
            binding.editTextName.setText(viewModel.getItem(itemPos).name)

        }

        binding.buttonOK.setOnClickListener {
            val item = Item(
                binding.editTextName.text.toString()

            )
            if (itemPos < 0)
                viewModel.addItem(item)
            else
                viewModel.updateItem(item, itemPos)
            dismiss()
        }


    }
}