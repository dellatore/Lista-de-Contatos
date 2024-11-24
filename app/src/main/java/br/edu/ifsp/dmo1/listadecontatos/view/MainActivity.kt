package br.edu.ifsp.dmo1.listadecontatos.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.dmo1.listadecontatos.R
import br.edu.ifsp.dmo1.listadecontatos.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.listadecontatos.databinding.NewContactDialogBinding
import br.edu.ifsp.dmo1.listadecontatos.model.Contact
import br.edu.ifsp.dmo1.listadecontatos.view.MainViewModel
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.View

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private val TAG = "CONTACTS"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListContactAdapter

    // Instância do ViewModel
    private val contactViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.v(TAG, "Executando o onCreate()")

        configClickListener()
        configListView()
        observeViewModel()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedContact = binding.listviewContacts.adapter.getItem(position) as Contact
        val uri = "tel:${selectedContact.phone}"
        val intent = Intent(Intent.ACTION_DIAL).apply { data = Uri.parse(uri) }
        startActivity(intent)
    }

    private fun configClickListener() {
        binding.buttonNewContact.setOnClickListener {
            handleNewContactDialog()
        }
    }

    private fun configListView() {
        adapter = ListContactAdapter(this, arrayListOf())
        binding.listviewContacts.adapter = adapter
        binding.listviewContacts.onItemClickListener = this
    }

    private fun observeViewModel() {
        // Observa mudanças na lista de contatos
        contactViewModel.contacts.observe(this, Observer { contacts ->
            adapter.clear()
            adapter.addAll(contacts)
            adapter.notifyDataSetChanged()
        })
    }

    private fun handleNewContactDialog() {
        val bindingDialog = NewContactDialogBinding.inflate(layoutInflater)
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setView(bindingDialog.root)
            .setTitle(R.string.new_contact)
            .setPositiveButton(
                R.string.btn_dialog_save,
                DialogInterface.OnClickListener { dialog, _ ->
                    Log.v(TAG, "Salvar contato")
                    val newContact = Contact(
                        name = bindingDialog.edittextName.text.toString(),
                        phone = bindingDialog.edittextPhone.text.toString()
                    )
                    contactViewModel.addContact(newContact) // Atualiza pelo ViewModel
                    dialog.dismiss()
                })
            .setNegativeButton(
                R.string.btn_dialog_cancel,
                DialogInterface.OnClickListener { dialog, _ ->
                    Log.v(TAG, "Cancelar novo contato")
                    dialog.cancel()
                })
        builderDialog.create().show()
    }
}
