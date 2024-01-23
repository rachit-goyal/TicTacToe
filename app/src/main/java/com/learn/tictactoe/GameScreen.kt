package com.learn.tictactoe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learn.tictactoe.ui.theme.BlueCustom
import com.learn.tictactoe.ui.theme.GrayBackground

/**
created by Rachit on 1/23/2024.
 */
@Composable
fun GameScreen(viewModel: GameViewModel) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Player 'O' : ${state.playerCircleCount}", fontSize = 16.sp)
            Text(text = "Draw : ${state.drawCount}", fontSize = 16.sp)
            Text(text = "Player 'X' : ${state.playerCrossCount}", fontSize = 16.sp)
        }
        Text(
            text = "Tic Tac Toe", fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = BlueCustom, fontFamily = FontFamily.Cursive
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(10.dp, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground), contentAlignment = Alignment.Center
        ) {
            BoardBase()

            LazyVerticalGrid(modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f), columns = GridCells.Fixed(3), content = {
                viewModel.boardItems.forEach { (index, boardCellValue) ->
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        viewModel.onUserAction(
                                            UserAction.BoardTapped(index)
                                        )
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AnimatedVisibility(
                                    visible = viewModel.boardItems[index] != BoardCellValue.NONE,
                                    enter = scaleIn(tween(1000))
                                ) {
                                    if (boardCellValue == BoardCellValue.CIRCLE) {
                                        Circle()
                                    } else if (boardCellValue == BoardCellValue.CROSS) {
                                        Cross()
                                    }
                                }
                            }
                        }
                }
            })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = state.hasWon,
                    enter = fadeIn(tween(2000))
                ) {
                    DrawVictoryLine(state = state)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Player 'O' Turn", fontSize = 24.sp, fontStyle = FontStyle.Italic)

            Button(
                onClick = { viewModel.onUserAction(UserAction.PlayAgainButtonCLicked) },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevatedButtonElevation()
            ) {
                Text(text = "Play Again")
            }

        }
    }
}

@Composable
fun DrawVictoryLine(state: GameState) {
    when(state.victoryType){
        VictoryType.HORIZONTAL1 -> {
            WinHorizontalLine1()
        }
        VictoryType.HORIZONTAL2 -> {
            WinHorizontalLine2()

        }
        VictoryType.HORIZONTAL3 -> {
            WinHorizontalLine3()

        }
        VictoryType.VERTICAL1 -> {
            WinVerticalLine1()

        }
        VictoryType.VERTICAL2 -> {
            WinVerticalLine2()

        }
        VictoryType.VERTICAL3 -> {
            WinVerticalLine3()

        }
        VictoryType.DIAGONAL1 -> {
            WinDiagonalLine1()
        }
        VictoryType.DIAGONAL2 -> {
            WinDiagonalLine2()

        }
        VictoryType.NONE -> {

        }
    }
}
