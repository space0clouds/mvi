package clouds.space.mvi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun CrewListScreen(
    modifier: Modifier = Modifier,
    viewModel: CrewListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    if (state.crew.isEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.handleIntent(CrewIntent.GetAllCrew)
        }
    }

    Box(modifier) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            state.exception != null -> {

            }
            else -> {
                CrewList(
                    state.crew,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                )
            }
        }
    }
}

@Composable
fun CrewList(
    crew: List<Crew>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(crew) {
            CrewListItem(it)
        }
    }
}

@Composable
fun CrewListItem(
    crew: Crew,
    modifier: Modifier = Modifier,
) = with(crew) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = image,
            contentDescription = "크루 이미지",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .aspectRatio(3 / 4F),
            contentScale = ContentScale.Crop
        )

        Text(text = name)

        Text(text = agency)
    }
}