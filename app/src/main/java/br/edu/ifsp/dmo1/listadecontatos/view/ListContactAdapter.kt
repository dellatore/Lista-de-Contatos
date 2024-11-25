package br.edu.ifsp.dmo1.listadecontatos.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.edu.ifsp.dmo1.listadecontatos.databinding.ContactItemLayoutBinding
import br.edu.ifsp.dmo1.listadecontatos.R
import br.edu.ifsp.dmo1.listadecontatos.model.Contact

class ListContactAdapter(context: Context, data: List<Contact>) :
    ArrayAdapter<Contact>(context, R.layout.contact_item_layout, data.sortedBy { it.name }) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ContactItemLayoutBinding

        // Inflar o layout e associar o binding
        if (convertView == null) {
            binding = ContactItemLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ContactItemLayoutBinding
        }

        // Obter o contato atual
        val currentContact = getItem(position)

        // Preencher os dados no layout
        binding.textviewItemName.text = currentContact?.name
        binding.textviewItemPhonenumber.text = currentContact?.phone

        return binding.root
    }

    // Método adicional para atualizar a lista e manter ordenada
    fun updateData(newData: List<Contact>) {
        val sortedData = newData.sortedBy { it.name }
        clear()
        addAll(sortedData)
        notifyDataSetChanged()
    }
}
