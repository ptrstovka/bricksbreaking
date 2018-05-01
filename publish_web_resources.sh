#!/usr/bin/env bash

cd $PWD/web && npm run deploy && cd ..
rm -rf src/main/resources/static/*
mkdir -p src/main/resources/static/assets
cp -R web/assets/* src/main/resources/static/assets
cp -R web/build/* src/main/resources/static
