# dbgenerator

A simple tool for defining a relational database model in Java with possibilities to generate (so far Oracle compatible) DDL statements defining the database as well as to create respective test data.

Currently, the tool is set up to generate all of this for a specific database, yet this could be easily refactored to a more modular approach where user-defined classes and schemas may be supplied without altering the source code.