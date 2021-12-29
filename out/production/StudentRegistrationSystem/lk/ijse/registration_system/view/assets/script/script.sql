SELECT s.studentId,s.studentName,s.address,s.dob,s.age,s.email,s.contactNo,p.programName,r.upfrontFee
FROM Student s INNER JOIN Registration r
ON s.studentId = r.studentId
INNER JOIN Program p
ON r.programId=p.programId
WHERE s.studentId='S001';

SELECT s.studentId,p.programName,r.upfrontFee
FROM Student s INNER JOIN Registration r
ON s.studentId = r.studentId
INNER JOIN Program p
ON r.programId=p.programId
WHERE s.studentId='S001';

INSERT INTO Registration (dateOfReg,upfrontFee,programId, studentId) values ('2021-12-23','15000','CT0003','S001');


INSERT INTO Login (userName,password) values ('U001','U001@ijse');
INSERT INTO Login (userName,password) values ('U002','U002@ijse');

SELECT l.password FROM Login l WHERE l.password = "U001@ijse";