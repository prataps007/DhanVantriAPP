package com.example.plantimagerecognitionusingml

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig

object Model {

     val model = GenerativeModel(
        // 1
        modelName = "gemini-1.5-flash-latest",
        // 2
        apiKey = "AIzaSyCZ5As-DGnHLCL09aRK5MjQhIwTC93Sm8M",
        // 3
        generationConfig = generationConfig {
            temperature = 0.7f
        },
        // 4
        systemInstruction = content {
            text("You are a knowledgeable and friendly bot specialized in medicinal plants. Respond concisely and helpfully. " +
                    "You will be mostly asked queries related to medicinal plants and their uses, their properties" +
                    "applications, their geographical locations and other relevant information. " + " Give correct and precise answer of user's queries to the best of your ability." +
                    "Try to keep the conversation insightful and user satisfactory")
        }
    )

}