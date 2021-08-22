# Main app folder structure Top level

### Every folder has feature base subfolder
* Di (Dependency Injection)
* domain (App core with need to be implement)
* repository 
* utils
* viewModels
* views

  BaseActivity.java (Abstracted activity where featured has been shared to eatch activity)
  BaseApplication.java (Application start point where dependency has been resolve)
  SessionManager.java (Keep logged in user info on application lifecycle)
  
