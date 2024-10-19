package br.com.quatrodcum.myhealth.view.meal.detail.ingredientmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.quatrodcum.myhealth.databinding.DialogIngredientMealBinding
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import br.com.quatrodcum.myhealth.view.FullDialogFragment
import br.com.quatrodcum.myhealth.view.meal.detail.unitOfMeasurementmeal.UnitOfMeasurementComboboxAdapter

class IngredientMealDialogFragment private constructor(
    private val ingredients: List<Ingredient>,
    private val unitOfMeasurements: List<UnitOfMeasurement>
) : FullDialogFragment() {

    private lateinit var binding: DialogIngredientMealBinding
    private var _onConfirmListener: (IngredientMeal) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogIngredientMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        val ingredientAdapter = IngredientComboboxAdapter(ingredients)
        val unitOfMeasurementAdapter = UnitOfMeasurementComboboxAdapter(unitOfMeasurements)

        binding.cbxIngredient.adapter = ingredientAdapter
        binding.cbxUnitOfMeasurement.adapter = unitOfMeasurementAdapter

        binding.btnAdd.setOnClickListener {
            val indexIngredient = binding.cbxIngredient.selectedItemPosition
            val indexUnitOfMeasurement = binding.cbxUnitOfMeasurement.selectedItemPosition

            val ingredient = ingredients[indexIngredient]
            val unitOfMeasurement = unitOfMeasurements[indexUnitOfMeasurement]
            val quantity = binding.edtQuantity.text.toString().toIntOrNull() ?: 0


            if (quantity > 0) {
                val ingredientMeal = IngredientMeal(ingredient, unitOfMeasurement, quantity)
                _onConfirmListener.invoke(ingredientMeal)
            }

            dismiss()
        }
    }

    fun setOnConfirmListener(action: (IngredientMeal) -> Unit) {
        this._onConfirmListener = action
    }

    companion object {
        fun newInstance(
            ingredients: List<Ingredient>,
            unitOfMeasurements: List<UnitOfMeasurement>
        ): IngredientMealDialogFragment {
            return IngredientMealDialogFragment(ingredients, unitOfMeasurements)
        }
    }
}