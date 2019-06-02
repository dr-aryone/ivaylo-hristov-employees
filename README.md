# ivaylo-hristov-employees
This is explanation how i decided to approach the problem:
  1. Load the data.
  2. Create the employees and store them in a Set to avoid duplicates.
  3. Extract employees data (each employee contains Map that links the project id and the days that they worked on it).
  4. Test all possible combinations for pair of employees and store them in Map<Partnership, Days that they worked together>. If the same pair   was working on different projects together then the previous Value is summed with the new one.
  5. Find the biggest Map value and return a string that contains the result.


