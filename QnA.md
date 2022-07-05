How long did this assignment take?
> Approximately 5 hours.

What was the hardest part?
> Setting up spring, and getting it to consume/produce the correct request/response formats.

Did you learn anything new?
> I learned a decent bit about spring, and found it to be convenient to solve a lot of different hard problems with it.  I also learned a bit about JWT--though would have learned more had I had more time to implement some additional feature there.

Is there anything you would have liked to implement but didn't have the time to?
> 1. Better handling/responses to invalid, or illegal requests.

2. More testing--specifically automated integration and database testing.

3. The JWT algorithm's secret should be obtained via some kind of pubsub to enable key rotation, and to enable multiple instances of this service running concurrently.

4. All the security holes mentioned below.

What are the security holes (if any) in your system? If there are any, how would you fix them?
> 1. Unsalted password hashes: Currently I am storing the hash of users passwords in the db.  If the db is compromised, these can be vulnerable if the attacker has a hash lookup. These passwords should be stored/hashed with a salt.

2. JWT reuse: There is no timeout on the java web token, which means that a malicious third party could have access to the system as long as they wanted if they aquired a valid JWT. I should check to make sure JWT's are not above a certain age.

3. DB security: The PSQL database does not have a password. I considered adding a password to the db and storing that in a configuration file here, but that is also not good practice. There should at least be a pubsub with the db password, but ideally something password-less for this service to connect to the db.

Do you feel that your skills were well tested?
> I think this was a good test of my basic software engineering skills. I think the problem was too simple to test a variety of other skills, such as system design.