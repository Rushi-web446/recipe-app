# 🍽️ TasteTokri - Full Stack Recipe Web Application

**TasteTokri** is a feature-rich full-stack web application designed for food lovers and chefs to **share, explore, and print recipes**. Unlike typical recipe platforms limited to a single chef, TasteTokri enables **multiple chefs** to upload their recipes and allows **users** to interact with them — all within a clean, intuitive UI.

> 🚧 **Deployment in progress** via Docker & Render.

---

## 🔧 Tech Stack

| Layer     | Technology                               |
|-----------|------------------------------------------|
| Backend   | Java, Spring Boot, Spring Security       |
| Frontend  | HTML, Thymeleaf, Bootstrap, JavaScript   |
| Database  | MySQL                                    |
| DevOps    | Docker, Docker Compose, Git & GitHub     |

---

## ✨ Features

### 👥 User Roles
- **Chef**:
  - Upload, edit, and manage recipes
- **User**:
  - Browse, save, and print recipes

### 🍲 Recipe Upload
- Add recipe name, description, image, ingredients, nutrition details, and step-by-step process
- Filter recipes by **Meal Type** (e.g., Breakfast, Dinner) and **Cuisine Type** (e.g., Indian, Italian)
- Print-friendly layout with **scalable servings (1 to N people)**

### 🎨 UI/UX Design
- Fully responsive using **Bootstrap**
- Clean component structure using **Thymeleaf fragments**
- Unified dropdown filter for an organized recipe browsing experience

### 🔐 Authentication & Security
- Role-based login using **Spring Security**
- Password encryption using **BCrypt**

### 🗃️ Database Schema
- Optimized MySQL schema with:
  - **Many-to-Many**: Recipes ↔ Chefs
  - **One-to-Many**: Recipes → Ingredients, Steps, Nutrition
  - Tables: `users`, `chefs`, `recipes`, `ingredients`, `nutrition`, `steps`

---

## 🚀 Local Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Rushi-web446/recipe-app.git
cd recipe-app
