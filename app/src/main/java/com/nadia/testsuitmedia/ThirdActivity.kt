package com.nadia.testsuitmedia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nadia.testsuitmedia.databinding.ActivityThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter
    private var currentPage = 1
    private var totalPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(mutableListOf()) { user ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("SELECTED_USER_NAME", "${user.first_name} ${user.last_name}")
            startActivity(intent)
        }

        binding.recUser.layoutManager = LinearLayoutManager(this)
        binding.recUser.adapter = adapter

        binding.swipe.setOnRefreshListener {
            adapter.clear()
            currentPage = 1
            loadUsers()
        }

        binding.recUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && currentPage < totalPage) {
                    currentPage++
                    loadUsers()
                }
            }
        })

        loadUsers()
    }

    private fun loadUsers() {
        binding.swipe.isRefreshing = true

        with(ApiConfig) {
            apiService.getUsers(currentPage, 6).enqueue(object : Callback<Responses> {
                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    binding.swipe.isRefreshing = false
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val users: List<User> = (it.data ?: emptyList()) as List<User>
                            totalPage = it.totalPages ?: 1

                            if (users.isEmpty()) {
                                showEmptyState(true)
                            } else {
                                showEmptyState(false)
                                adapter.addUsers(users)
                            }
                        }
                    } else {
                        Log.e("API Error", "Response not successful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    binding.swipe.isRefreshing = false
                    Log.e("API Failure", t.message ?: "Unknown error")
                }
            })
        }
    }

    private fun showEmptyState(show: Boolean) {
        binding.emptyStateText.visibility = if (show) TextView.VISIBLE else TextView.GONE
    }
}

