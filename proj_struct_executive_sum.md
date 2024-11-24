# Project Structure Legend

https://bobbyhadz.com/blog/markdown-display-directory-and-file-structure

```
/src

    Menus/
        /*
         * Login UI and logic
         */
        LogIN.java 
    ui/
        /*
         * Entry point to launch the main application
         */
        MainUI.java 
    models/
        /*
         * Represents user data (username, password, etc.)
         */
        User.java 
    services/
        /*
         * Handles user-related logic (e.g. authentication)
         */
    Main.java
    
```

### LogIN.java and MainUI.java:

LogIN.java:

* Focuses solely on user authentication.
* Prompts for username and password.
* Handles basic input validation.
* Transition point to the main application UI after successful login.

MainUI.java:

Acts as the primary workspace for all cashier functionalities:

* Inventory management.
* Invoice creation and editing.
* Tax and discount application.
* Receipt generation and display.

Encapsulates multiple panels that are logically grouped to match the project's requirements.

