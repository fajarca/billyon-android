package co.id.billyon.staff

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.id.billyon.databinding.RvItemProductBinding
import co.id.billyon.staff.uimodels.Product
import java.util.ArrayList

class ProductsRecyclerAdapter(private var products: ArrayList<Product>,
                              private val listener: OnProductClickListener)
    : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductsRecyclerAdapter.ViewHolder, position: Int) = holder.bind(products[position], listener)

    class ViewHolder(private val binding: RvItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, listener: OnProductClickListener?) {
            binding.product = product
            listener?.let {
                binding.root.setOnClickListener({ _ -> listener.onProductSelected(layoutPosition) })
            }
            binding.executePendingBindings()
        }


    }

    interface OnProductClickListener {
        fun onProductSelected(position: Int)
    }

    fun replaceData(products : ArrayList<Product> ) {
        this.products = products
        notifyDataSetChanged()
    }

}