#!/usr/bin/env bash

set -e
cd "$(dirname "${BASH_SOURCE[0]}")"/..

bin/run_sql.sh <sql/Ellin.sql
for file in sql/data/*; do
    bin/run_sql.sh <$file
done
