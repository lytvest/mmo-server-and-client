package ru.lytvest.aeon

typealias Method = ((Int) -> Unit)?

data class Item(val cost: Int, val add: Int, val method: Method = null)