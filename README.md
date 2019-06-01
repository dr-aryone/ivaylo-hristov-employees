# ivaylo-hristov-employees
This is explanation how i decided to approach the problem:
Load the data.
Create the employees and store them in a Set to avoid duplicates.
Extract employees data (each employee contains Map that links the project id and the days that they worked on it).
Test all possible combinations for pair of employees and store them in Map<Partnership, Days that they worked together>. If the same pair was working on different projects together then the previous Value is summed with the new one.
Find the biggest Map value and return a string that contains the result.


