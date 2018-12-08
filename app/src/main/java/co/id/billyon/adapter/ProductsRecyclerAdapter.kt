package co.id.billyon.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.id.billyon.databinding.RvItemProductBinding
import co.id.billyon.db.entity.join.ProductsAndCartProduct

class ProductsRecyclerAdapter(private var products: List<ProductsAndCartProduct>,
                              private val listener: OnProductClickListener)
    : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
        holder.bind(products[position], position, listener)
    }

    class ViewHolder(private val binding: RvItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductsAndCartProduct, position: Int, listener: OnProductClickListener?) {
            binding.product = product

            if (product.quantity > 0) {
                hideAddToCart()
                showQuantityPicker(product.quantity)
            } else {
                showAddToCart()
                hideQuantityPicker()
            }

            var current = binding.contentQuantityPicker.etCounter.text.toString().trim().toInt()

            listener?.let {
                binding.contentQuantityPickerButton.layoutAdd.setOnClickListener {
                    listener.onAddProductPressed(product, position)
                    binding.contentQuantityPickerButton.layoutAdd.visibility = View.GONE
                    binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.VISIBLE
                }
                binding.contentQuantityPicker.ivAdd.setOnClickListener {
                    //Make sure the selected quantity never exceeds the product stock
                    if (current < product.stock) {
                        current += 1
                        binding.contentQuantityPicker.etCounter.setText(current.toString())
                        binding.contentQuantityPicker.etCounter.setSelection(current.toString().length)
                        listener.onAddQtyPressed(current, product)
                    }

                }
                binding.contentQuantityPicker.etCounter.setOnClickListener {
                    listener.onCustomQtyPressed(current, product)
                }
                binding.contentQuantityPicker.ivRemove.setOnClickListener {
                    current -= 1

                    if (current == 0 || current < 0) {
                        listener.onRemoveProductPressed(product)
                        binding.contentQuantityPickerButton.layoutAdd.visibility = View.VISIBLE
                        binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.GONE
                    }

                    binding.contentQuantityPicker.etCounter.setText(current.toString())
                    binding.contentQuantityPicker.etCounter.setSelection(current.toString().length)

                    listener.onRemoveQtyPressed(current, product)
                }
            }


            binding.executePendingBindings()

        }


        fun showAddToCart() {
            binding.contentQuantityPickerButton.layoutAdd.visibility = View.VISIBLE
        }

        fun hideAddToCart() {
            binding.contentQuantityPickerButton.layoutAdd.visibility = View.GONE
        }

        fun showQuantityPicker(quantity: Int) {
            binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.VISIBLE
            binding.contentQuantityPicker.etCounter.setText(quantity.toString())
            binding.contentQuantityPicker.etCounter.setSelection(quantity.toString().length)
        }

        fun hideQuantityPicker() {
            binding.contentQuantityPicker.layoutQuantityPicker.visibility = View.GONE
        }
    }

    interface OnProductClickListener {
        fun onAddProductPressed(product: ProductsAndCartProduct, position: Int)
        fun onRemoveProductPressed(product: ProductsAndCartProduct)
        fun onAddQtyPressed(quantity: Int, product: ProductsAndCartProduct)
        fun onRemoveQtyPressed(quantity: Int, product: ProductsAndCartProduct)
        fun onCustomQtyPressed(quantity: Int, product: ProductsAndCartProduct)
    }

    fun replaceData(products: List<ProductsAndCartProduct>) {
        this.products = products
        notifyDataSetChanged()
    }


}