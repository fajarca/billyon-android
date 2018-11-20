package co.id.billyon.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.id.billyon.databinding.RvItemCategoryBinding
import co.id.billyon.databinding.RvItemProductBinding
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.CategoryWithProducts
import co.id.billyon.db.entity.Products
import co.id.billyon.generated.callback.OnClickListener
import co.id.billyon.model.Product
import java.util.ArrayList

class CategoryRecyclerAdapter(private var categories: List<CategoryWithProducts>,
                              private val listener: OnCategoryClickListener)
    : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(categories[position], listener)

    class ViewHolder(private val binding: RvItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryWithProducts, listener: OnCategoryClickListener?) {
            binding.category = category
            listener?.let {
                binding.layoutCategory.setOnClickListener { view ->
                    view?.let {
                        listener.onCategorySelected(category)
                    }
                }
                binding.ivCategory.setOnClickListener { view ->
                    view?.let {
                        listener.onCategorySelected(category)
                    }
                }
            }
            binding.executePendingBindings()
        }


    }

    interface OnCategoryClickListener {
        fun onCategorySelected(category: CategoryWithProducts)
        fun onCategoryImageSelected(category : CategoryWithProducts)
    }

    fun refreshData(categories: List<CategoryWithProducts>) {
        this.categories = categories
        notifyDataSetChanged()
    }

}