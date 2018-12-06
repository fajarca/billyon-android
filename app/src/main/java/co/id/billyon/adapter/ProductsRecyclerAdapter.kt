package co.id.billyon.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position], position, listener)

    class ViewHolder(private val binding: RvItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Products, position: Int, listener: OnProductClickListener?) {
            binding.product = product
            listener?.let {
                binding.contentQuantityPickerButton.layoutAdd.setOnClickListener(
                        { _ ->
                            listener.onAddProductPressed(product, position)
                            binding.contentQuantityPickerButton.layoutAdd.visibility = View.GONE
                            binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.VISIBLE
                        }
                )
                var current = binding.contentQuantityPicker.tvCounter.text.toString().trim().toInt()
                binding.contentQuantityPicker.ivAdd.setOnClickListener {
                    current += 1
                    binding.contentQuantityPicker.tvCounter.text = "$current"
                    listener.onAddQtyPressed(current, product)
                }
                binding.contentQuantityPicker.ivRemove.setOnClickListener {
                    current += -1
                    binding.contentQuantityPicker.tvCounter.text = "$current"

                    if (current == 0) {
                        binding.contentQuantityPickerButton.layoutAdd.visibility = View.VISIBLE
                        binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.GONE
                    }

                    listener.onRemoveQtyPressed(current, product)
                }
            }
            binding.executePendingBindings()
        }


    }

    interface OnProductClickListener {
        fun onAddProductPressed(product: Products, position: Int)
        fun onRemoveProductPressed(product: Products)
        fun onAddQtyPressed(quantity: Int, product: Products)
        fun onRemoveQtyPressed(quantity: Int, product: Products)
    }

    fun replaceData(products: List<Products>) {
        this.products = products
        notifyDataSetChanged()
    }


}