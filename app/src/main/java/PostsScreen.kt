import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face

import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PostsScreen(viewModel: PostsViewModel) {
    val posts by viewModel.posts.collectAsState()
    val comments by viewModel.comments.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(posts) { post ->
            PostItem(post = post, comments = comments[post.id]) {
                viewModel.fetchComments(post.id)
            }
        }
    }
}

@Composable
fun PostItem(post: Post, comments: List<Comment>?, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.body, style = MaterialTheme.typography.bodyMedium)

            comments?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Comments:", style = MaterialTheme.typography.titleMedium)
                it.forEach { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Row {
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = comment.body,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Preview
@Composable
fun PostItemPreview() {
    PostItem(
        post = Post(1, 1, "Sample Post", "This is a sample post body"),
        comments = listOf(Comment(1, 1, "Sample Comment", "sample@example.com", "This is a sample comment")),
        onClick = {}
    )
}
