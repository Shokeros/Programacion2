package com.example.primera.modelo

class ItemMesa(
    private val cantidad: Int,
    private val itemMenu: ItemMenu
) {
    fun calcularSubtotal(): Int {
        val subtotal = itemMenu.precio.toInt() * cantidad
        return subtotal
    }
}