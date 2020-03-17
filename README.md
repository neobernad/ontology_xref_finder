# Introduction

This project holds the following modules:

* [core](/core):

This tool reads an input ontology (`-i`) and loops through its classes. For each class, it searches crossreferences to the OLS given its `rdfs:label`. The amount of xrefs to append to a class as an annotation property is limited to 1 by default or to `-mx` parameter. As a result, it stores a new ontology (`-o`) with the appended crossreferences to the classes.

* [frontend](/):

**TODO**