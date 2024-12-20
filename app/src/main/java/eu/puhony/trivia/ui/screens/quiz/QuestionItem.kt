package eu.puhony.trivia.ui.screens.quiz

import eu.puhony.trivia.api.Question

data class QuestionItem (
    val question: Question,
    val wasRight: Boolean
)
