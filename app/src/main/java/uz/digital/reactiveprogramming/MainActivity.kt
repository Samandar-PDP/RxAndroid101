package uz.digital.reactiveprogramming

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import uz.digital.reactiveprogramming.adapter.BookAdapter
import uz.digital.reactiveprogramming.databinding.ActivityMainBinding
import uz.digital.reactiveprogramming.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel
    private val bookAdapter by lazy { BookAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRv()

        binding.inputBookName.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(700L)
                binding.progressBar.isVisible = true
                viewModel.searchBook(it.toString())
            }
        }
        viewModel.liveData.observe(this) {
            binding.progressBar.isVisible = false
            bookAdapter.submitList(it.items)
        }
    }

    private fun setupRv() {
        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.HORIZONTAL))
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}