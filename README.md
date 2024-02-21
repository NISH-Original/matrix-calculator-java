# Matrices Calculator

## About the project

The project will be a Matrices and Vectors calculator, designed to simplify complex matrices and vector operations for students and professionals alike. The application will provide a user-friendly interface to perform various operations including matrix addition, subtraction and multiplication of matrices, making it a very helpful tool for mathematicians, and computer scientists.


As mentioned earlier, the application will mainly be used by students and professionals having to do with the fields of mathematics and computer science. This project is of personal interest to me because I also am currently taking up a linear algebra course, which consists mostly of matrices and their properties and calculations, thus I wanted to build an application that can facilitate me and fellow students to easily carry out matrix calculations, thus saving us a lot of time and effort, so we can focus on studying the more in-depth and logical aspects of linear algebra. Therefore, I am enthusiastic about developing such a robust and user-friendly application that can enhance the learning and problem-solving experiences of students and professionals.

## User Story
- As a user, I want to be able to first choose what operation I want to carry out with the matrices.
- As a user, I want to be able to input the dimensions of the matrices and input them in a grid format.
- As a user, I want to be able to save the history of my current session in a json file by adding multiple operations to the history that were carried out during runtime.
- As a user, I want to view a filtered list of any single operation in my history.
- As a user, I want to be able to delete some operations from the history.
- As a user, I want to load the history of my previous session in a new session.

## Instructions for the grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by inputting the rows and columns on the matrices, selecting an operation from the dropdown, and pressing the 'Enter' button. Then, input the matrices, and press 'Calculate' to see the answer.
- You can generate the second required action related to the user story "viewing list of Xs in Y" by pressing the 'History' button in the side-panel to view the list of operations carried out in the current session.
- You can generate the second required action related to the user story "filtering Xs in Y" by pressing the 'History' button in the side-panel to view the list of operations carried out in the current session, then choosing any operation in the 'Filter' dropdown on the top-panel of the History window, to view a filtered list of history for that particular operation.
- You can locate my visual component by running the code and viewing the splashscreen (Neo!) before the program starts.
- You can save the state of my application by pressing the 'Save' button in the sidebar.
- You can reload the state of my application by pressing the 'Load' button in the sidebar.

## Phase 4: Task 2

Mon Nov 27 17:05:52 PST 2023
Calculated answer for Addition


Mon Nov 27 17:05:52 PST 2023
Added operation to history


Mon Nov 27 17:06:00 PST 2023
Calculated answer for Subtraction


Mon Nov 27 17:06:00 PST 2023
Added operation to history


Mon Nov 27 17:06:09 PST 2023
Calculated answer for Multiplication


Mon Nov 27 17:06:09 PST 2023
Added operation to history


Mon Nov 27 17:06:19 PST 2023
Deleted operation from history


Mon Nov 27 17:06:21 PST 2023
Deleted operation from history


Mon Nov 27 17:06:22 PST 2023
Filtered history for Multiplication


Mon Nov 27 17:06:23 PST 2023
Filtered history for Subtraction


Mon Nov 27 17:06:23 PST 2023
Filtered history for Addition


Mon Nov 27 17:06:26 PST 2023
Cleared entire history


Mon Nov 27 17:06:30 PST 2023
Added operation to history


Mon Nov 27 17:06:38 PST 2023
Deleted operation from history

## Phase 4: Task 3

If I had more time to work on my project, I would have made the following changes:
- Making my `Operation` class abstract instead of an interface because I found that I duplicated a lot of my methods for all the `Addition`, `Subtraction` and `Multiplication` classes that implemented `Operation`. Then, I would've implemented the common methods directly in the `Operation` class and have the subclasses implement those methods, like the getters and setters.
