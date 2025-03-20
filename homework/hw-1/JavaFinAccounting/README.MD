# JavaFinAccounting
Software Construction course first big homework.

## Build
```
gradle build
```
```
java -jar build\libs\JavaFinAccounting-1.0-SNAPSHOT.jar
```

## Available commands
```create account [name] [balance]``` - creates new account
```create category [type] [name]``` - creates new category (type: income (i) /expense (e))
```create operation [type] [amount] [date] [bankAccountId] [categoryId] [description]``` - creates new operation
(type: income (i) /expense (e)), description is optional

```update account [id] [name] [balance]``` - updates account with given id
```update category [id] [name]``` - updates category with given id
```update operation [id] [type] [amount] [date] [bankAccountId] [categoryId] [description]``` - updates operation
with given id

```delete account [id]``` - deletes account with given id
```delete category [id] ``` - deletes category with given id
```delete operation [id]``` - deletes operation with given id

```get accounts``` - displays all accounts
```get categories``` - displays all categories
```get operation``` - displays all operations

```export csv [accountsFileName] [categoriesFileName] [operationsFileName]``` - exports to csv (filenames without .csv)
```export yaml [accountsFileName] [categoriesFileName] [operationsFileName]``` - exports to yaml 
(filenames without .yaml)
```export json [accountsFileName] [categoriesFileName] [operationsFileName]```  - exports to json 
(filenames without .json)

```import csv [filePath]``` - imports from csv
```import yaml [filePath]``` - imports from yaml
```import json [filePath]``` - imports from json

```analytics summary account [id] [startDate] [endDate]``` - displays analytics for account with given id
```analytics summary all [startDate] [endDate]``` - displays analytics for all accounts

```group by categories``` - groups by categories
```group by accounts``` - groups by accounts

```exit``` - terminates program