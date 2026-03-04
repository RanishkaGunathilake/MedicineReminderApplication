## MEDIPLUS is an Android based medicine reminder application developed using Java and SQLite. This application allows users to securely register, log in and manage their daily medicine schedules with reminder days, times and also special notes.

<br/>

## Overview of the Application

MEDIPLUS is designed to help users organize and manage their medication schedules efficiently. Each user has a secure account and all medicine records are stored separately per user.
The system includes authentication, full CRUD operations for medicines, scheduling with reminder days and times and strong validation rules to ensure correct data entry.

<br/>

## Features

### User Authentication
- Secure user registration
- Password hashing using SHA-256
- Login validation
- Unique username enforcement
- Input validation for username and password strength

### Medicine Management
- Add new medicines
- Edit existing medicines
- Delete medicines
- Store dosage details
- Add optional notes
- View all medicines in a RecyclerView list

### Scheduling System
- Select start date
- Optional entry for end date
- End date validation (end date cannot be added before start date)
- Select reminder days (Monday to Sunday)
- Add multiple reminder times
- At least one reminder day required to save the medicine

### Multi-User Support
- Each medicine record is linked to a specific user
- Users can only view and manage their own medicines

<br/>

## Technologies Used

- **Language :** Java
- **IDE :** Android Studio 
- **Platform :** Android  
- **Database :** SQLite  
- **Security :** SHA-256 Password Hashing  

<br/>

## Project Structure

com.example.medicinereminderapplication<br/>
│ <br/>
├── MainActivity.java<br/>
├── LoginActivity.java<br/>
├── RegisterActivity.java<br/>
├── HomeActivity.java<br/>
├── AddEditMedicineActivity.java<br/>
│ <br/>
├── DatabaseHelper.java<br/>
├── Medicine.java<br/>
├── MedicineAdapter.java<br/>
├── PasswordUtils.java<br/>
│ <br/>
└── res/<br/>
├── layout/<br/>
│ ├── activity_main.xml<br/>
│ ├── activity_login.xml<br/>
│ ├── activity_register.xml<br/>
│ ├── activity_home.xml<br/>
│ ├── activity_add_edit_medicine.xml<br/>
│ └── item_medicine.xml<br/>
├── drawable/<br/>
└── values/<br/>

<br/>

##  Database Design

### Users Table

| Column   | Type    | Description                        |
|----------|---------|------------------------------------|
| id       | INTEGER | Primary Key (Auto Increment)       |
| username | TEXT    | Unique Username                    |
| password | TEXT    | SHA-256 Hashed Password            |

### Medicines Table

| Column     | Type    | Description                        |
|------------|---------|------------------------------------|
| id         | INTEGER | Primary Key (Auto Increment)       |
| name       | TEXT    | Medicine name                      |
| dosage     | TEXT    | Dosage details                     |
| start_date | TEXT    | Start date                         |
| end_date   | TEXT    | Optional end date                  |
| times      | TEXT    | Comma separated reminder times     |
| days       | TEXT    | Comma separated reminder days      |
| notes      | TEXT    | Additional notes                   |
| username   | TEXT    | Owner of the medicine              |

<br/>

##  Validation Rules

### Username
- Letters and numbers only

### Password
- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one number
- At least one special character

### Medicine Entry
- Medicine name is required
- Start date is required
- At least one reminder day must be selected
- End date must be after start date

<br/>

## How to Run the Project

1. Clone the repository:
git clone https://github.com/RanishkaGunathilake/MedicineReminderApplication.git

2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Run the project on an emulator or Android device.

<br/>

##  Password Security

Passwords are hashed using SHA-256 before being stored in the database. This ensures that plain text passwords are never saved.

<br/>

## 
