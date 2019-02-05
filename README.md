# Transactions
This is a simple webapp based on the [Play framework](https://www.playframework.com/). It uses a [Postgres database](https://www.postgresql.org/) to store/update data.

It expects a Postgres database to be running on localhost; there is a setup script: `scripts/init-db.sh`- apologies Windows users, I didn't have time to make a .bat version :( This script simply drops its existing db (if it exists), and runs the db scripts to setup some test data; it'll prompt you for the sudo password, and the postgres user password.

It is built in [Scala](https://www.scala-lang.org/download/); you can either import the project into IntelliJ (probably other IDE's as well, but I haven't checked), or if you've setup SBT, compile/run it from the project root using `sbt run`. It runs on port 9000, so you can check it's running by going to `localhost:9000`.

The endpoints can all be found in the `routes.conf` file. I had to amend some of the signatures from what was originally asked for, as there was a configuration clash in Play's routing. The endpoints are:
* GET `transactions/get/{id}`: gets a shallow copy of a transaction by its id, this includes the state and candidate
* GET `transactions/state/{state}`: gets a list of transactions by state
* GET `transactions/states`: gets the counts for each state
* GET `merchants/get/{id}`: gets the merchant by id, and lists their associated locations
* GET `merchants/name/{name}`: gets a list of all merchants with the given name; doesn't include locations
* POST `transactions/match` takes two transaction ids (`firstId` and `secondId`), and matches their transactions together
* POST `transactions/unmatch` takes two transaction ids, and unmatches their transactions

The endpoints can be queiried in a web browser (eg `localhost:9000/transactions/state/matched`) or via curl:
```bash 
curl -X GET "localhost:9000/transactions/states"
```
```bash
curl -X POST "localhost:9000/transactions/match" -H 'Content-Type: application/json' -d'
{
    "firstId" : "156ce21a-5ad3-431e-b00c-f61b38b9c83f",
    "secondId" : "1cadfabf-2b87-46bc-9cf5-f38e44432069"
}
'
```
## Code explanation
I've used a Play project as they're very simple to setup, and include options for ORM out of the box (via Slick). It also comes with built-in filters, which can be used to restrict/check things based on whether a user is logged in, what IP etc etc.

The ORM I've done is pretty basic, as the case classes just have the id's for child tables, instead of the actual models themselves. This is just because it was quicker; proper relational mapping is always recommended.

There's a little bit of error handling around the parameters being passed to the functions (you get a bad request respose), but for the most part it just won't return stuff if it doesn't match. I think you get basic SQL injection prevention for free, but I haven't tested it.

If there are any errors let me know :)
