import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostsViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _comments = MutableStateFlow<Map<Int, List<Comment>>>(emptyMap())
    val comments: StateFlow<Map<Int, List<Comment>>> = _comments

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val postList = RetrofitInstance.api.getPosts()
            _posts.value = postList
        }
    }

    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            val commentList = RetrofitInstance.api.getComments(postId)
            _comments.value = _comments.value + (postId to commentList)
        }
    }
}