//1. create courses node
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vQl4ewanefLXz_qsNa1FEYimkQoPWLighjsYtUicQ2ZAfje3BJNvIq2LHLMsTtNhSRq3bdDudFwSA47/pub?gid=1395040011&single=true&output=csv' AS row
CREATE (c:courses {
  number: toInteger(row.number),
  name: row.name,
  description: row.description,
  credithours: toInteger(row.credithours),
  level: row.level,
  dcode: toInteger(row.dcode)
})

//2. create degree nodes
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vQmzlXmZkQbRaV4gcSaMRINRlEypaOZjMNBNutAHj2Eij4CgpfYni54oj5pFtNAb1k5vCu6pQlsuSBN/pub?gid=1817942933&single=true&output=csv
' AS row
CREATE (d:degrees {
  dname: row.name,
  level: row.level,
  dcode: toInteger(row.dcode)
})

//3. create department node
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vSZjB-njgyoNAS7hhgvyqPMKs7gSEAzRzj55XrDsS4Ly_Q5pS2XWIWe-Qnx_UsszAxs7GZDT8z9N6l6/pub?gid=1270533085&single=true&output=csv
' AS row
CREATE (d:department {
  dcode: toInteger(row.dcode),
  dname: row.name,
  phone: toInteger(row.phone),
  college: row.college
})

//4. create students node
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vROrskhgTXDn0YC0bMnn9pSZG0fguBT8qSemdN8iJyNqhhn9bQ6z5X-3Lw1I3lFBmNyfDVohA4ZfOHi/pub?gid=946458172&single=true&output=csv' AS row
CREATE (s:students {
  snum: toInteger(row.snum),
  ssn: toInteger(row.ssn),
  name: row.name,
  gender: row.gender,
  dob: date(row.dob),
  c_addr: row.c_addr,
  c_phone: row.c_phone,
  p_addr: row.p_addr,
  p_phone: row.p_phone
})

//5. Create constraint
create constraint constraint_students for (s:students) require s.snum is unique;
create constraint constraint_department for (d:department) require d.dcode is unique;
create constraint constraint_degrees FOR (deg:degrees) REQUIRE (deg.dname, deg.level) is unique;
create constraint constraint_courses for (c:courses) require c.number is unique;

//6. Create relation_administer
MATCH (d:degrees), (dep:department)
WHERE d.dcode = dep.dcode
MERGE (d)<-[:administer]-(dep)

//7. Create relation_major
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vShYSEUl5j9HpV0GngP4AIk_3wlIfQXK-OF3zCxiL6TkDZ69ultb734LN5LPBq9oKAfcl-T1iqQkS1q/pub?gid=995703209&single=true&output=csv' AS row
MATCH (s:students {snum: toInteger(row.snum)}), (d:degrees {dname: row.name, level: row.level})
MERGE (s)-[:major]->(d)

//8. Create relation_minor
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vQEhGIicNxhMTy6z0bpzPZIlSneFOorg0Sp6ApOFlHf0uz3aNuZAHB1KoXNssjqrGPksWXjEdBJeZ1j/pub?gid=1971279781&single=true&output=csv' AS row
MATCH (s:students {snum: toInteger(row.snum)}), (d:degrees {dname: row.name, level: row.level})
MERGE (s)-[:minor]->(d)

//9. Create relation_register
LOAD CSV WITH HEADERS FROM 'https://docs.google.com/spreadsheets/d/e/2PACX-1vT7DgJc6JrTXz0JI5QUy4Vf8EbUipytjzLBLTRYe9qCQai62-4wxQFAnW5VUzHI0YZcKzt3_aGZS_GD/pub?gid=147295713&single=true&output=csv' AS row
MATCH (s:students {snum: toInteger(row.snum)}), (c:courses {number: toInteger(row.course_number)})
MERGE (s)-[r:register]->(c)
SET r.regtime = row.regtime, r.grade = toInteger(row.grade)

//10. create relation_offer
MATCH (c:courses), (d:department)
WHERE c.dcode = d.dcode
MERGE (d)-[:offers]->(c)