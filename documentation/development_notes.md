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


# Lecture 3

Software acceptence - a legal, formal process of handing in the software to the buyer.
One of the steps is softwere validation. A conctract on which the purchase is based should include the specification
of the softwere and guidence on how to verify complience based on this specification.

In agile methodology - the softwere is developed in short cycles - called sprints (taking a couple of weeks), after each
sprint the team delivers an 'increment' of the softwere, each sprint is concluded with a working deployment of 
changes made during the sprint. In this context, the specification is being agreed upon before each sprint.
This aproach means that the contract is validated and re-evaluated periodically. Consequentially there will not be
one session of user acceptence tests performed, rather they will be performed on basis of sprint.

Non-functional requirements - the technical - non-business side of the application (like secuirty and performence).
There arises the need of tests the non-functional requirements, like performence, that makes it necessary to be very specific
in the language describing such requirements, those specificiations can not be subjective and have to be exhoustive, so
that external conditions would not affect the result of tests.

Data migration - in case of softwere being a replacment for temporary used softwere, the data that has been previously used
have to match and be readeble by new softwere.
As this can be a part of the contract, we need to develop a way of veryfing this - one of the aproaches could be to
asser the validitity of random samples of migrated data and draw the conclusions based on that.

Mostly contract should also inculde training of the users of the developed softwere. Just as contract elements above this also
requires a way of verification - after a training thats the contract ensures to takes place, we should verify
the degree of understanding of the software. A best of way of testing it is to validate whether employees - users of the softwere
can do their chores using the developed software— unfortunately, it costs. One of the ways to mitigate costs is to
also use the statistical approach - approximate the real results on conclusions from sampled data.

Contract should specifically the time for rectification in case of failed acceptance. 
The work put into ractification is at contractor (the provider) expense.
Provider can cover themself by speicificng a markup cost in the contract that would cover need for potential ractifiction.
It's benefficial for both sides to put an effort into stating the shape of consequences of failed acceptence.

## Post-implementation service
Next phase.

There will arise need of patches as erros will spring out.
Maintenance is a part of the contract - SLA and the costs should be specified.

Also the continued relevance of the sofwtere should be ensured - therefore it has to be continously modified
to match the new problems and demands in the market.
SLA could be used to legally bind that - for example it could state that X men hours monthly should be invested into
modifiction of the software.
The customer of the softwere is interested in the software continuing to work and not getting deprecated due to changing 
enviorment.

Helpdesk is also often a part of the contract. It should be adjusted to address the dfferent types of problems.

(i need to rewatched missed lectures)

An alternative from the classic approach is SaaS.
In SaaS the receiver can be subjected of vendor lock-in.
It's easiest to make a vendor lock-in either by investing time to familiarize your employees with complex
mechanincs of the softwere or investing the data.