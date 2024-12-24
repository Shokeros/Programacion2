package com.example.primera.modelo

class CuentaMesa (
    val _item: MutableList<ItemMesa> = mutableListOf<ItemMesa>(),
    val mesa: Int
) {
    fun agregarItem(cantidad: Int, itemMenu: ItemMenu) {1
        val nuevoItem = ItemMesa(cantidad, itemMenu)
        _item.add(nuevoItem)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        _item.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        var totalSinPropina = 0
        for (item in _item) {
            totalSinPropina += item.calcularSubtotal()
        }
        return totalSinPropina
    }

    fun calcularPropina() : Int {
        var propina = 0
        for (item in _item) {
            propina += (item.calcularSubtotal() * 0.10).toInt()
        }
        return propina
    }

    fun calcularTotalConPropina() : Int {
        var total = 0
        for (item in _item) {
            total += (item.calcularSubtotal() * 1.10).toInt()
        }
        return total
    }
}
