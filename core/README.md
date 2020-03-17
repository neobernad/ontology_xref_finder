# Introduction

This tool reads an input ontology (`-i`) and loops through its classes. For each class, it searches crossreferences to the OLS given its `rdfs:label`. The amount of xrefs to append to a class as an annotation property is limited to 1 by default or to `-mx` parameter. As a result, it stores a new ontology (`-o`) with the appended crossreferences to the classes.

# Usage

```bash
usage: ontology_xref_finder [-c <arg>] [-h] [-i <arg>] [-mx <arg>] [-o
       <arg>]
---
 -c,--config <arg>       Path to a config file
 -h                      help
 -i,--input <arg>        Input ontology file path
 -mx,--max_xrefs <arg>   Maximum number of Xrefs to add to a term
 -o,--output <arg>       Output ontology file path
---
```

# Configuration file

You can override the API endpoints with via the `-c` parameter, which points to configuration file path. The configuration template is the following (change at will):

```bash
# OLS API
ols_api="https://www.ebi.ac.uk/ols/api"
```