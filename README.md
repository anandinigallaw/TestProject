# TestProject

Build/Run:
We can run the code as Cucumber Feature using the EggTimer.feature file.
Or can run it from build.gradle file as the task is created to trigger the cucumber test.

Platform Limitations:
I've implemented this as of now only with Chrome web browser, this can be further extented and can be validated from IE, Firefox, Safari etc.

All required dependencies have been added in the build.gradle file.

Highlights:
Cucumber BDD Feature File  ---> EggTimer/src/test/resources/EggTimer.feature
Steps Definition Java File ---> EggTimer/src/test/java/EggTimerTest/TestStepsDefinition.java 
Build File                 ---> EggTimer/build.gradle

Improvements:
Test could have been extented to include the hours as well in the input values.
Could have implemented better customized reports.
If the scope of the test is extended, we can have the Page Object models created separately.
For better code reusability we can separate the selenium actions to separate methods recided in a different Java file and these methods can be invoked from Step definition file.
