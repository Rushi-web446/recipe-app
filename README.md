# 🍽️ TasteTokri - Full Stack Recipe Web Application

TasteTokri is a feature-rich full-stack web application designed for food lovers and chefs to **share, explore, and print recipes**. Unlike typical recipe platforms limited to a single chef, TasteTokri enables **multiple chefs** to upload their recipes and allows **users** to interact with them — all in a clean, user-friendly environment.

🚧 *Deployment in progress via Docker & Render.*  


## 🔧 Tech Stack

| Layer        | Technology                             |
|--------------|----------------------------------      |
| Backend      | Java, Spring Boot, Spring Security     |
| Frontend     | HTML, Thymeleaf, Bootstrap, JavaScript |
| Database     | MySQL                                  |
| DevOps       | Docker, Docker Compose, Git & GitHub   |

---

## ✨ Features

- 👨‍🍳 **Two User Roles**:
  - **Chef**: Can upload, edit, and manage recipes
  - **User**: Can view, save, and print recipes

- 🍲 **Recipe Upload**:
  - Name, description, image, ingredients, nutrition, process steps
  - Meal Type & Cuisine filters
  - Print-ready version with dynamic scaling (for 1 to N people)

- 📦 **Advanced UI/UX**:
  - Unified filtering system
  - Responsive design using Bootstrap
  - Clean component structure using Thymeleaf fragments

- 🔐 **Authentication**:
  - Role-based login using Spring Security
  - Password encryption

- 🗂️ **Database Design**:
  - Complex relationships: Many-to-Many and One-to-Many
  - Optimized schema for `users`, `chefs`, `recipes`, `ingredients`, `nutrition`, and `steps`

---

## 🚀 Local Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/Rushi-web446/recipe-app.git
cd recipe-app
