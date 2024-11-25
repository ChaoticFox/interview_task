How to run test
Tested on:
System: Windows 11 Home, Chrome Version 131.0.6778.86 (Official Build) (64-bit)
IDE: IntelliJ IDEA 2024.3 (Community Edition)

From IDE
Open IDE: Launch IDE
Open the Project:
  Select File > Open... from the menu.
  Navigate to the directory where you cloned the repository and open it.
  Wait for Gradle to Sync: IntelliJ IDEA will automatically detect the Gradle project and start syncing the dependencies. Wait for this process to complete.
Run Tests:
  Navigate to the Project tool window (usually on the left).
  Open the kotlin/org/example/testcases directory to locate your test files.
  Right-click on a test file, test class, or the testcases directory and select Run 'Tests in ...'.
  Alternatively, you can run all tests by navigating to the Gradle tool window (usually on the right), expanding the tasks, and double-clicking test under the verification group.

From command line
(tested in IDEA terminal)
1. Set system variables (can be skipped):
set BROWSER=chrome Note: Only chromedriver is added, potentially can have others
set platform=desktop Note: other options - tablet-h, tablet-v, mobile, but test written only for desktop
2. ./gradlew test
