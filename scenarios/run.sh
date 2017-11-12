#!/bin/sh

for file in scenarios/*
do
    echo Running scenario $file
    sh ${file}
done