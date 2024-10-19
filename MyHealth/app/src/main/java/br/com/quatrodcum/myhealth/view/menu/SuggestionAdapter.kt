package br.com.quatrodcum.myhealth.view.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.quatrodcum.myhealth.databinding.ItemMenuMealBinding
import br.com.quatrodcum.myhealth.model.domain.Meal

class SuggestionAdapter :
    ListAdapter<Meal, SuggestionAdapter.SuggestionViewHolder>(SuggestionDiff()) {

    private var _onItemClickListener: (Meal) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuMealBinding.inflate(layoutInflater, parent, false)

        return SuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun onItemClickListener(action: (Meal) -> Unit) {
        this._onItemClickListener = action
    }

    inner class SuggestionViewHolder(
        val binding: ItemMenuMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAddIngredient.setOnClickListener {
                _onItemClickListener.invoke(currentList[adapterPosition])
            }
        }

        fun bind(meal: Meal) {
            binding.btnAddIngredient.text = meal.name
        }
    }

    class SuggestionDiff : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }
    }
}