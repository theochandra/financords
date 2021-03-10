package com.financords.android.search

import android.content.res.AssetManager
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

class SearchViewModel(
    private val searchApi: SearchApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    companion object {
        const val SEARCH_DELAY_MS = 500L
        const val MIN_QUERY_LENGTH = 3
    }

    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    /**
     * use flow to handle search on typed
     * article: https://www.hellsoft.se/instant-search-with-kotlin-coroutines/
     * example: https://github.com/ErikHellman/InstantSearchDemo.git
     */
    @FlowPreview
    @ExperimentalCoroutinesApi
    @VisibleForTesting
    internal val internalSearchResult = queryChannel
        .asFlow() // convert channel into a Flow
        .debounce(SEARCH_DELAY_MS) // ensures we can throttle the call to our search API
                                // and only send a query after 500ms have passed
                                // without a new string being emitted.
        .mapLatest { query ->   // this operator will perform the actual call to search API
                                // and return the search result
            /**
             * The great thing with flow is that when a new event is emitted,
             * anything below that hasn't been collected yet will be cancelled.
             *
             * In order to get better understanding of how this works, you can wrap the code
             * in a try / catch statement which catches CancellationException and prints
             * something to logcat.
             * Just make sure you rethrow the CancellationException or the coroutine won't
             * be shut down properly.
             */
            try {
                if (query.length >= MIN_QUERY_LENGTH) {
                    val searchResult = withContext(ioDispatcher) {
                        searchApi.performSearch(query)
                    }
                    println("Search result: ${searchResult.size} hits")

                    if (searchResult.isNotEmpty()) {
                        ValidResult(searchResult)
                    } else {
                        EmptyResult
                    }
                } else {
                    EmptyQuery
                }
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    println("Search was cancelled!")
                    throw e
                } else {
                    ErrorResult(e)
                }
            }
        }
        .catch { emit(TerminalError) }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchResult = internalSearchResult.asLiveData()

    class Factory(private val assets: AssetManager, private val dispatcher: CoroutineDispatcher) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(SearchRepository(assets), dispatcher) as T
        }
    }
}

sealed class SearchResult
class ValidResult(val result: List<String>) : SearchResult()
object EmptyResult : SearchResult()
object EmptyQuery : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()
object TerminalError : SearchResult()