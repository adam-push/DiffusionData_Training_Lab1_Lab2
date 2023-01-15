#!/bin/sh
mvn dependency:copy-dependencies
mvn install
java -cp "target/Lab2-0.0.1-SNAPSHOT.jar:target/dependency/*" com.diffusion.training.lab2.AddCheckRequestClient


