//Drop All 
DROP CONSTRAINT constraint_courses;
DROP CONSTRAINT constraint_degrees;
DROP CONSTRAINT constraint_department;
DROP CONSTRAINT constraint_students;
MATCH (n)
DETACH DELETE n