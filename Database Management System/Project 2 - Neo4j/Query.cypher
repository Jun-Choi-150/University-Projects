//1. The student number and ssn of the student whose name is "Becky"
MATCH (s:students {name: "Becky"})
RETURN s.snum, s.ssn

//2. The major name and major level of the student whose ssn is 123097834
MATCH (s:students {ssn: 123097834})-[:major]->(d:degrees)
RETURN d.dname, d.level

//3. All degree names and levels offered by the department Computer Science
MATCH (d:department {dname: "Computer Science"})-[:administer]->(deg:degrees)
RETURN deg.dname, deg.level

//4. The names of all students who have a minor
MATCH (s:students)-[:minor]->(d:degrees)
RETURN s.name

//5. The count of students who have a minor
MATCH (s:students)-[:minor]->(:degrees)
RETURN count(s) as minor_students_count

//6. The names and snums of all students enrolled in course “Algorithm”
MATCH (s:students)-[:register]->(c:courses {name: "Algorithm"})
RETURN s.name, s.snum

//7. The names of all students who enrolled in course 363 and their corresponding grade 
MATCH (s:students)-[r:register]->(c:courses {number: 363})
RETURN s.name, r.grade

//8. The name and snum of the oldest student
MATCH (s:students)
RETURN s.name, s.snum
ORDER BY s.dob
LIMIT 1

//9. The name and snum of the youngest student
MATCH (s:students)
RETURN s.name, s.snum
ORDER BY s.dob DESC
LIMIT 1

//10. The name, snum and SSN of the students whose name contains letter “n” or “N”
MATCH (s:students)
WHERE toLower(s.name) CONTAINS 'n'
RETURN s.name, s.snum, s.ssn

//11. The name, snum and SSN of the students whose name does not contain letter “n” or “N”
MATCH (s:students)
WHERE NOT toLower(s.name) CONTAINS 'n'
RETURN s.name, s.snum, s.ssn

//12. The course number, name and the number of students registered for each course
MATCH (c:courses)<-[:register]-(s:students)
WITH c, count(s) as num_students
RETURN c.number, c.name, num_students

//13. The name of the students enrolled in Fall2020 semester.
MATCH (s:students)-[r:register]->(:courses)
WHERE r.regtime = "Fall2020"
RETURN DISTINCT s.name

//14. The course numbers and names of all courses offered by Department of Computer Science
MATCH (d:department {dname: "Computer Science"})-[:offers]->(c:courses)
RETURN c.number, c.name

//15. The course numbers and names of all courses offered by either Department of Computer Science or Department of Landscape Architect.
MATCH (d:department)-[:offers]->(c:courses)
WHERE d.dname IN ["Computer Science", "Landscape Architect"]
RETURN c.number, c.name