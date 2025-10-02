SELECT
    b.book_id,
    b.title,
    SUM(o.quantity) AS total_quantity_sold
FROM
    Books b
        JOIN
    Orders o ON b.book_id = o.book_id
GROUP BY
    b.book_id, b.title
ORDER BY
    total_quantity_sold DESC
    LIMIT 5;



SELECT
    student_id,
    student_name,
    SUM(max_subject_mark) AS total_marks
FROM (
         SELECT
             student_id,
             student_name,
             subject,
             MAX(marks) AS max_subject_mark
         FROM
             student_test_scores
         GROUP BY
             student_id, student_name, subject
     ) AS subject_max_marks
GROUP BY
    student_id, student_name
ORDER BY
    total_marks DESC;


SELECT DISTINCT salary
FROM Employee
ORDER BY salary DESC
    LIMIT 1 OFFSET 1;


SELECT d.department_id, d.dept_name, AVG(e.salary) AS avg_salary
FROM Employee e
         JOIN Department d ON e.department_id = d.department_id
GROUP BY d.department_id, d.dept_name
ORDER BY avg_salary DESC
    LIMIT 1;


SELECT e.*
FROM Employees e
         LEFT JOIN Orders o ON e.employee_id = o.employee_id
WHERE o.employee_id IS NULL;
