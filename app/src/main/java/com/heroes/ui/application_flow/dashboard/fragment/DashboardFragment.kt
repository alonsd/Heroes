package com.heroes.ui.application_flow.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.heroes.data.viewmodel.HeroesViewModel
import com.heroes.databinding.FragmentDashboardBinding
import com.heroes.ui.application_flow.dashboard.viewholder.HeroesListAdapter
import com.heroes.utils.custom_implementations.OnSearchViewOnlyTextChangedListener
import com.heroes.utils.extensions.setAdapter
import com.heroes.utils.extensions.setVisiblyAsGone
import com.heroes.utils.extensions.setVisiblyAsVisible
import org.koin.android.ext.android.get

class DashboardFragment : Fragment() {

    //Class Variables - UI
    private lateinit var binding: FragmentDashboardBinding

    //Class Variables - Dependency Injection
    private val heroesViewModel = get<HeroesViewModel>()

    //Class Variables - Adapter
    private lateinit var heroesAdapter: HeroesListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()
        handleData()
    }

    private fun init() {
        heroesAdapter = HeroesListAdapter { heroModel ->
            findNavController().navigate(DashboardFragmentDirections.actionMainFragmentToHeroesDetailsFragment(heroModel))
        }
        binding.heroesRecyclerView.setAdapter(requireContext(), heroesAdapter)
        binding.heroesSearchView.setOnQueryTextListener(object : OnSearchViewOnlyTextChangedListener() {

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false
                heroesViewModel.getHeroesByName(newText)
                binding.progressBar.setVisiblyAsVisible()
                return false
            }

        })
    }

    private fun handleData() {

        heroesViewModel.actions.observe(viewLifecycleOwner) { result ->
            when (result) {
                is HeroesViewModel.HeroesViewModelActions.HandleHeroesList -> {
                    heroesAdapter.submitList(result.modelsListResponse.toList())
                    binding.progressBar.visibility = View.GONE
                    binding.progressBar.setVisiblyAsGone()
                }
                is HeroesViewModel.HeroesViewModelActions.ShowGeneralError -> {
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_LONG).show()
                    binding.progressBar.setVisiblyAsGone()
                }

                is HeroesViewModel.HeroesViewModelActions.GetSuggestedList -> {
                    heroesViewModel.getSuggestedHeroesList()
                    binding.progressBar.setVisiblyAsVisible()
                }
                else -> return@observe
            }
        }
    }

}