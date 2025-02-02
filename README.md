# Aptoide Python API Script

Simple Python script to interact with Aptoide API.

## Requirements

- Python 3.x
- `requests` library

## How to Run

1. Install requirements:

pip install requests

2. Run the script:

python aptoide_python.py

The script will search for apps, show app details, and download the APK file.

# Aptoide Android Challenge

## Problems Identified

1. @PrimaryKey annotation missing in User.kt file
2. @Volatile annotation missing in INSTANCE initialization. We need the annotation to make the INSTANCE visible across Threads.
3. initDatabase() was performing operations on the main Thread which Room Databases don't allow by default and it wasn't returning anything but was modifying a nullable property. Changed function name to getDatabase()
4. Database was being populated in the same operation as the initialization of it. I moved it to a new function populateRoomDatabase()
5. populateRoomDatabase() is a suspend function so we can call it from a Courotine.
6. Missing Internet Permissions
7. In the UserDao.kt file we have to change the expected type of the object for the getAllUsers() query to LiveData<List<User>> to ensure its consistent with the type being passed in the MainActivityViewModel.
8. In MainActivityViewModel we need to modify the loadUserData() to use the CouroutineScope just like in the loadPostsData()

### After this changes the app runned perfectly
