# Database Audit Logging with Triggers and Views

This project implements an automated auditing system for a MySQL database using triggers and views.

## Features
- **INSERT Trigger**: Automatically logs new records added to the `employees` table.
- **UPDATE Trigger**: Captures both old and new data when a record is changed.
- **Daily Activity Report**: A database view providing summarized activity statistics per day.

---

## How to Run in VS Code

### 1. Install SQL Extension
- Open VS Code.
- Go to the **Extensions** view (Ctrl+Shift+X).
- Search for and install **MySQL** (by WeXuan) or **SQLTools**.

### 2. Connect to XAMPP MySQL
- Ensure your XAMPP Control Panel is open and **MySQL** is started.
- In VS Code, open the **SQL** explorer.
- Create a new connection:
  - **Host**: `localhost`
  - **User**: `root`
  - **Password**: (leave blank by default)
  - **Port**: `3306`

### 3. Execute the Script
- Open `audit_logging.sql`.
- Right-click inside the editor and select **Run All Queries** (or use the play button provided by the extension).

---

## How to Upload to GitHub

### 1. Initialize Git
Open your VS Code terminal (Ctrl+`) and run:
```bash
git init
git add .
git commit -m "Initial commit: SQL Auditing System"
```

### 2. Create Repository on GitHub
- Go to [GitHub](https://github.com/new).
- Name your repository (e.g., `sql-audit-logging`).
- Click **Create repository**.

### 3. Push to GitHub
Copy the commands from GitHub's instruction page, which will look like this:
```bash
git remote add origin https://github.com/YOUR_USERNAME/sql-audit-logging.git
git branch -M main
git push -u origin main
```
