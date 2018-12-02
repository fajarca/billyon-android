package co.id.billyon.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import co.id.billyon.databinding.RvItemProductBinding
import co.id.billyon.db.entity.Products

class ProductsRecyclerAdapter(private var products: List<Products>,
                              private val listener: OnProductClickListener)
    : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position], listener)

    class ViewHolder(private val binding: RvItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Products, listener: OnProductClickListener?) {
            binding.product = product
            listener?.let {
                //binding.root.setOnClickListener({ _ -> listener.onProductSelected(product) })
                binding.contentQuantityPickerButton.layoutAdd.setOnClickListener({ _ -> listener.onAddProductPressed(product) })
                val current = binding.contentQuantityPicker.tvCounter.text.toString().trim().toInt()
                Log.v("TAG", "Current $current")
                binding.contentQuantityPicker.ivAdd.setOnClickListener {
                    val next = current + 1
                    binding.contentQuantityPicker.tvCounter.text = "$next"
                    Log.v("TAG", "Next $next")
                    listener.onAddQtyPressed(current,product)
                }
                //binding.contentQuantityPicker.ivAdd.setOnClickListener({ _ -> listener.onAddQtyPressed(count,product) })
                binding.contentQuantityPicker.ivRemove.setOnClickListener({ _ -> listener.onRemoveQtyPressed(product) })

            }
            binding.executePendingBindings()
        }


    }

    interface OnProductClickListener {
        fun onAddProductPressed(product: Products)
        fun onRemoveProductPressed(product: Products)
        fun onAddQtyPressed(quantity : Int, product: Products)
        fun onRemoveQtyPressed(product: Products)
    }

    fun replaceData(products : List<Products> ) {
        this.products = products
        notifyDataSetChanged()
    }

}