package br.edu.ifsp.dmo1.listadecontatos.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.listadecontatos.model.Contact
import br.edu.ifsp.dmo1.listadecontatos.model.ContactDao

class MainViewModel : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        _contacts.value = ContactDao.findAll()
    }

    fun addContact(contact: Contact) {
        ContactDao.insert(contact)
        loadContacts()
    }
}
