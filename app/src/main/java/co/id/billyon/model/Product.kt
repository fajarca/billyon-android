package co.id.billyon.model


data class Product(
        val productId: Long,
        val storeId: Int,
        val imagePath: String,
        val categoryId: Int,
        val name: String,
        val stock: Int,
        val minStock: Int,
        val displayPrice: Long,
        val actualPrice: Long, //harga jual
        val unitPrice: Long, //harga modal
        val dozenPrice: Long,
        val isActive: Int

)