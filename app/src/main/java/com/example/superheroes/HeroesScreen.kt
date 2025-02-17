package com.example.superheroes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.data.Hero
import com.example.superheroes.data.HeroSource
import com.example.superheroes.ui.theme.SuperheroesTheme


@Composable
fun HeroCard(hero:Hero,modifier: Modifier=Modifier) {
    Card(modifier.clip(MaterialTheme.shapes.small), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .sizeIn(minWidth = 72.dp)) {
            Column(modifier.weight(1f)) {
                Text(text = stringResource(id = hero.nameRes), style =MaterialTheme.typography.displaySmall)
                Text(text = stringResource(id = hero.descriptionRes), style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier= Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp))) {
                Image(
                    painter = painterResource(id = hero.imageRes),
                    contentDescription = stringResource(
                        id = hero.descriptionRes
                    ),
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth


                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroList(heroes:List<Hero>,modifier: Modifier=Modifier, contentPadding: PaddingValues = PaddingValues(0.dp),) {
    val visibleState = remember{
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(heroes) {index, hero ->
                HeroCard(hero = hero, modifier.padding(horizontal = 16.dp, vertical = 8.dp).animateEnterExit(
                    enter = slideInVertically(
                        animationSpec = spring(
                            stiffness = StiffnessVeryLow,
                            dampingRatio = DampingRatioLowBouncy
                        ),
                        initialOffsetY = { it * (index + 1)})
                )
                )
            }
        }
    }
}

@Preview
@Composable
fun HeroListPreview() {
    SuperheroesTheme {
        HeroList(heroes = HeroSource.heroes)
    }

}

//@Preview
//@Composable
//fun HeroCardPreview() {
//    HeroCard(hero = Hero(nameRes = R.string.hero1, imageRes = R.drawable.android_superhero1, descriptionRes = R.string.description1))
//}