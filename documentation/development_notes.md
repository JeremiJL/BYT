## Payment
I have decided to represent Emptio - a receiver of payments from campaigns as an instance of a user.



## Lecture
_Operational Database :_ 
Can't not allow for the data to be obsolete.
Has to contain current data.
Often updated.
Not suitable for analyical processes.

Due to that it has to support quick write.

Such databases are reffered to as OLTP - online transaction processing databases

_The contrasting approach is that of Data Warehouse :_
Databases strictly dedicated for analytical purposes, contains both current and historical data, aggregates databases
from large amount of operational databases.

They are referred to OLAP - Online Analytical Processing.

Why do we need OLAP's


_Data warehousing_
A process of assembling and managing data into warehouses.
Fetched from various sourcews.
Purpose - answer business questions.


Characteristics of data stored in warehouses :
- subject oriented
- integrated
- time-variant
- non-volatile (we are at no point removing old records)

_ETL_ :
Process in wjich we :
- extract data from various source
- we clean the data
- we transform data to match the schema of warehouse, from all possible schemas from original data sources
- we load it into warehouse

_Data cleaning_ :

Fix consistency problems when fetcing from multiple sources.
We have to establish rules for automatized data cleaning. 

_Schema patterns_ : 
Most important schema patterns utilized in data warehouses :
Star pattern :
Fact table - subjects of our interest, which we want to put store in our warehouse
Such fact table breaks down into dimension tables, whcih represent an individual aspect of stored data.

advantagaes of star schema pattern :
simple schema - simple queries
siple relations hsips
single dtaa dimension - single table
disadvnatages : 
intorduce redundancy


alternative - snowflae schema :
concept introduces additinal layers of dimensionality - each dimension table can be a fact table iteself

advantage :
allows analysis at various depth of interests,
allows addition of exra layers of dimensions simply


alternative 2 - galaxy schema :

dimension tables might be shared across fact tables,

adv :
saves even more memory, by reducing redundancy,
makes maintainance more complex

Cube metaphor :

OLAP Cube - mind trick for visualising dimensionalioty of data,
there are some operations that are easy to visualise on a cube, that represent some analytical operations that
could be perfomred on multidimensional data

Not all data cubes are based on relational databases, some can be based on multi-dimensional arrays (Array DBMSs).
There are specialize languages for dealign with data cubes.

OLAP Cubes are easier to understand by business people and allow for adhoc analysis.

previous approaches - generating a report at the end of the day based on data from operational databases.

_NewSQL DBS_ :

lack of ACID, new knowladge that have to be learned.
The idea of newSQL is that is affirms the pros and sql and tries to impove on what is already there, while
nosql disregards all that sql was about

example google spanner - have taken a simmilar path to that of big table, implemented transactions using atomic clocks
why this trends take place, because of convencinece of what sql promises us to deliver.

CockroachDB - implemented in Go,
Business Source License - source is available, forking is limited, SaaS is paid. You can only use it for free if
you have a local branch (on premise) and maintain it yourself.

Compatible with PostgreSQL on driver level.
optimized for transactions - the most common reason for people to chose SQL anyway
distibuted
consistent over available (as constistency is part of ACID - SQLs promise while availability is not)
geo-partitioned - while setting up a table you configure its use cases, it levereges the existance of heuristics to
optimize operations, it allows you to differentiate you how data is managed based on the legislative neighbourhood

requires lot of reources - due to it conforming to the distribution approach

# Lecture 2

The challanges of introducing a new system.


