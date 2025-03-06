# Interview Copilot (Text-Based)

Interview Copilot is a Spring Boot and React-based application designed to assist users in preparing for technical interviews. It uses **Gemini API** (or GPT-4) to generate structured answers to coding and behavioral questions based on the user's resume.

---

## Features

1. **Resume Upload**: Users can upload their resume (PDF) to provide context for tailored answers.
2. **Question Answering**: Users can ask coding or behavioral questions, and the system generates structured answers.
3. **Contextual Responses**: Answers are tailored based on the user's resume (skills, experience, etc.).
4. **Coding-Specific Answers**: Provides approach, solution code, and complexity analysis for coding questions.

---

## Tech Stack

- **Backend**: Spring Boot (Java)
- **Frontend**: React (JavaScript)
- **LLM**: Gemini API (or GPT-4)
- **Resume Parsing**: Apache PDFBox
- **Caching**: Redis

---

## Prerequisites

1. **Java 17**: Ensure Java 17 is installed.
2. **Node.js**: Install Node.js for the React frontend.
3. **Redis**: Install and run Redis locally for session management.
4. **Gemini API Key**: Obtain an API key from [Gemini](https://ai.google.dev/).

---

## Setup

### Backend (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/interview-copilot.git
   cd interview-copilot
