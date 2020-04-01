# Introduction

This tool reads an input ontology (`-i`) and loops through its classes. For each class, it searches crossreferences to the OLS given its `rdfs:label`. The amount of xrefs to append to a class as an annotation property is limited to 1 by default or to `-mx` parameter. As a result, it stores a new ontology (`-o`) with the appended crossreferences to the classes.

# Usage

```bash
usage: ontology_xref_finder [-h] [-i <arg>] [-mx <arg>] [--no_dbxref] [-o
       <arg>] [--ols_url <arg>] [--ontologies <arg>]
---
 -h                      help
 -i,--input <arg>        Input ontology file path
 -mx,--max_xrefs <arg>   Maximum number of Xrefs to add to a term
    --no_dbxref          If specified, annotations are NOT annotated with
                         the source cross-referenced class where the
                         annotation comes from.
 -o,--output <arg>       Output ontology file path
    --ols_url <arg>      URL to the OWL API (e.g.
                         https://www.ebi.ac.uk/ols/api)
    --ontologies <arg>   Comma separeted list of ontology names (e.g:
                         doid,clo)
---

```

For custom OLS deployments use `--ols_url` to specify the URL of the OLS API.