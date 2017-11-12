#!/bin/sh

# We will do 24x 5 seconds loop = two minutes
for file in scenarios/*
do
    echo Running scenario $file
    sh ${file}
done