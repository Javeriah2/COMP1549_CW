# COMP1549_CW

## Objective

<p>The aim of this task wa to implememnt a Java-based application to enfore enforce security rules by design, helping prevent common programming errors that lead to security vulnerabilities. Specifically, the application was to ensure that incorrect access to resources is restricted at compile time whereverpossible to avoid program failures at run-time.</p>

<p>Hence, we have developed a small security library and demo application that models a realistic access-controlled system based off of a corporate working environment.</p>

<p>The application includes the following concepts:</p>

1. **Users**: Users with a **Unique ID**, and a **Role** and can access different resources using the above.

2. **Roles**: Each user has a Role. E.g.: Intern, Sys_Admin,etc.

3. 