package com.example.exerciseslot4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseslot4.ui.theme.ExerciseSlot4Theme
import kotlinx.coroutines.delay

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)

class QuizActivity : ComponentActivity() {
    
    companion object {
        const val EXTRA_PLAYER_NAME = "player_name"
        private const val TAG = "QuizActivity"
        private const val KEY_CURRENT_QUESTION = "current_question"
        private const val KEY_SCORE = "score"
        private const val KEY_SELECTED_ANSWER = "selected_answer"
    }
    
    private val questions = listOf(
        Question(
            "What is the capital of France?",
            listOf("London", "Berlin", "Paris", "Madrid"),
            2
        ),
        Question(
            "Which planet is known as the Red Planet?",
            listOf("Venus", "Mars", "Jupiter", "Saturn"),
            1
        ),
        Question(
            "What is 2 + 2?",
            listOf("3", "4", "5", "6"),
            1
        ),
        Question(
            "Who painted the Mona Lisa?",
            listOf("Van Gogh", "Picasso", "Da Vinci", "Michelangelo"),
            2
        ),
        Question(
            "What is the largest mammal?",
            listOf("Elephant", "Blue Whale", "Giraffe", "Hippo"),
            1
        )
    )
    
    private var currentQuestionIndex by mutableIntStateOf(0)
    private var score by mutableIntStateOf(0)
    private var selectedAnswer by mutableIntStateOf(-1)
    private var playerName by mutableStateOf("")
    private var showNextButton by mutableStateOf(false)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        
        // Get player name from intent
        playerName = intent.getStringExtra(EXTRA_PLAYER_NAME) ?: "Player"
        
        // Restore state if available
        savedInstanceState?.let {
            currentQuestionIndex = it.getInt(KEY_CURRENT_QUESTION, 0)
            score = it.getInt(KEY_SCORE, 0)
            selectedAnswer = it.getInt(KEY_SELECTED_ANSWER, -1)
            showNextButton = selectedAnswer != -1
        }
        
        enableEdgeToEdge()
        setContent {
            ExerciseSlot4Theme {
                QuizScreen(
                    playerName = playerName,
                    currentQuestion = questions[currentQuestionIndex],
                    currentQuestionIndex = currentQuestionIndex,
                    totalQuestions = questions.size,
                    score = score,
                    selectedAnswer = selectedAnswer,
                    showNextButton = showNextButton,
                    onAnswerSelected = { answerIndex ->
                        selectedAnswer = answerIndex
                        showNextButton = true
                        
                        if (answerIndex == questions[currentQuestionIndex].correctAnswer) {
                            score++
                            Toast.makeText(this@QuizActivity, "Correct!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@QuizActivity, "Incorrect!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onNextQuestion = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedAnswer = -1
                            showNextButton = false
                        } else {
                            // Quiz finished
                            Toast.makeText(this@QuizActivity, "Quiz completed! Final score: $score/${questions.size}", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                )
            }
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }
    
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState called")
        outState.putInt(KEY_CURRENT_QUESTION, currentQuestionIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_SELECTED_ANSWER, selectedAnswer)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    playerName: String,
    currentQuestion: Question,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    score: Int,
    selectedAnswer: Int,
    showNextButton: Boolean,
    onAnswerSelected: (Int) -> Unit,
    onNextQuestion: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Welcome message
            Text(
                text = "Welcome, $playerName!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Score display
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Score: $score/$totalQuestions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            
            // Question number
            Text(
                text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Question text
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )
            }
            
            // Answer options
            currentQuestion.options.forEachIndexed { index, option ->
                val isSelected = selectedAnswer == index
                val isCorrect = index == currentQuestion.correctAnswer
                val isIncorrect = isSelected && !isCorrect
                
                val buttonColors = when {
                    isCorrect && selectedAnswer != -1 -> ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                    isIncorrect -> ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                    isSelected -> ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                    else -> ButtonDefaults.buttonColors()
                }
                
                Button(
                    onClick = { 
                        if (selectedAnswer == -1) {
                            onAnswerSelected(index)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = buttonColors,
                    enabled = selectedAnswer == -1
                ) {
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Next Question button
            if (showNextButton) {
                Button(
                    onClick = onNextQuestion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = if (currentQuestionIndex < totalQuestions - 1) "Next Question" else "Finish Quiz",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}