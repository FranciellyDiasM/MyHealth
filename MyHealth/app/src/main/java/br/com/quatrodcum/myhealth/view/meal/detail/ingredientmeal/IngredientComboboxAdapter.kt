package br.com.quatrodcum.myhealth.view.meal.detail.ingredientmeal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.quatrodcum.myhealth.databinding.SpinnerItemBinding
import br.com.quatrodcum.myhealth.model.domain.Ingredient

class IngredientComboboxAdapter (
    private val ingredients: List<Ingredient>
) : BaseAdapter() {

    override fun getCount(): Int {
        return ingredients.size
    }

    override fun getItem(position: Int): Ingredient {
        return ingredients[position]
    }

    override fun getItemId(position: Int): Long {
        return ingredients[position].id?.toLong() ?: -1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder: ViewHolder

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent!!.context)
            val binding = SpinnerItemBinding.inflate(layoutInflater, parent, false)
            viewHolder = ViewHolder(binding)
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.bind(ingredients[position])

        return viewHolder.binding.root
    }

    fun getSelectedIngredient(position: Int): Ingredient {
        return getItem(position)
    }

    private class ViewHolder(val binding: SpinnerItemBinding) {

        init {
            binding.root.tag = this
        }

        fun bind(ingredient: Ingredient) {
            binding.textSpinnerItem.text = ingredient.name
        }

    }
}