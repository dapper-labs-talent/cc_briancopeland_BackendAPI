## DB Setup
This service required a postgresql instance to be running locally. It should be running on the port 5432 (default), and have a database called "mydb". Additionally, there should be a database user named "brian" with no password.

If you wish to have a different database name or want to use a different postgres username/password, these settings can be configured in the "demo/src/main/resources/application.properties". To use a different database name or port, change line 1 `spring.datasource.url` to reflect your desired settings. To configure the username/password to use, change lines 4 and 5, `spring.datasource.username` and `spring.datasource.password`.

To run the service, navigate to /demo and run the command `./gradlew bootRun`