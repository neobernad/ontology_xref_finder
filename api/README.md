# Crossreference finder API

The API runs on port 8088. In order to find the swagger information, please go to:

```bash
http://{host}:8088/swagger-ui.html
```

# Open Endpoints

## xref-controller

Crossreference finder API

### Get ontology IDs from OLS

Returns a list of ontologies located in an OLS endpoint.

**Method** : `GET`

**URL** : `/rest/xref/ols/getOntologies`

**URL Params** :
- Required:
	-  url=[URL]. URL to an OLS API (default: https://www.ebi.ac.uk/ols/api).

**Permissions required** : None

#### Success Response

**Code** : `200 OK`

#### Example:
*Query*:
`{{URL}}:{{PORT}}/rest/xref/ols/getOntologies?url=https://www.ebi.ac.uk/ols/api`
*Result*:

```json
[
    {
        "id": "aeo",
        "title": "Anatomical Entity Ontology",
        "version": "2014-12-05",
        "preferredPrefix": "AEO",
        "description": "AEO is an ontology of anatomical structures that expands CARO, the Common Anatomy Reference Ontology",
        "homepage": "https://github.com/obophenotype/human-developmental-anatomy-ontology/"
    },
    {
        "id": "apo",
        "title": "Ascomycete phenotype ontology",
        "version": "2020-03-10",
        "preferredPrefix": "APO",
        "description": "A structured controlled vocabulary for the phenotypes of Ascomycete fungi",
        "homepage": "http://www.yeastgenome.org/"
    },
    ...
]
```

### Search crossreferences

Returns the crossreferences found under the input criteria.

**Method** : `GET`

**URL** : `/rest/xref/ols/search`

**URL Params** :
- Required:
	- ontologies=[List<String>]. Comma separated list of ontology names to perform the search. If the list is empty, it searches in the whole OLS.
	- terms=[List<String>]. Comma separated list of terms to search in the OLS.
	- url=[URL]. URL to the OLS API endpoint.
- Optional:
	- exact=[Boolean]. Performs an exact search in the OLS, if true. Default: true.
	- max_xrefs=[Integer]. Maximum number of crossreferences to search per term. Default: 1.

**Permissions required** : None

#### Success Response

**Code** : `200 OK`

#### Example:
*Query*:
`{{URL}}:{{PORT}}/rest/xref/ols/search?exact=true&max_xrefs=5&terms=mol,water&url=https://www.ebi.ac.uk/ols/api&ontologies=ncit`
*Result*:

```json
{
    "xrefFound": [
        {
            "text": "mol",
            "xrefs": [
                {
                    "xrefLabel": "Mole",
                    "xrefIri": "http://purl.obolibrary.org/obo/NCIT_C42539",
                    "xrefOntology": "ncit",
                    "xrefSynonyms": [
                        "mole(s)",
                        "mole (chemical)",
                        "mole",
                        "mol",
                        "M",
                        "Mole",
                        "MOLE"
                    ]
                }
            ]
        },
        {
            "text": "water",
            "xrefs": [
                {
                    "xrefLabel": "Water",
                    "xrefIri": "http://purl.obolibrary.org/obo/NCIT_C65147",
                    "xrefOntology": "ncit",
                    "xrefSynonyms": [
                        "Water",
                        "WATER"
                    ]
                },
                {
                    "xrefLabel": "Drinking Water",
                    "xrefIri": "http://purl.obolibrary.org/obo/NCIT_C90377",
                    "xrefOntology": "ncit",
                    "xrefSynonyms": [
                        "Drinking Water",
                        "WATER"
                    ]
                }
            ]
        }
    ]
}
```


