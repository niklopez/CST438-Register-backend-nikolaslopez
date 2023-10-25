INSERT INTO student VALUES 
(1,'test','test@csumb.edu',NULL,0),
(2,'david','dwisneski@csumb.edu',NULL,0),
(3,'tom', 'trebold@csumb.edu', NULL, 0);

INSERT INTO course VALUES 
(2020,'Fall',30157,1,'BUS 203 - Financial Accounting','We 6:00PM - 7:20PM','506','112','cchou@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',30163,1,'BUS 306 - Fundamentals of Marketing','Mo 11:00AM - 11:50AM','Library','1180','anariswari@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',30291,1,'BUS 304 - Business Communication, Pro-seminar & Critical Thinking','Mo 8:00AM - 9:50AM','506','108','kposteher@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31045,1,'CST 363 - Introduction to Database Systems','MoWe 4:00PM - 5:50PM','506','104','dwisneski@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31249,1,'CST 237 - Intro to Computer Architecture','TuTh 2:00PM - 3:50PM','506','104','sislam@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31253,1,'BUS 307 - Finance','We 2:00PM - 3:50PM','506','112','hwieland@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31747,1,'CST 238 - Introduction to Data Structures','Mo 2:00PM - 2:50PM','506','117','jgross@csumb.edu','2020-08-24','2020-12-13'),
(2021,'Fall',40442,1,'CST 363 - Introduction to Database Systems','MoWe 4:00PM - 5:50PM','506','104','dwisneski@csumb.edu', '2021-08-24', '201-12-13'),
(2021,'Fall',40443,1,'CST 438 - Software Engineering','TuTh 10:00AM - 11:50AM','506','BIT','dwisneski@csumb.edu','2021-08-24','2021-12-13');


insert into enrollment values 
(1, 1, 2020, 'Fall', 30157, null), 
(2, 1, 2020, 'Fall', 30163, null),
(3, 1, 2020, 'Fall', 31045, null),
(4, 2, 2020, 'Fall', 31045, null),
(5, 3, 2020, 'Fall', 31045, null),
(6,3, 2021, 'Fall', 40443, null),
(7,1, 2021, 'Fall', 40443, null);


-- Assignment 
-- Insert assignments for course 40443
-- INSERT INTO assignment (due_date, name, course_id)
-- VALUES ('2021-10-10', 'Assignment 1', 40443),
--        ('2021-10-15', 'Assignment 1r', 40443);
--        -- Assign scores to students for assignments

-- INSERT INTO assignment_grade (score, assignment_id, enrollment_id)
-- VALUES (60, 1, 6), -- Score 85 for Assignment 1 (enrollment_id 6 corresponds to 'tom')
--        (50, 2, 6), -- Score 90 for Assignment 2 (enrollment_id 6 corresponds to 'tom')
--        (92, 1, 7), -- Score 92 for Assignment 1 (enrollment_id 7 corresponds to 'test')
--        (100, 2, 7); -- Score 88 for Assignment 2 (enrollment_id 7 corresponds to 'test')
