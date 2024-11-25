package br.edu.ifsp.dmo1.listadecontatos.model

import java.util.LinkedList


object ContactDao {
    private val dataset = LinkedList<Contact>()

    // Insere diretamente na lista principal
    fun insert(contact: Contact) {
        dataset.add(contact)
    }

    // Retorna diretamente a lista para evitar inconsistências
    fun findAll(): List<Contact> {
        return dataset
    }
}