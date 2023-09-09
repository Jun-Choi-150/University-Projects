//1. Change the name of the student with ssn = 746897816 to Scott
MATCH (s:students {ssn: 746897816})
SET s.name = "Scott"

//2. Change the major of the student with ssn = 746897816 to Computer Science, Master. 
MATCH (s:students {ssn: 746897816})-[r:major]->(:degrees)
DELETE r
WITH s
MATCH (d:degrees {dname: "Computer Science", level: "MS"})
CREATE (s)-[:major]->(d)

//3. Delete all registration records that were in “Spring2021”
MATCH (:students)-[r:register {regtime: "Spring2021"}]->(:courses)
DELETE r